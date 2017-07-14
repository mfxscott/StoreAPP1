package com.scott.shopplat.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.fragment.MainFragmentActivity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.checkPermission.PermissionsActivity;
import com.scott.shopplat.utils.checkPermission.PermissionsChecker;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * ***************************
 * des
 * @author mfx
 * 邮箱：mafx@uxunchina.com
 * 公司：深圳市优讯信息技术有限公司
 * 日期：17/4/1 下午11:59
 * ***************************
 */
public class StartMainActivity extends Activity {
    private OKManager manager;//工具类

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_CODE = 0; // 请求码
    private MyCountDownTimer mc;
    private ViewPager viewPager;
    private List<View> viewContainter = new ArrayList<>();
    private LinearLayout guideLinlay;
    private ImageView logoIv;
    private TextView  countTv;
    private ImageView  addLogoIv;
    private RelativeLayout addLoglLin;
    private Activity activity;
    private Handler hand;
    /**
     * 图片资源id
     */
    private int[] imgIdArray ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            }
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //全屏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
//                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);
        activity = this;
        mPermissionsChecker = new PermissionsChecker(this);
        activity =this;
        //启动页面倒计时
        mc = new MyCountDownTimer(3000, 1000);
        mc.start();
//        // 缺少权限时, 进入权限配置页面
//        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//            startPermissionsActivity();
//        }
//        //获取手机IMEI码
//        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = tm.getSimSerialNumber();
//        Log.i("手机唯一标示IMEI====",imei+"");
//        GetuserList();
        initView();
    }
    private void initView(){
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:

                        break;
                    case AppClient.UPDATEVER:
                        Map<String,String> map = (Map<String, String>) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(map.get("verUrl")+"==");
                        break;
                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
        LauncherHttp();
//        RequestReqMsgData.UpdateVersion(activity,hand);
        logoIv = (ImageView) findViewById(R.id.start_logo_iv);
        guideLinlay = (LinearLayout) findViewById(R.id.start_guide_linlay);

        countTv = (TextView) findViewById(R.id.start_addlogo_time_tv);
        addLogoIv = (ImageView) findViewById(R.id.start_addlogo_iv);
        addLoglLin = (RelativeLayout) findViewById(R.id.start_addlogo_linlay);
        countTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartMainActivity.this, MainFragmentActivity.class);
                startActivity(intent);
                mc.cancel();
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgIdArray = new int[]{R.mipmap.guide_1, R.mipmap.guide_2,R.mipmap.guide_3,R.mipmap.guide_4};
        //设置Adapter
//        viewPager.setCurrentItem((mImageViews.length) * 100);
        View view1 = LayoutInflater.from(this).inflate(R.layout.guide, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.guide2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.guide3, null);
        View view4 = LayoutInflater.from(this).inflate(R.layout.guide4, null);

        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        viewContainter.add(view4);

        viewPager.setAdapter(new PagerAdapter() {

            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }
            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }
            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                if(position == 3){
                    TextView tv = (TextView) viewContainter.get(position).findViewById(R.id.guide_close_tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            guideLinlay.setVisibility(View.GONE);
                            addLoglLin.setVisibility(View.VISIBLE);
                            //广告显示倒计时
                            mc = new MyCountDownTimer(2000, 1000);
                            mc.start();
//                            Intent intent = new Intent(StartMainActivity.this, MainFragmentActivity.class);
//                            startActivity(intent);
                        }
                    });
                }
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return null;
            }
        });
    }
    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
    /**
     * 倒计时
     */
    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mc.cancel();
            if(addLoglLin.getVisibility() != View.VISIBLE) {
                String firstStr = SXUtils.getInstance(activity).getSharePreferences("isFirst");
                if(firstStr == null  || firstStr.equals("0")){
                    SXUtils.getInstance(activity).setSharePreferences("isFirst","1");
                    guideLinlay.setVisibility(View.VISIBLE);
                    logoIv.setVisibility(View.GONE);
                }
                else{
                    guideLinlay.setVisibility(View.GONE);
                    addLoglLin.setVisibility(View.VISIBLE);
                    mc = new MyCountDownTimer(3000, 1000);
                    mc.start();
                }
            }else{
                Intent intent = new Intent(StartMainActivity.this, MainFragmentActivity.class);
                startActivity(intent);
                finish();
            }

        }
        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("MainActivity", millisUntilFinished + "");
            countTv.setText("跳过"+millisUntilFinished / 1000);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            System.exit(0);
        }
        return true;
    }
    public void LauncherHttp(){
        RequestBody requestBody = new FormBody.Builder()
//                .add("mobile", mobile)
//                .add("vcode", codeStr)
//                .add("registerType", "0")//0=手机,1=微信,2=QQ
//                .add("password", psdStr)
//                .add("tag","64")
                .build();
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.APP_LAUNCH, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("启动图发送成功返回参数=======",jsonObject.toString());
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = "";
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                hand.sendMessage(msg);
            }
        });
    }
}
