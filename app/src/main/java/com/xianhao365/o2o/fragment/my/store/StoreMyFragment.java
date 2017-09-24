package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.MessageEvent;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.fragment.CommonWebViewMainActivity;
import com.xianhao365.o2o.fragment.my.store.order.MyOrderActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 摊主或者个人登录进入我的界面
 * 通过区分标识显示不同界面
 * @author mfx
 * @time  2017/7/5 16:22
 */
public class StoreMyFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout myStoreMyorderRel;
    private LinearLayout myStoreWaitpayLin;
    private LinearLayout myStoreWaitsendLin;
    private LinearLayout myStoreWaitgetLin;
    private LinearLayout myStoreMykeyLin;
    private LinearLayout myStoreCybillLin;
    private LinearLayout myStoreReturnLin;
    private LinearLayout myStoreNewLin;
    private LinearLayout myStoreMyfpLin;
    private RelativeLayout myStoreHelpCenterRel;
    private LinearLayout myStoreQuesionLin;
    private LinearLayout myStoreFwcenterLin;
    private LinearLayout myStoreZxserviceLin;
    private LinearLayout myStoreKffwLin;
    private Activity activity;
    private LinearLayout storeLin; //店铺账号类型显示
    private LinearLayout perLin; //个人账号类型显示
    private Handler hand;
    private UserInfoEntity userinfo;//个人所有用户信息
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView store_my_money;
    private TextView name;
    private ImageView headimg;
    private TextView orderNum1,orderNum2,orderNum3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.BLACK);
//        }
        view = inflater.inflate(R.layout.fragment_store_my, null);
        initView();
        SXUtils.showMyProgressDialog(activity,false);
        EventBus.getDefault().register(this);
        LoadData();
        return view;
    }

    /**
     * 初始化加载用户相关接口数据
     */
    private void LoadData(){
        if(SXUtils.getInstance(activity).IsLogin()) {
            getUserInfoHttp();
//            GetOrderListHttp();
//            GetUserWalletHttp();
        }else{
            if(swipeRefreshLayout != null){
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
    /**
     * 初始化
     */
    private void initView(){
        store_my_money = (TextView) view.findViewById(R.id.store_my_money);
        //订单状态订单数量
        orderNum1 = (TextView) view.findViewById(R.id.store_order_num1_tv);
        orderNum2 = (TextView) view.findViewById(R.id.store_order_num2_tv);
        orderNum3 = (TextView) view.findViewById(R.id.store_order_num3_tv);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.user_center_swipe_container);
//        swipeRefreshLayout.setColorSchemeResources( R.color.qblue, R.color.red, R.color.btn_gray);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
//                myWebView.reload();
//                getUserInfoHttp();
                LoadData();
            }
        });
//        Glide.with(activity).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(activity, 10)).into(headimg);

