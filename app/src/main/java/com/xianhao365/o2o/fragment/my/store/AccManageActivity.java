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

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 账号管理
 */
public class AccManageActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;
    private Handler hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_acc_manage);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");
        ImageView headimg = (ImageView) findViewById(R.id.acc_manage_headimg);
        Glide.with(activity).load(AppClient.headImg).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity, 60)).into(headimg);
        LinearLayout accinfolin = (LinearLayout) findViewById(R.id.acc_manage_info_lin);
        accinfolin.setOnClickListener(this);
        Button loginOut = (Button) findViewById(R.id.login_out_btn);
        loginOut.setOnClickListener(this);
        RelativeLayout  rel = (RelativeLayout) findViewById(R.id.acc_manage_security_rel);
        rel.setOnClickListener(this);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("退出成功");
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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_out_btn:
                SXUtils.getInstance(activity).ToastCenter("sdf");
                break;
            case R.id.acc_manage_info_lin:
                Intent intent = new Intent(activity,AccInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.acc_manage_security_rel:
                Intent sec = new Intent(activity,AccSecurityActivity.class);
                startActivity(sec);
                break;
        }
    }
    public void RegistHttp(){
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
