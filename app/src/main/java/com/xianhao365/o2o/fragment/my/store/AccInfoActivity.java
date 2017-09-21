package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.entity.address.AddressProvinceEntity;
import com.xianhao365.o2o.fragment.my.pop.DatePickerPopWin;
import com.xianhao365.o2o.fragment.my.pop.MyWheelView;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import java.util.List;

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
        userinfo =(UserInfoEntity)bundle.getParcelable("userinfo");
        activity = this;
        String json = SXUtils.getInstance(activity).getFromAssets("areas.txt");
        Logs.i("=======json==="+json);

        initView();
    }
    private void initView() {
        registerBack();
        setTitle("账户信息");
        ImageView headimg = (ImageView) findViewById(R.id.acc_info_headimg);
        Glide.with(activity).load(userinfo.getIcon()).placeholder(R.mipmap.loading_img)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity)).into(headimg);
        acount.setText(userinfo.getAcount()+"");
        if(AppClient.USERROLETAG.equals("64")){
        userLin.setVisibility(View.VISIBLE);
        accInfoUsernameEdt.setText(userinfo.getUsername());
        accInfoPersonPhoneEdt.setText(userinfo.getMobile());
        accInfoAddressEdt.setText(userinfo.getProvince()+userinfo.getCity()+userinfo.getDistrict()+userinfo.getAddr());
        }else{
            storeLin.setVisibility(View.VISIBLE);
            storeAdd.setText(userinfo.getProvince()+userinfo.getCity()+userinfo.getDistrict()+userinfo.getAddr());
            storefzr.setText(userinfo.getManager());
            storeId.setText("");
            storeInfo.setText("");
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
                String json = SXUtils.getInstance(activity).getFromAssets("areas.json");
                List<AddressProvinceEntity> addressList = ResponseData.getInstance(activity).parseJsonArray(json.toString(), AddressProvinceEntity.class);
            DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(activity, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        Toast.makeText(activity, dateDesc, Toast.LENGTH_SHORT).show();
                    }
                }).textConfirm("CONFIRM") //text of confirm button
                        .textCancel("CANCEL") //text of cancel button
                        .btnTextSize(14) // button text size
                        .viewTextSize(14) // pick view text size
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .minYear(1990) //min year in loop
                        .maxYear(2550) // max year in loop
                        .dateChose("2013-11-11") // date chose when init popwindow
                        .build();
                pickerPopWin.showPopWin(activity);
            }
        });
        storeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = SXUtils.getInstance(activity).getFromAssets("areas.json");
                List<AddressProvinceEntity> addressList = ResponseData.getInstance(activity).parseJsonArray(json.toString(), AddressProvinceEntity.class);
                // 构建弹出框View
                View outerView = LayoutInflater.from(activity)
                        .inflate(R.layout.wheel_view, null);

                MyWheelView wv = (MyWheelView) outerView
                        .findViewById(R.id.wheel_view_wv);
                // wv.setOffset(0);// 偏移量
                wv.setOffset(2);
                wv.setItems(addressList);// 实际内容
                wv.setSeletion(0);// 设置默认被选中的项目
                // wv.setSeletion(3);
                wv.setOnWheelViewListener(new MyWheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        // 选中后的处理事件
                        Log.d("====", "[Dialog]selectedIndex: " + selectedIndex
                                + ", item: " + item);
                    }
                });

                // 展示弹出框
                new AlertDialog.Builder(activity)
                        .setTitle("WheelView in Dialog").setView(outerView)
                        .setPositiveButton("OK", null).show();
            }
        });
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
