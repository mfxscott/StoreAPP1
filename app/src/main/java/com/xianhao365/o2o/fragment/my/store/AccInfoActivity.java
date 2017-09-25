package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.entity.address.AddressProvinceEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.xianhao365.o2o.R.id.acc_info_update_btn;

/**
 * 账号信息
 */
public class AccInfoActivity extends BaseActivity {
    @BindView(R.id.acc_info_username_edt)
    EditText accInfoUsernameEdt;
    @BindView(R.id.acc_info_address_edt)
    TextView accInfoAddressEdt;
    @BindView(R.id.acc_info_person_edt)
    EditText accInfoPersonEdt;
    @BindView(R.id.acc_info_person_phone_edt)
    EditText accInfoPersonPhoneEdt;
    @BindView(acc_info_update_btn)
    Button accInfoUpdateBtn;
    @BindView(R.id.acc_info_name_tv)
    TextView acount;
    @BindView(R.id.acc_userinfo_update_liny)
    LinearLayout userLin;
    //店铺信息
    @BindView(R.id.acc_store_add_edt)
    TextView storeAdd;
    @BindView(R.id.acc_store_fz_edt)
    EditText storefzr;
    @BindView(R.id.acc_store_id_edt)
    EditText storeId;
    @BindView(R.id.acc_store_info_edt)
    EditText storeInfo;
    @BindView(R.id.acc_storeinfo_update_liny)
    LinearLayout storeLin;

    private Activity activity;
    private Handler hand;
    private UserInfoEntity userinfo;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_info);
        unbinder = ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        userinfo = (UserInfoEntity) bundle.getParcelable("userinfo");
        activity = this;
        ButterKnife.bind(this);
        String json = SXUtils.getInstance(activity).getFromAssets("areas.txt");
        Logs.i("=======json===" + json);

        initView();
         PopViewPrick();
    }

    private void initView() {
        registerBack();
        setTitle("账户信息");
        ImageView headimg = (ImageView) findViewById(R.id.acc_info_headimg);
        if(userinfo != null) {
            Glide.with(activity).load(userinfo.getIcon()).placeholder(R.mipmap.loading_img)
                    .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity)).into(headimg);
            acount.setText(userinfo.getAcount() + "");
            if (AppClient.USERROLETAG.equals("64")) {
                userLin.setVisibility(View.VISIBLE);
                accInfoUsernameEdt.setText(userinfo.getUsername());
                accInfoPersonPhoneEdt.setText(userinfo.getMobile());
                accInfoAddressEdt.setText(userinfo.getProvince() + userinfo.getCity() + userinfo.getDistrict() + userinfo.getAddr());
            } else {
                storeLin.setVisibility(View.VISIBLE);
                storeAdd.setText(userinfo.getProvince() + userinfo.getCity() + userinfo.getDistrict() + userinfo.getAddr());
                storefzr.setText(userinfo.getManager());
                storeId.setText("");
                storeInfo.setText("");
            }
        }
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("退出成功");
                        finish();
                        break;
                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg + "");
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
        accInfoAddressEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(activity).addressPickerPopView(pvOptions);
            }
        });
        storeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(activity).addressPickerPopView(pvOptions);

            }
        });
    }
    OptionsPickerView pvOptions;
    private void  PopViewPrick(){
        pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                ArrayList<AddressProvinceEntity>   jsonBean = SXUtils.getInstance(activity).getAddress();
//                SXUtils.getInstance(activity).ToastCenter(jsonBean.get(options1).getLabel());
//                SXUtils.getInstance(activity).ToastCenter(jsonBean.get(options1).getChildren().get(option2).getLabel());
//                SXUtils.getInstance(activity).ToastCenter(jsonBean.get(options1).getChildren().get(option2).getChildren().get(options3).getLabel());
                //返回的分别是三个级别的选中位置
              String tx = jsonBean.get(options1).getLabel()
                      + jsonBean.get(options1).getChildren().get(option2).getLabel()
                      + jsonBean.get(options1).getChildren().get(option2).getChildren().get(options3).getLabel();
//              tvOptions.setText(tx);
                storeAdd.setText(tx+"");
                accInfoAddressEdt.setText(tx+"");
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
//                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
    }

    /**
     * 获取用户余额
     */
    public void GetUserWalletHttp(HttpParams params) {
        HttpUtils.getInstance(activity).requestPost(false, AppClient.UPDATE_USER_INFO, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                UserInfoEntity gde = null;
                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(), UserInfoEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = gde;
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

    @OnClick(acc_info_update_btn)
    public void onViewClicked() {
        String name = accInfoUsernameEdt.getText().toString().trim();
        String address = accInfoUsernameEdt.getText().toString().trim();
        String person = accInfoUsernameEdt.getText().toString().trim();
        String phone = accInfoUsernameEdt.getText().toString().trim();
        HttpParams httpP =new HttpParams();
        httpP.put("username",name);
        httpP.put("sex","");
        httpP.put("mobile",phone);
        httpP.put("email","");
        httpP.put("nickname",person);
        httpP.put("iconFile","");
        httpP.put("addr",address);
        httpP.put("","");
        GetUserWalletHttp(httpP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
