package com.xianhao365.o2o.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.member.LoginNameActivity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.checkPermission.PermissionsActivity;
import com.xianhao365.o2o.utils.checkPermission.PermissionsChecker;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ***************************
 * des
 * @author mfx
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
    private int seconds=3000;//广告图片显示时间
    private String imgUrl;//广告图片
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
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
        SXUtils.getInstance(activity).deleteDir(SXUtils.getInstance(activity).getSDPath()+"/apk/sx.apk");
        //启动页面倒计时
        mc = new MyCountDownTimer(3000, 1000);
        mc.start();

        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(StartMainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(StartMainActivity.this, PERMISSIONS_STORAGE,
                    1000);//自定义的code
        }


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
        String username =SXUtils.getInstance(activity).getSharePreferences("username");
        String psd =  SXUtils.getInstance(activity).getSharePreferences("psd");
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(psd)){
            //启动进行登录
            SXUtils.getInstance(activity).psdLoginHttp(hand,username,psd);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
//                Intent aa = new Intent(activity, StoreMapActivity.class);
//                activity.startActivity(aa);
            } else
            {
                Toast.makeText(activity, "手机权限被拒绝,将无法定位当前周边店铺.", Toast.LENGTH_SHORT).show();
//                Intent aa = new Intent(activity, StoreMapActivity.class);
//                activity.startActivity(aa);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }
    private void initView(){
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        Glide.with(activity)
                                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1501757540559&di=e90bccf64389faec7963e21e102c4367&imgtype=0&src=http%3A%2F%2Fwww.1tong.com%2Fuploads%2Fallimg%2F121024%2F1-1210241T0360-L.jpg")
                                .fitCenter()
                                .into(addLogoIv);
                        break;
                    case AppClient.UPDATEVER:
                        Map<String,String> map = (Map<String, String>) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(map.get("verUrl")+"==");
                        break;
                    case AppClient.ERRORCODE:
//                        String errormsg = (String) msg.obj;
//                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
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
//                Intent intent = new Intent(StartMainActivity.this, MainFragmentActivity.class);
//                startActivity(intent);
                if(TextUtils.isEmpty(AppClient.USER_SESSION) || TextUtils.isEmpty(AppClient.USER_ID)){
                    Intent intent = new Intent(StartMainActivity.this, LoginNameActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                    startActivity(mainintent);
                }
                finish();
                mc.cancel();
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgIdArray = new int[]{R.mipmap.guide_1, R.mipmap.guide_2};
        //设置Adapter
//        viewPager.setCurrentItem((mImageViews.length) * 100);
        View view1 = LayoutInflater.from(this).inflate(R.layout.guide, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.guide2, null);
//        View view3 = LayoutInflater.from(this).inflate(R.layout.guide3, null);
//        View view4 = LayoutInflater.from(this).inflate(R.layout.guide4, null);

        viewContainter.add(view1);
        viewContainter.add(view2);
//        viewContainter.add(view3);
//        viewContainter.add(view4);

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
                if(position == 1){
                    TextView tv = (TextView) viewContainter.get(position).findViewById(R.id.guide_close_tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            guideLinlay.setVisibility(View.GONE);
                            addLoglLin.setVisibility(View.VISIBLE);
                            //广告显示倒计时
                            mc = new MyCountDownTimer(seconds, 1000);
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
                    mc = new MyCountDownTimer(2000, 1000);
                    mc.start();
                }
            }else{
//                Intent intent = new Intent(StartMainActivity.this, MainFragmentActivity.class);
//                startActivity(intent);
                if(TextUtils.isEmpty(AppClient.USER_SESSION) || TextUtils.isEmpty(AppClient.USER_ID)){
                    Intent intent = new Intent(StartMainActivity.this, LoginNameActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                    startActivity(mainintent);
                }
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
    public void LauncherHttp() {
        HttpParams httpParams = new HttpParams();
        HttpUtils.getInstance(activity).requestPost(false,AppClient.APP_LAUNCH, httpParams, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("启动图发送成功返回参数=======",jsonObject.toString());
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = new JSONObject(jsonObject.toString());
                    String secondsStr = jsonObject1.getString("seconds");
                    if(!TextUtils.isEmpty(secondsStr)){
                        seconds = Integer.parseInt(secondsStr);
                    }else{
                        seconds = 3;
                    }
                    imgUrl = jsonObject1.getString("imgUrl");
                } catch (JSONException e) {
                    Message msg = new Message();
                    msg.what = AppClient.ERRORCODE;
                    msg.obj = jsonObject.toString();
                    hand.sendMessage(msg);
                }
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
