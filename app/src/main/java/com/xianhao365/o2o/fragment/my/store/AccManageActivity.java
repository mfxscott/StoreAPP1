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
import com.xianhao365.o2o.fragment.my.store.yhj.AddAccActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 账号管理
 */
public class AccManageActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;
    private Handler hand;
    private UserInfoEntity userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_acc_manage);
        Bundle bundle = this.getIntent().getExtras();
        userinfo =(UserInfoEntity)bundle.getParcelable("userinfo");
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");
        ImageView headimg = (ImageView) findViewById(R.id.acc_manage_headimg);
        Glide.with(activity).load(userinfo.getShopLogo()).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity)).into(headimg);
        LinearLayout accinfolin = (LinearLayout) findViewById(R.id.acc_manage_info_lin);
        accinfolin.setOnClickListener(this);
        TextView tvname = (TextView) findViewById(R.id.acc_manage_name_tv);



        Button loginOut = (Button) findViewById(R.id.login_out_btn);
        loginOut.setOnClickListener(this);
        RelativeLayout  rel = (RelativeLayout) findViewById(R.id.acc_manage_security_rel);
        rel.setOnClickListener(this);
        RelativeLayout addacc = (RelativeLayout) findViewById(R.id.acc_manage_addson_rel);
        addacc.setOnClickListener(this);
        if(AppClient.USERROLETAG.equals("64") || AppClient.USERROLETAG.equals("4") ){
            tvname.setText(userinfo.getAcount()+"");
        }else{
            tvname.setText(userinfo.getShopName()+"");
            addacc.setVisibility(View.VISIBLE);
        }
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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_out_btn:
                SXUtils.showMyProgressDialog(activity,false);
                getLoginOutHttp();
                break;
            case R.id.acc_manage_info_lin:
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
        }
    }
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
