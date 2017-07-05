package com.scott.shopplat.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.scott.shopplat.R;
import com.scott.shopplat.fragment.bill.BillFragment;
import com.scott.shopplat.fragment.car.CarFragment;
import com.scott.shopplat.fragment.goods.GoodsListFragment;
import com.scott.shopplat.fragment.home.HomeFragment;
import com.scott.shopplat.fragment.my.store.StoreMyFragment;
import com.scott.shopplat.fragment.my.user.MyFragment;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.view.BadgeView;


/**
 * ***************************
 * 主结构管理类
 *
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * ***************************
 */
public class MainFragmentActivity extends FragmentActivity {
    public static RadioButton homeRb, goodsRb, billRb,carRb,myRb;
    private RadioGroup radioGroup;
    public FragmentManager fragmentManager;
    public FragmentTransaction transaction;

    public HomeFragment homeFrag;
    public BillFragment billFrag;
    public GoodsListFragment goodsFrag;
    public CarFragment  carFrag;
    public MyFragment myFrag;
    public StoreMyFragment storeFrag;
    public Fragment lastshowFragment;
    private DisplayMetrics dm;
    public static final int FLAG = 100;//进入判断登录后返回主页 标示
    //    public  static boolean isShow=true;//判断是否主页打开过
    private String gesturePhone;//获取用户登录手机号用于判断是否使用手势密码登录
    public static BadgeView badge1;
     private String isMy = "2";//2 为登录状态为摊主，1 为个人用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
//        isShow = false;
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        SXUtils.getInstance().setSysStatusBar(this);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        init();
        compat(this);
    }

    private static final int INVALID_VAL = -1;
    public static void compat(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.red));//activity.getResources().getColor(R.color.blue));
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
            statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.blue));
            contentView.addView(statusBarView, lp);
        }
    }
    private void init() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        AppClient.fullWidth = dm.widthPixels;
        AppClient.fullHigh = dm.heightPixels;
        Logs.i("屏幕宽高========", AppClient.fullWidth + "====" + AppClient.fullHigh);
//        FragmentManager childFragmentManager = this.getChildFragmentManager();
        fragmentManager = this.getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.main_groub_tab);
        radioGroup.setOnCheckedChangeListener(new OnCheckChanged());
        homeRb = (RadioButton) findViewById(R.id.main_tab_home_rb);
        goodsRb = (RadioButton) findViewById(R.id.main_tab_financial_rb);
        billRb = (RadioButton) findViewById(R.id.main_tab_live_rb);
        myRb = (RadioButton) findViewById(R.id.main_tab_my_rb);
        carRb = (RadioButton) findViewById(R.id.main_tab_car_rb);

        homeRb.setChecked(true);
//        gesturePhone = SharedPrefsUtil.getSahrePreference(MainFragmentActivity.this, "gesturePhone", "");
//        if (!(TextUtils.isEmpty(gesturePhone))) {
//            RequestReqMsgData.ChckGestureStatue(MainFragmentActivity.this,0);
//        }
        Button btn4 = (Button) findViewById(R.id.btn_car);
        remind(btn4);
    }
    private void remind(View view) { //BadgeView的具体使用
         badge1 = new BadgeView(this, view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText("0"); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色

//        badge1.setPadding(0,0,0,2);
//        badge1.setHeight(30);
//        badge1.setWidth(30);
        badge1.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
//        badge1.setBackgroundResource(R.drawable.round_red_bg_shap); //设置背景图片
        badge1.setTextSize(12); // 文本大小
//        badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
//        badge1.setBadgeMargin(2); //各边间隔
//         badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        badge1.show();// 只有显示
        badge1.setVisibility(View.GONE);
//         badge1.hide();//影藏显示
    }
    public static MainFragmentActivity instance;

    public static MainFragmentActivity getInstance() {
        if (instance == null)
            instance = new MainFragmentActivity();
        return instance;
    }


    /**
     * 判断是否是减，还是加入购物车
     * @param issub  true 增加
     * @param strcount  增加条数
     */
    public void setBadge(boolean issub,int strcount){
        String nowsize = badge1.getText().toString();
            if(issub){
             int num= Integer.parseInt(nowsize.equals("99+") ?"99":nowsize)+strcount;
                if(num >= 100){
                    badge1.setText("99+");
                }else{
                    badge1.setText(num+"");
                }
                badge1.setVisibility(View.VISIBLE);
            }else{
                int num =  Integer.parseInt(nowsize.equals("99+") ?"99":nowsize)-strcount;
                if(num > 0){
                    badge1.setText(num+"");
                }else{
                    badge1.setText("0");
                    badge1.setVisibility(View.GONE);
                }
            }
    }
    public void addFragmentToStack(Fragment fragment) {
        if (fragmentManager == null)
            fragmentManager = this.getFragmentManager();
        if (transaction == null)
            transaction = fragmentManager.beginTransaction();
        if (lastshowFragment != null)
            transaction.hide(lastshowFragment);
        transaction.show(fragment);
        lastshowFragment = fragment;
        transaction.commit();
    }

    class OnCheckChanged implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            transaction = fragmentManager.beginTransaction();
            if (checkedId == homeRb.getId()) {
                if (homeFrag == null) {
                    homeFrag = new HomeFragment();
                    transaction.add(R.id.content, homeFrag);
                }
                addFragmentToStack(homeFrag);
            } else if (billRb.getId() == checkedId) {
                if (billFrag == null) {
                    billFrag = new BillFragment();
                    transaction.add(R.id.content, billFrag);
                }
                addFragmentToStack(billFrag);
            } else if (goodsRb.getId() == checkedId) {
                if (goodsFrag == null) {
                    goodsFrag = new GoodsListFragment();
                    transaction.add(R.id.content, goodsFrag);
                }
                addFragmentToStack(goodsFrag);
            } else if (myRb.getId() == checkedId) {
                if(isMy.equals("1")){
                    if (myFrag == null) {
                        myFrag = new MyFragment();
                        transaction.add(R.id.content, myFrag);
                    }
                    addFragmentToStack(myFrag);
                }else if(isMy.equals("2")){
                    if (storeFrag == null) {
                        storeFrag = new StoreMyFragment();
                        transaction.add(R.id.content, storeFrag);
                    }
                    addFragmentToStack(storeFrag);
                }

            }else if(carRb.getId() == checkedId){
                if (carFrag == null) {
                    carFrag = new CarFragment();
                    transaction.add(R.id.content,carFrag);
                }
                addFragmentToStack(carFrag);
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            exitSystem(MainFragmentActivity.this);
        }
        return true;
    }

    public long firstTime = 0;

    public void exitSystem(Activity context) {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_LONG).show();
            firstTime = secondTime;//更新firstTime
            return;
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(startMain);
            System.exit(0);
        }
    }

}