//        Glide.with(activity)
//                .load("")
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .bitmapTransform(new RoundedCornersTransformation(activity)) //使用圆形变换，还可以使用其他的变换
//                .into(headimg);
//
//
//        Glide.with(this).load("").bitmapTransform(new RoundedCornersTransformation(this, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(image5);

        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.my_per_wallet);
        rel.setOnClickListener(this);

        storeLin = (LinearLayout) view.findViewById(R.id.my_store_dis_lin);
        perLin = (LinearLayout) view.findViewById(R.id.my_per_dis_lin);
        if(AppClient.USERROLETAG .equals("64")){
            perLin.setVisibility(View.VISIBLE);
            storeLin.setVisibility(View.GONE);
        }else{
            perLin.setVisibility(View.GONE);
            storeLin.setVisibility(View.VISIBLE);
        }
        TextView accTv = (TextView) view.findViewById(R.id.my_acc_mamage_tv);
        accTv.setOnClickListener(this);
        ImageView messageIv = (ImageView) view.findViewById(R.id.per_my_message_iv);
        messageIv.setOnClickListener(this);

        myStoreMyorderRel = (RelativeLayout) view.findViewById(R.id.my_store_myorder_rel);
        myStoreWaitpayLin = (LinearLayout) view.findViewById(R.id.my_store_waitpay_lin);
        myStoreWaitsendLin = (LinearLayout) view.findViewById(R.id.my_store_waitsend_lin);
        myStoreWaitgetLin = (LinearLayout) view.findViewById(R.id.my_store_waitget_lin);
        myStoreMykeyLin = (LinearLayout) view.findViewById(R.id.my_store_mykey_lin);
        myStoreCybillLin = (LinearLayout) view.findViewById(R.id.my_store_cybill_lin);
        myStoreReturnLin = (LinearLayout) view.findViewById(R.id.my_store_return_lin);
        myStoreNewLin = (LinearLayout) view.findViewById(R.id.my_store_new_lin);
        myStoreMyfpLin = (LinearLayout) view.findViewById(R.id.my_store_myfp_lin);
        myStoreHelpCenterRel = (RelativeLayout) view.findViewById(R.id.my_store_help_center_rel);
        myStoreQuesionLin = (LinearLayout) view.findViewById(R.id.my_store_quesion_lin);
        myStoreFwcenterLin = (LinearLayout) view.findViewById(R.id.my_store_fwcenter_lin);
        myStoreZxserviceLin = (LinearLayout) view.findViewById(R.id.my_store_zxservice_lin);
        myStoreKffwLin = (LinearLayout) view.findViewById(R.id.my_store_kffw_lin);
        myStoreMyorderRel.setOnClickListener(this);
        myStoreWaitpayLin.setOnClickListener(this);
        myStoreWaitsendLin.setOnClickListener(this);
        myStoreWaitgetLin.setOnClickListener(this);
        myStoreMykeyLin.setOnClickListener(this);
        myStoreCybillLin.setOnClickListener(this);
        myStoreReturnLin.setOnClickListener(this);
        myStoreNewLin.setOnClickListener(this);
        myStoreMyfpLin.setOnClickListener(this);
        myStoreHelpCenterRel.setOnClickListener(this);
        myStoreQuesionLin.setOnClickListener(this);
        myStoreFwcenterLin.setOnClickListener(this);
        myStoreZxserviceLin.setOnClickListener(this);
        myStoreKffwLin.setOnClickListener(this);
        name = (TextView) view.findViewById(R.id.user_info_name_tv);
        headimg = (ImageView) view.findViewById(R.id.my_head_img);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        userinfo = (UserInfoEntity) msg.obj;
                        name.setText(userinfo.getUsername()+"");
                        Glide.with(activity).load(userinfo.getIcon()).placeholder(R.mipmap.default_head)
                                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity)).into(headimg);
                        break;
                    case 1001:
                        //订单列表
                        break;
                    case 1002:
                        //钱包
                        break;
                    case AppClient.ERRORCODE:
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                if(swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }
    private List<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    @Override
    public void onClick(View v) {
        if(!SXUtils.getInstance(activity).IsLogin())
            return ;
        switch (v.getId()){
            case R.id.my_per_wallet:
                Intent wall = new Intent(activity,MyWalletActivity.class);
                startActivity(wall);
                break;
            case R.id.my_acc_mamage_tv:
//                String json = SXUtils.getInstance(activity).getFromAssets("areas.json");
//                List<AddressProvinceEntity> jsonBean = ResponseData.getInstance(activity).parseJsonArray(json.toString(), AddressProvinceEntity.class);
//                ArrayList<String>  jsoList = new ArrayList<>();
//                for (int c = 0; c < jsonBean.size(); c++) {//遍历该省份的所有城市
//                    String CityName = jsonBean.get(0).getChildren().get(c).getLabel();
//                    jsoList.add(CityName);//添加城市
//                }
//
//                options1Items = jsoList;
//                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//                ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//                ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
//                for (int c = 0; c < jsonBean.get(0).getChildren().size(); c++) {//遍历该省份的所有城市
//                    String CityName = jsonBean.get(0).getChildren().get(c).getLabel();
//                    CityList.add(CityName);//添加城市
//
//                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//                }
//                for (int d = 0; d <jsonBean.get(0).getChildren().get(0).getChildren().size(); d++) {//该城市对应地区所有数据
//                    String AreaName = jsonBean.get(0).getChildren().get(0).getChildren().get(d).getLabel();
//                    City_AreaList.add(AreaName);//添加该城市所有地区数据
//                }
//
//                /**
//                 * 添加城市数据 shide
//                 */
//                options2Items.add(CityList);
//
//                /**
//                 * 添加地区数据OK
//                 */
//                options3Items.add(Province_AreaList);
//                //shide
//                //时间选择器
//                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(activity, new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                        //返回的分别是三个级别的选中位置
////                        String tx = options1Items.get(options1).getPickerViewText()
////                                + options2Items.get(options1).get(option2)
////                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
////                        tvOptions.setText(tx);
//                    }
//                })
//
//                        .setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("选择城市")//标题
//                        .setSubCalSize(18)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setLinkage(true)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(false)//是否显示为对话框样式
//                        .build();
//                pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
//                pvOptions.show();
                Intent accm = new Intent(activity,AccManageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("userinfo",userinfo);
                accm.putExtras(bundle);
                startActivity(accm);
                break;
            case R.id.per_my_message_iv:
                //消息
                Intent msg = new Intent(activity,MessageActivity.class);
                startActivity(msg);
                break;
            case R.id.my_store_myorder_rel:
                Intent order = new Intent(activity,MyOrderActivity.class);
                order.putExtra("orderTag","1");
                startActivity(order);
                break;
            case R.id.my_store_waitpay_lin:
                Intent payorder = new Intent(activity,MyOrderActivity.class);
                payorder.putExtra("orderTag","1");
                startActivity(payorder);
                break;
            case R.id.my_store_waitsend_lin:
                Intent sendorder = new Intent(activity,MyOrderActivity.class);
                sendorder.putExtra("orderTag","2");
                startActivity(sendorder);
                break;
            case R.id.my_store_waitget_lin:
                Intent getorder = new Intent(activity,MyOrderActivity.class);
                getorder.putExtra("orderTag","3");
                startActivity(getorder);
                break;
            case R.id.my_store_mykey_lin:
            case R.id.my_store_cybill_lin:
            case R.id.my_store_return_lin:
            case R.id.my_store_new_lin:
            case R.id.my_store_myfp_lin:
//            case R.id.my_store_help_center_rel:
            case R.id.my_store_quesion_lin:
            case R.id.my_store_fwcenter_lin:
            case R.id.my_store_zxservice_lin:
                Intent mykey = new Intent(activity, CommonWebViewMainActivity.class);
                mykey.putExtra("tag","2");
                mykey.putExtra("postUrl","http:www.baidu.com");
                startActivity(mykey);
                break;
            case R.id.my_store_kffw_lin:
                //拨打客服电话
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + AppClient.CUST_TELEPHONE);
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取不同用户信息
     */
    public void getUserInfoHttp() {
        HttpUtils.getInstance(activity).requestPost(false,AppClient.USER_INFO, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                UserInfoEntity gde = null;
                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),UserInfoEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = gde;
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what =AppClient.ERRORCODE;
                msg.obj = strError;
                hand.sendMessage(msg);

            }
        });
    }


