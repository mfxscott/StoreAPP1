package com.xianhao365.o2o.fragment.my.store;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.entity.MessageEvent;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.entity.address.AddressProvinceEntity;
import com.xianhao365.o2o.fragment.my.store.yhj.AddAccActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 账号管理
 */
public class AccManageActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;
    private Handler hand;
    private UserInfoEntity userinfo;
    @BindView(R.id.acc_manage_filesize_tv)
    TextView filesizeTv;
    @BindView(R.id.acc_manage_clear_rel)
    RelativeLayout  clearRel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_acc_manage);
        Bundle bundle = this.getIntent().getExtras();
        userinfo =(UserInfoEntity)bundle.getParcelable("userinfo");
        ButterKnife.bind(this);
        activity = this;
        initView();
        initData(userinfo);
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");


        LinearLayout accinfolin = (LinearLayout) findViewById(R.id.acc_manage_info_lin);
        accinfolin.setOnClickListener(this);

        Button loginOut = (Button) findViewById(R.id.login_out_btn);
        loginOut.setOnClickListener(this);
        RelativeLayout  rel = (RelativeLayout) findViewById(R.id.acc_manage_security_rel);
        rel.setOnClickListener(this);
        RelativeLayout addacc = (RelativeLayout) findViewById(R.id.acc_manage_addson_rel);
        addacc.setOnClickListener(this);
        clearRel.setOnClickListener(this);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("退出成功");
                        AppClient.USER_SESSION = "";
                        AppClient.USER_ID="";
                        SXUtils.getInstance(activity).removeSharePreferences("username");
                        SXUtils.getInstance(activity).removeSharePreferences("psd");
                        EventBus.getDefault().post(new MessageEvent(4444,"login out"));
                        finish();
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
        Long  filesize =   SXUtils.getInstance(activity).getFolderSize(new File(SXUtils.getInstance(activity).getSDPath()));
        filesizeTv.setText(filesize+"kb");
    }
    private void  initData(UserInfoEntity usrnfo){
        if(usrnfo == null)
            return;
        ImageView headimg = (ImageView) findViewById(R.id.acc_manage_headimg);
        Glide.with(activity).load(usrnfo.getShopLogo()).placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity)).into(headimg);
        TextView tvname = (TextView) findViewById(R.id.acc_manage_name_tv);
        if(AppClient.USERROLETAG.equals("64") || AppClient.USERROLETAG.equals("4") || AppClient.USERROLETAG.equals("16")){
            tvname.setText(usrnfo.getAcount()+"");
        }else{
            tvname.setText(usrnfo.getShopName()+"");
//            addacc.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_out_btn:
                SXUtils.showMyProgressDialog(activity,false);
                getLoginOutHttp();
                break;
            case R.id.acc_manage_info_lin:
//                init();
//                initJsonData();

                Intent intent = new Intent(activity,AccInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("userinfo",userinfo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.acc_manage_security_rel:
                Intent sec = new Intent(activity,AccSecurityActivity.class);
                startActivity(sec);
                break;
            case R.id.acc_manage_addson_rel:
                //添加子账号界面
                Intent addacc = new Intent(activity,AddAccActivity.class);
                startActivity(addacc);
                break;
            case R.id.acc_manage_clear_rel:
                SXUtils.getInstance(activity).deleteDir(SXUtils.getInstance(activity).getSDPath());
                SXUtils.getInstance(activity).ToastCenter("清除成功");
                break;
        }
    }
    ArrayList<AddressProvinceEntity> jsonBean = new ArrayList<>();
//    private ArrayList<String> options1Items = new ArrayList<>();
//    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
//    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
//    private void initJsonData() {//解析数据
//        /**
//         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
//         * 关键逻辑在于循环体
//         *
//         * */
//        String json = SXUtils.getInstance(activity).getFromAssets("areas.json");
//        jsonBean = (ArrayList<AddressProvinceEntity>) ResponseData.getInstance(activity).parseJsonArray(json.toString(), AddressProvinceEntity.class);
//        /**
//         * 添加省份数据
//         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
//         * PickerView会通过getPickerViewText方法获取字符串显示出来。
//         */
//
//        for (int i=0;i<jsonBean.size();i++){//遍历省份
//            options1Items.add(jsonBean.get(i).getLabel());
//            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
//            if(jsonBean.get(i).getChildren() != null) {
//                for (int c = 0; c < jsonBean.get(i).getChildren().size(); c++) {//遍历该省份的所有城市
//
//                    String CityName = jsonBean.get(i).getChildren().get(c).getLabel();
//                    CityList.add(CityName);//添加城市
//
//                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//
//                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
//                    if (jsonBean.get(i).getChildren().get(c).getChildren() != null) {
//                        if (jsonBean.get(i).getChildren().get(c).getLabel() == null
//                                || jsonBean.get(i).getChildren().get(c).getChildren().size() == 0) {
//                            City_AreaList.add("");
//                        } else {
//                            for (int d = 0; d < jsonBean.get(i).getChildren().get(c).getChildren().size(); d++) {//该城市对应地区所有数据
//                                String AreaName = jsonBean.get(i).getChildren().get(c).getChildren().get(d).getLabel();
//
//                                City_AreaList.add(AreaName);//添加该城市所有地区数据
//                            }
//                        }
//                    }
//                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//                }
//            }
//            /**
//             * 添加城市数据
//             */
//            options2Items.add(CityList);
//            /**
//             * 添加地区数据
//             */
//            options3Items.add(Province_AreaList);
//
//        }
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
//        pvOptions.show();
//    }
//    OptionsPickerView   pvOptions;
//    private void  init(){
//        pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                //返回的分别是三个级别的选中位置
////              String tx = options1Items.get(options1).getPickerViewText()
////                      + options2Items.get(options1).get(option2)
////                      + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
////              tvOptions.setText(tx);
//            }
//        })
//                .setSubmitText("确定")//确定按钮文字
//                .setCancelText("取消")//取消按钮文字
////                .setTitleText("城市选择")//标题
//                .setSubCalSize(18)//确定和取消文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
//                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(true)//设置是否联动，默认true
////                .setLabels("省", "市", "区")//设置选择的三级单位
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(false)//是否显示为对话框样式
//                .build();
//    }

    public void getLoginOutHttp(){
        RequestBody requestBody = new FormBody.Builder()
//                .add("mobile", mobile)
//                .add("vcode", codeStr)
//                .add("registerType", "0")//0=手机,1=微信,2=QQ
//                .add("password", psdStr)
//                .add("tag",Tag)
                .build();
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.USER_LOGINOUAt, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("退出登录发送成功返回参数=======",jsonObject.toString());
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
