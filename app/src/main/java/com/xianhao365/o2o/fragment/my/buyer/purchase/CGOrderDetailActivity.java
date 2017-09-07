package com.xianhao365.o2o.fragment.my.buyer.purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.CGOrderDetailRecyclerViewAdapter;
import com.xianhao365.o2o.entity.cgListInfo.CGListInfoEntity;
import com.xianhao365.o2o.entity.cgListInfo.CGPurchaseLinesEntity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购订单详情
 * @author mfx
 * @time  2017/9/1 17:57
 */
public class CGOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private String orderTag;
    @BindView(R.id.cg_order_detail_take_btn)
    TextView  takeOrder;
    private Activity activity;
    private CGListInfoEntity cgListInfo;
    private List<CGPurchaseLinesEntity> purchaseList;
    @BindView(R.id.cg_order_detail_total_tv)
    TextView  totalTv;
    @BindView(R.id.cg_order_get_time_tv)
    TextView  getTimeTv;
    @BindView(R.id.cg_order_detail_send_addr_tv)
    TextView  sendAddrTv;
    @BindView(R.id.cg_order_detail_get_preson_tv)
    TextView  getPresonTv;
    @BindView(R.id.cg_order_detail_recycler)
    RecyclerView recycler;
    @BindView(R.id.cg_order_detail_no_tv)
    TextView  purCodeTv;
    private Handler hand;
    private String purchaseCodeStr,actualNumberStr,skuCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cg_order_detail);
        activity = this;
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        ArrayList list = bundle.getParcelableArrayList("PurchaseList");
        cgListInfo = bundle.getParcelable("orderList");
        purchaseList= (List<CGPurchaseLinesEntity>) list.get(0);//强转成你自己定义的list，这样list2就是你传过来的那个list了。
        initView();
    }
    private void initView(){
        totalTv.setText(cgListInfo.getPurchaseAmount()+"");
        getTimeTv.setText(cgListInfo.getReceiveTime()+"");
        sendAddrTv.setText(cgListInfo.getReceiverAddr()+"");
        getPresonTv.setText(cgListInfo.getReceiver()+"");
        purCodeTv.setText("采购单号:"+cgListInfo.getPurchaseCode());

        purchaseCodeStr = cgListInfo.getPurchaseCode();
        actualNumberStr= purchaseList.get(0).getActualNumber();
        skuCodeStr = purchaseList.get(0).getSkuCode();

        takeOrder.setOnClickListener(this);
        registerBack();
        setTitle("采购订单详情");

        recycler.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        CGOrderDetailRecyclerViewAdapter simpAdapter = new CGOrderDetailRecyclerViewAdapter(this,purchaseList);
        recycler.setAdapter(simpAdapter);
        switch (Integer.parseInt(cgListInfo.getReceiveState())){
            case 10:
                takeOrder.setText("确认订单");
                break;
            case 20:
                takeOrder.setText("立即发货");
                break;
            case 30:
                takeOrder.setText("已收货");
                takeOrder.setTextColor(getResources().getColor(R.color.orange));
                takeOrder.setBackgroundResource(R.color.transparent);
                break;
            case 44:
                takeOrder.setText("已完成");
                takeOrder.setTextColor(getResources().getColor(R.color.orange));
                takeOrder.setBackgroundResource(R.color.transparent);
                break;
        }
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cg_order_detail_take_btn:
                switch (Integer.parseInt(cgListInfo.getReceiveState())) {
                    case 10:
                        getConfirmPurchaseHttp(cgListInfo.getPurchaseCode());
                        break;
                    case 20:
                        Intent intent = new Intent(activity, CGOrderDeliveActivity.class);
                        intent.putExtra("code",cgListInfo.getPurchaseCode());
                        intent.putExtra("num",purchaseList.get(0).getActualNumber());
                        intent.putExtra("skucode",purchaseList.get(0).getSkuCode());
                        startActivity(intent);
                        break;
                }
                break;
        }
    }
    /**
     *供应商确认采购订单采购
     */
    public void getConfirmPurchaseHttp(String purchaseCode) {
        HttpParams params = new HttpParams();
        params.put("purchaseCode",purchaseCode);
        HttpUtils.getInstance(activity).requestPost(false, AppClient.GYS_CONFIRM_PURCHASE, null, new HttpUtils.requestCallBack() {
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
