package com.xianhao365.o2o.activity.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginNameActivity extends BaseActivity implements View.OnClickListener {
    private EditText loginAccPhoneEdt;
    private EditText loginAccPsdEdt;
    private Button loginAccNext;
    private TextView loginAccUsecodeTv;
    private TextView loginAccForgetpsdTv;
     private String   nameStr="",psdStr="";
    private Handler hand;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_name);
        activity = this;
        initView();
    }
    private void initView(){
        setTitle(getString(R.string.login_str));
        TextView backTv = (TextView) findViewById(R.id.all_title_back_tv);
        backTv.setOnClickListener(this);
        backTv.setBackgroundResource(R.mipmap.close);
        TextView  titleRight = (TextView) findViewById(R.id.all_title_right);
        titleRight.setText(getString(R.string.regist_str));
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setTextColor(getResources().getColor(R.color.qblue));
        titleRight.setOnClickListener(this);


        loginAccPhoneEdt = (EditText) findViewById(R.id.login_acc_phone_edt);
        loginAccPhoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameStr = s.toString();
                //生产环境需要放开 用于检验输入框参数是否合法
//                inputEditListener();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginAccPsdEdt = (EditText) findViewById(R.id.login_acc_psd_edt);
        loginAccPsdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                psdStr = s.toString();
                inputEditListener();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginAccNext = (Button) findViewById(R.id.login_acc_next);
        loginAccNext.setOnClickListener(this);
        loginAccUsecodeTv = (TextView) findViewById(R.id.login_acc_usecode_tv);
        loginAccUsecodeTv.setOnClickListener(this);
        loginAccForgetpsdTv = (TextView) findViewById(R.id.login_acc_forgetpsd_tv);
        loginAccForgetpsdTv.setOnClickListener(this);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    //登录成功
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("登录成功");
                        Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                        startActivity(mainintent);
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
            case R.id.login_acc_next:
                String phone = loginAccPhoneEdt.getText().toString();
                String psd = loginAccPsdEdt.getText().toString();
                AppClient.USERROLETAG = phone;

                Intent gomain = new Intent(activity, MainFragmentActivity.class);
                startActivity(gomain);
                finish();
//                SXUtils.showMyProgressDialog(this,false);
//                psdLoginHttp(phone,psd);
                break;
            case R.id.login_acc_usecode_tv:
                Intent intent = new Intent(LoginNameActivity.this, LoginCodeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_acc_forgetpsd_tv:
                Intent forget = new Intent(LoginNameActivity.this, ForGetPsdActivity.class);
                startActivity(forget);
                break;
            case R.id.all_title_right:
                Intent regist = new Intent(LoginNameActivity.this, RegistCheckActivity.class);
                startActivity(regist);
                break;
            case R.id.all_title_back_tv:
                Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                startActivity(mainintent);
                finish();
                break;
        }
    }
    /**
     * 判断三个参数是否满足条件才显示注册按钮
     */
    private void inputEditListener(){
        if(nameStr.equals("") || nameStr.length() <11 || psdStr.equals("") ||psdStr.length()<6){
            loginAccNext.setEnabled(false);
            loginAccNext.setBackgroundResource(R.drawable.gray_round_shap);
        }else{
            loginAccNext.setEnabled(true);
            loginAccNext.setBackgroundResource(R.drawable.login_button_selector);
        }
    }
    public void psdLoginHttp(String mobile,String psdStr){
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password",psdStr)
                .add("loginType","1")//0=验证码登录,1=密码登录
                .build();
        new OKManager(this).sendStringByPostMethod(requestBody, AppClient.USER_LOGIN, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("密码登录发送成功返回参数=======",jsonObject.toString());
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                    AppClient.USER_ID = jsonObject1.getString("uid");
                    AppClient.USER_SESSION = jsonObject1.getString("sid");
                    AppClient.USERROLETAG = jsonObject1.getString("tag");
                } catch (JSONException e) {
                    e.printStackTrace();
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
