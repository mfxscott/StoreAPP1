package com.xianhao365.o2o.fragment.my.buyer.purchase;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商采购订单发货流程及相关参数填写
 */
public class CGOrderDeliveActivity extends BaseActivity {
    private Handler hand;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgorder_delive);
        activity = this;
        initView();
    }
    private void initView(){
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        break;
                    case 1001:
                        break;
                    case AppClient.ERRORCODE:
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                return true;
            }
        });
    }
    /**
     *供应商确认采购订单采购
     */
    public void getPurchaseDeliveHttp(String purchaseCode) {
        HttpParams params = new HttpParams();
        params.put("purchaseCode",purchaseCode);//
        params.put("vehicleNo",purchaseCode);//车牌号
        params.put("driverName",purchaseCode);//司机
        params.put("driverPhone","");//司机电话
        params.put("sendTime",SXUtils.getInstance(activity).GetNowDateTime());//发货时间
        params.put("sendAddr",purchaseCode);//发货地址
        params.put("senderPhone",purchaseCode);//发货人联系方式
        params.put("senderName",purchaseCode);//发货人名称
//        params.put("attachFiles",purchaseCode);//司机和货车照片
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("skuCode","123");
            jsonObject.put("actualNumber","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<File> fileList = new ArrayList<>();
        File f = new File("1111");
        fileList.add(f);
        params.put("purchaseLines",jsonObject.toString());//采购明细
        HttpUtils.getInstance(activity).requestFilePost(false, AppClient.GYS_CPURCHASE_DELIVER, "attachFiles",params,fileList, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = jsonObject.toString();
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
