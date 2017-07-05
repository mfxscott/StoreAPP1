package com.scott.shopplat.fragment.my.user;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scott.shopplat.R;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * ***************************
 * 初始我的界面
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 17/2/16 上午11:56
 * ***************************
 */
public class MyFragment extends Fragment implements View.OnClickListener{
    private  View view;
    private Activity activity;
    private OKManager manager;
    private Handler hand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        activity = getActivity();
        manager = new OKManager(activity);
//        SXUtils.getInstance().setSysStatusBar(activity,R.color.red);
        init();
        return view;
    }
    //初始化
    private void init(){
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case AppClient.AUTHINFO:
                        break;
                }
                return true;
            }
        });
//        if(!TextUtils.isEmpty(AppClient.TOKENSTR)){
//            SelectBalance();
//            RequestReqMsgData.SelectAuth(activity,hand);
//            RequestReqMsgData.selectUserMsg(activity,hand);
//        }
    }
    @Override
    public void onClick(View view) {

}
    /**
     * 账号余额查询
     */
    private void  SelectBalance(){
        RequestBody requestBody = new FormBody.Builder()
                .add("registerType", "0")//0=手机,1=微信,2=QQ
                .build();
        manager.sendStringByPostMethod(requestBody,"memberbalanceandotherRspMsg", new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
//                JSONObject msgRspJson;
//                JSONObject repmsg;
//                JSONObject msgrsp;
//                try {
//                    repmsg = new JSONObject(jsonObject.toString());
//                    msgrsp = repmsg.getJSONObject("memberbalanceandotherRspMsg");
//                    msgRspJson = msgrsp.getJSONObject("msgrsp");
//                    totalassetsStr = msgRspJson.getString("totalassets");//总账号总余额
//                    balanceStr = msgRspJson.getString("balance");//余额
//                    freezingbalanceStr = msgRspJson.getString("freezingbalance");//冻结余额
//                    sxcbalance = msgRspJson.getString("sxcbalance");//随心存
//                    cfcbalance = msgRspJson.getString("cfcbalance");//财富存
//                    useBalance = msgRspJson.getString("availblebalance");//三峡付可用余额
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                hand.sendEmptyMessage(2);
                Logs.i("登录返回返回结果=======",jsonObject.toString()+"");
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