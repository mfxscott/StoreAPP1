package com.xianhao365.o2o.activity.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginCodeActivity extends BaseActivity implements OnClickListener{
    private EditText loginInputPhoneEdt;
    private TextView loginGetcodeTv;
    private EditText loginCodeEdt;
    private TextView loginHintSendCodeTv;
    private Button loginNext;
    private TextView loginUsePsdLoginTv;

    private String  phoneStr="",codeStr="";
    private MyCountDownTimer mc;
    private Handler hand;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);
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
        titleRight.setTextColor(this.getResources().getColor(R.color.qblue));
        titleRight.setOnClickListener(this);

        loginInputPhoneEdt = (EditText) findViewById(R.id.login_input_phone_edt);
        loginInputPhoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneStr = s.toString();
                inputEditListener();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginGetcodeTv = (TextView) findViewById(R.id.login_getcode_tv);
        loginGetcodeTv.setOnClickListener(this);
        loginCodeEdt = (EditText) findViewById(R.id.login_code_edt);
        loginCodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                codeStr = s.toString();
                inputEditListener();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        loginHintSendCodeTv = (TextView) findViewById(R.id.login_hint_send_code_tv);
        loginHintSendCodeTv.setOnClickListener(this);
        loginNext = (Button) findViewById(R.id.login_next);
        loginNext.setOnClickListener(this);
        loginUsePsdLoginTv = (TextView) findViewById(R.id.login_use_psd_login_tv);
        loginUsePsdLoginTv.setOnClickListener(this);

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("登录成功");
                        Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                        startActivity(mainintent);
                        finish();
                        break;
                    //验证码发送成功
                    case AppClient.GETCODEMSG:
                        mc = new MyCountDownTimer(10*6000, 1000);
                        mc.start();
                        loginGetcodeTv.setEnabled(false);
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
    private String mobilestr;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_next:
                String codestr = loginCodeEdt.getText().toString();
                SXUtils.showMyProgressDialog(activity,true);
                codeLoginHttp(mobilestr,codestr);
                break;
            case R.id.login_getcode_tv:
                mobilestr = loginInputPhoneEdt.getText().toString();
                if(!TextUtils.isEmpty(mobilestr) && mobilestr.length() == 11 && mobilestr.substring(0,1).equals("1")){
                    SXUtils.showMyProgressDialog(activity,true);
                    SXUtils.getInstance(activity).getCodeMsgHttp(activity,mobilestr,"1",hand);
                }
                else{
                    SXUtils.getInstance(activity).ToastCenter("输入手机格式不正确");
                }
                break;
            case R.id.login_use_psd_login_tv:
                Intent intent = new Intent(LoginCodeActivity.this, LoginNameActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.all_title_right:
                Intent regist = new Intent(LoginCodeActivity.this, RegistCheckActivity.class);
                startActivity(regist);
                break;
            case R.id.all_title_back_tv:
                Intent mainintent = new Intent(activity, MainFragmentActivity.class);
                startActivity(mainintent);
                break;
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
            loginGetcodeTv.setEnabled(true);
            loginGetcodeTv.setTextColor(getColor(R.color.col_hint));
            loginGetcodeTv.setText(getString(R.string.get_code_str));
        }
        @Override
        public void onTick(long millisUntilFinished) {
            loginHintSendCodeTv.setVisibility(View.VISIBLE);
            loginGetcodeTv.setTextColor(getResources().getColor(R.color.qblue));
            loginGetcodeTv.setText(getString(R.string.regist_send_code_yes_str)+millisUntilFinished / 1000+getString(R.string.regist_second_str));
        }
    }
    /**
     * 判断三个参数是否满足条件才显示注册按钮
     */
    private void inputEditListener(){
        if(phoneStr.equals("") || phoneStr.length() <11 || codeStr.equals("") ||codeStr.length()<6){
            loginNext.setEnabled(false);
            loginNext.setBackgroundResource(R.drawable.gray_round_shap);
        }else{
            loginNext.setEnabled(true);
            loginNext.setBackgroundResource(R.drawable.login_button_selector);
        }
    }
    public void codeLoginHttp(String mobile,String codeStr){
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password",codeStr)
                .add("loginType","0")//0=验证码登录,1=密码登录
                .add("type", "1")//拉取类型(1=登录,2=注册,3=忘记密码,4=安全密码)
                .build();

        new OKManager(this).sendStringByPostMethod(requestBody, AppClient.USER_LOGIN, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("code登录发送成功返回参数=======",jsonObject.toString());
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

