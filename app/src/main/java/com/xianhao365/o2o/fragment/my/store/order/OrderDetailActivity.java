package com.xianhao365.o2o.fragment.my.store.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.activity.GoodsDetailActivity;
import com.xianhao365.o2o.adapter.WaitPayGoodsRecyclerViewAdapter;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.entity.orderlist.OrderGoodsInfoEntity;
import com.xianhao365.o2o.fragment.my.pay.TopUpActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private String orderTag,orderId,orderAddress,orderTime,orderTotal,name;
    private TextView takeOrder,cancelTv,cancelOrder;
    private LinearLayout  btnLin;
    private  Activity activity;
    private RecyclerView recyclerView;
    private List<OrderGoodsInfoEntity> orderGoodsList;
    @BindView(R.id.order_detail_name_tv)
    TextView nameTv;
    @BindView(R.id.order_detail_total_tv)
    TextView orderTotalTv;
    @BindView(R.id.order_detail_no_tv)
    TextView orderNoTv;
    @BindView(R.id.order_detail_time_tv)
    TextView orderTimeTv;
    @BindView(R.id.order_detail_paytype_tv)
    TextView orderPayTypeTv;
    @BindView(R.id.order_detail_address_tv)
    TextView orderAddressTv;
    @BindView(R.id.order_detail_pay_total_tv)
    TextView payTotalTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        activity = this;
        ButterKnife.bind(activity);
        orderTag = this.getIntent().getStringExtra("orderTag");
//        orderId = this.getIntent().getStringExtra("orderId");
        Bundle bundle = this.getIntent().getExtras();
        ArrayList list = bundle.getParcelableArrayList("orderDetail");
        orderId = bundle.getString("orderNo");
        orderAddress = bundle.getString("orderAddress");
        orderTime = bundle.getString("orderTime");
        name = bundle.getString("name");
        orderTotal = bundle.getString("total");
        orderGoodsList= (List<OrderGoodsInfoEntity>) list.get(0);//强转成你自己定义的list，这样list2就是你传过来的那个list了。
        initView();
        getOrderDetailHttp();
    }
    private void initView(){
        nameTv.setText(name);
        orderTotalTv.setText("总价："+orderTotal);
        orderTimeTv.setText(orderTime);
        orderNoTv.setText(orderId);
        orderPayTypeTv.setText("");
        orderAddressTv.setText(orderAddress);
        payTotalTv.setText("合计："+orderTotal);
        recyclerView = (RecyclerView) findViewById(R.id.order_detail_item_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final WaitPayGoodsRecyclerViewAdapter simpAdapter = new WaitPayGoodsRecyclerViewAdapter(activity,orderGoodsList);
        recyclerView.setAdapter(simpAdapter);

        takeOrder = (TextView) findViewById(R.id.order_detail_wait_pay_take_btn);
        takeOrder.setOnClickListener(this);
        btnLin = (LinearLayout) findViewById(R.id.order_detail_btn_lin);
        cancelTv = (TextView) findViewById(R.id.order_detail_done_tv);
        cancelOrder = (TextView) findViewById(R.id.order_detail_wait_pay_cancel_btn);
        cancelOrder.setOnClickListener(this);
        RelativeLayout   rel1 = (RelativeLayout) findViewById(R.id.order_detail_wait_pay_rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.order_detail_wait_pay_rel2);
        rel1.setOnClickListener(this);
        rel2.setOnClickListener(this);
        registerBack();
        setTitle("订单详情");
        switch (Integer.parseInt(orderTag)){
            case 1:
                btnLin.setVisibility(View.VISIBLE);
                takeOrder.setText("立刻付款");
                takeOrder.setBackgroundResource(R.drawable.comfirm_take_selector);
                break;
            case 2:
                btnLin.setVisibility(View.VISIBLE);
                takeOrder.setText("提醒发货");
                takeOrder.setTextColor(activity.getResources().getColor(R.color.orange));
                takeOrder.setBackgroundResource(R.drawable.cancel_order_selector);
                break;
            case 3:
                btnLin.setVisibility(View.VISIBLE);
                takeOrder.setText("确定收货");
                takeOrder.setBackgroundResource(R.drawable.comfirm_take_selector);
                break;
            case 4:
                btnLin.setVisibility(View.GONE);
                cancelTv.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_detail_wait_pay_take_btn:
                switch (Integer.parseInt(orderTag)) {
                    case 1:
                        Intent pay = new Intent(activity, TopUpActivity.class);
                        pay.putExtra("payTag","1");
                        pay.putExtra("paySum","1000");
                        startActivity(pay);
                        break;
                    case 2:
                        SXUtils.getInstance(activity).ToastCenter("提醒发货");
                        break;
                    case 3:
                        SXUtils.getInstance(activity).ToastCenter("确认发货");
                        break;
                    case 4:
                        break;
                }
                break;
            case R.id.order_detail_wait_pay_cancel_btn:
                SXUtils.getInstance(activity).ToastCenter("取消订单");
                break;
            case R.id.order_detail_wait_pay_rel1:case R.id.order_detail_wait_pay_rel2:
                Intent intent = new Intent(activity, GoodsDetailActivity.class);
                startActivity(intent);
                break;
        }
    }
    public Handler hand = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
//                        List<CGListInfoEntity> gde = (List<CGListInfoEntity>) msg.obj;
//                    List<OrderInfoEntity> gde = (List<OrderInfoEntity>) msg.obj;

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
    /**
     * 获取订单详情
     */
    public void getOrderDetailHttp() {
        HttpParams params = new HttpParams();
        params.put("orderNo",orderId);
        HttpUtils.getInstance(activity).requestPost(false, AppClient.ORDER_DETAIL, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                UserInfoEntity gde = null;
                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),UserInfoEntity.class);
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
}