//    public void getUserInfoHttp(){
//        RequestBody requestBody = new FormBody.Builder()
//                .build();
//        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.USER_INFO, new OKManager.Func4() {
//            @Override
//            public void onResponse(Object jsonObject) {
//                try {
//                    UserInfoEntity userinfo =  ResponseData.getInstance(activity).getUserInfo(jsonObject);
//                    Message msg = new Message();
//                    msg.what = 1000;
//                    msg.obj = userinfo;
//                    hand.sendMessage(msg);
//                } catch (JSONException e) {
//                    Message msg = new Message();
//                    msg.what = AppClient.ERRORCODE;
//                    msg.obj = e.toString();
//                    hand.sendMessage(msg);
//                }
//
//            }
//            @Override
//            public void onResponseError(String strError) {
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = strError;
//                hand.sendMessage(msg);
//            }
//        });
//    }
    /**
     * 获取订单信息
     */
    public void GetOrderListHttp() {
        HttpUtils.getInstance(activity).requestPost(false,AppClient.USER_ORDERS, null, new HttpUtils.requestCallBack() {

            @Override
            public void onResponse(Object jsonObject) {
//                UserInfoEntity gde = null;
//                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),UserInfoEntity.class);
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = "订单条目"+jsonObject.toString();
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = "获取用户订单信息="+strError;
                hand.sendMessage(msg);

            }
        });
    }
    //    /**
//     * 获取用户余额
//     */
//    public void GetUserWalletHttp() {
//        HttpUtils.getInstance(activity).requestPost(false,AppClient.USER_WALLET, null, new HttpUtils.requestCallBack() {
//            @Override
//            public void onResponse(Object jsonObject) {
////                UserInfoEntity gde = null;
////                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),UserInfoEntity.class);
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = "余额="+jsonObject.toString();
//                hand.sendMessage(msg);
//            }
//            @Override
//            public void onResponseError(String strError) {
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = "获取钱包余额="+strError;
//                hand.sendMessage(msg);
//
//            }
//        });
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        if(messageEvent.getTag()==1){
            LoadData();
        }else if(messageEvent.getTag() == 4444){
            name.setText("未登录");
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
