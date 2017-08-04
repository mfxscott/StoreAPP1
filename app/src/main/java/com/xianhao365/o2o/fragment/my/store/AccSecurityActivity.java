package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
 * 账号管理
 */
public class AccSecurityActivity extends BaseActivity {
    private Activity activity;
    private Handler hand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_security);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");

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
