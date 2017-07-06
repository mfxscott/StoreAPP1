package com.scott.shopplat.activity.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.fragment.CommonWebViewMainActivity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class Regis1Activity extends BaseActivity implements View.OnClickListener{
    private EditText registInputPhoneEdt;
    private TextView registGetcodeTv;
    private EditText registCodeEdt;
    private EditText registInputPsdEdt;
    private CheckBox registSeeTv;
    private Button  registBtn;
    private TextView sendCodeHintTv;//提示验证码发送
    private String  phoneStr="",codeStr="",psdStr="";
    private MyCountDownTimer mc;
    private TextView registRuleTv;//注册条款
    private Activity activity;
    private Handler   hand;
    private String Tag; //64 个人 32 商户判断用户商户还是个人注册
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis1);
        Tag = this.getIntent().getStringExtra("registRole");
        activity = this;
        initView();

    }
    private void initView(){
        registerBack();
        setTitle(getString(R.string.regist_str));
        registRuleTv = (TextView) findViewById(R.id.regist_rule_tv);
        registRuleTv.setOnClickListener(this);
        sendCodeHintTv = (TextView) findViewById(R.id.regist_hint_send_code_tv);
        registInputPhoneEdt = (EditText) findViewById(R.id.regist_input_phone_edt);
        registInputPhoneEdt.addTextChangedListener(new TextWatcher() {
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
        registGetcodeTv = (TextView) findViewById(R.id.regist_getcode_tv);
        registGetcodeTv.setOnClickListener(this);
        registCodeEdt = (EditText) findViewById(R.id.regist_code_edt);
        registCodeEdt.addTextChangedListener(new TextWatcher() {
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
        registInputPsdEdt = (EditText) findViewById(R.id.regist_input_psd_edt);
        registInputPsdEdt.addTextChangedListener(new TextWatcher() {
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
        registBtn = (Button) findViewById(R.id.regist_next);
        registBtn.setOnClickListener(this);
        registSeeTv = (CheckBox) findViewById(R.id.regist_see_tv);
        registSeeTv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//选择状态 显示明文--设置为可见的密码
                    registInputPsdEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
//默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    registInputPsdEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        if(LoginNameActivity.activity != null)
                            LoginNameActivity.activity.finish();
                        if(LoginCodeActivity.activity != null)
                            LoginCodeActivity.activity.finish();
                        SXUtils.getInstance(activity).ToastCenter("注册成功"+"");

                        if(Tag.equals("64")){
                            Intent intent = new Intent(activity,LoginNameActivity.class);
                            startActivity(intent);
                        }else {
                            Intent aa = new Intent(activity, StoreMapActivity.class);
                            activity.startActivity(aa);
                        }
                        finish();
                        break;
                    //验证码发送成功
                    case AppClient.GETCODEMSG:
                        String msgstr = (String) msg.obj;
                        try {
                            JSONObject jsonObject = new JSONObject(msgstr.toString());
                            AppClient.TAG   = jsonObject.getString("secs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mc = new MyCountDownTimer(10*6000, 1000);
                        mc.start();
                        registGetcodeTv.setEnabled(false);
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
    String mobilestr;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist_next:
                String codemsg = registCodeEdt.getText().toString();
                String psdstr = registInputPsdEdt.getText().toString();
                SXUtils.showMyProgressDialog(activity,true);
                RegistHttp(mobilestr,psdstr,codemsg);
                break;
            case R.id.regist_getcode_tv:
                mobilestr = registInputPhoneEdt.getText().toString();
                if(!TextUtils.isEmpty(mobilestr) && mobilestr.length() == 11 && mobilestr.substring(0,1).equals("1")){
                    SXUtils.showMyProgressDialog(activity,true);
                    SXUtils.getInstance(activity).getCodeMsgHttp(activity,mobilestr,"2",hand);
                }
                else{
                    SXUtils.getInstance(activity).ToastCenter("输入手机格式不正确");
                }
                break;
            case R.id.regist_rule_tv:
                Intent intent = new Intent(Regis1Activity.this, CommonWebViewMainActivity.class);
                intent.putExtra("tag","2");
                intent.putExtra("postUrl","http:www.baidu.com");
                startActivity(intent);
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
            registGetcodeTv.setEnabled(true);
            registGetcodeTv.setTextColor(getColor(R.color.col_hint));
            registGetcodeTv.setText(getString(R.string.get_code_str));
        }
        @Override
        public void onTick(long millisUntilFinished) {
            sendCodeHintTv.setVisibility(View.VISIBLE);
            registGetcodeTv.setTextColor(getColor(R.color.qblue));
            registGetcodeTv.setText(getString(R.string.regist_send_code_yes_str)+millisUntilFinished / 1000+getString(R.string.regist_second_str));
        }
    }

    /**
     * 判断三个参数是否满足条件才显示注册按钮
     */
    private void inputEditListener(){
        if(phoneStr.equals("") || phoneStr.length() <11 || codeStr.equals("") ||codeStr.length()<6 ||psdStr.equals("") || psdStr.length()<6){
            registBtn.setEnabled(false);
            registBtn.setBackgroundResource(R.drawable.gray_round_shap);
        }else{
            registBtn.setEnabled(true);
            registBtn.setBackgroundResource(R.drawable.login_button_selector);
        }
    }
    public void RegistHttp(String mobile,String psdStr,String codeStr){

        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("vcode", codeStr)
                .add("registerType", "0")//0=手机,1=微信,2=QQ
                .add("password", psdStr)
                .add("tag",Tag)
                .build();
        Logs.i("注册请求字段===","=="+mobile+"=="+codeStr+"==="+psdStr+"===="+Tag);
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.USER_REGIST, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("注册发送成功返回参数=======",jsonObject.toString());
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
