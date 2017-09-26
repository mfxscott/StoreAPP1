package com.xianhao365.o2o.fragment.car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.CGOrderGoodsListRecyclerViewAdapter;
import com.xianhao365.o2o.entity.address.AddressInfoEntity;
import com.xianhao365.o2o.entity.car.FromOrderEntity;
import com.xianhao365.o2o.entity.car.OrderCouponsEntity;
import com.xianhao365.o2o.entity.car.OrderLinesEntity;
import com.xianhao365.o2o.entity.car.PayTypeEntity;
import com.xianhao365.o2o.fragment.my.store.TopUpActivity;
import com.xianhao365.o2o.fragment.my.store.yhj.YHJActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品预下单成功去支付界面
 */
public class GoPayActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;
    FromOrderEntity fromOrder;
    @BindView(R.id.gopay_address_name_tv)
    TextView addressName;
    @BindView(R.id.gopay_address_phone_tv)
    TextView  addressPhone;
    @BindView(R.id.gopay_address_info_tv)
    TextView  addressInfo;
    @BindView(R.id.go_pay_submit_order_recycler)
    RecyclerView recycler;
    @BindView(R.id.go_pay_price_tv)
    TextView  priceTv;
    @BindView(R.id.go_pay_total_tv)
    TextView  totalPriceTv;
    @BindView(R.id.go_pay_freightAmount_tv)
    TextView  freightAmountTv;//运费
    @BindView(R.id.gopay_getto_rel)
    RelativeLayout getToPayRely;
    @BindView(R.id.gopay_online_rel)
    RelativeLayout onLineRely;
    @BindView(R.id.gopay_online_iv)
    ImageView onlineTv;
    @BindView(R.id.gopay_getto_iv)
    ImageView gottoTv;
    @BindView(R.id.go_pay_coupons_tv)
    TextView  couponsTv;
    @BindView(R.id.go_pay_goods_num_tv)
    TextView  goodsNumTv;
    @BindView(R.id.go_pay_order_detail_lin)
    LinearLayout orderDetailLin;
    private int  REQUESTCODE=1000;
    private ArrayList<OrderLinesEntity> orderList;
    private ArrayList<OrderCouponsEntity> couponsList;
    private ArrayList<PayTypeEntity> payTypeList;
    private Handler hand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pay);
        activity = this;
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        fromOrder = bundle.getParcelable("fromOrder");
        ArrayList list = bundle.getParcelableArrayList("orderLine");
        orderList= (ArrayList<OrderLinesEntity>) list.get(0);
        ArrayList coupsonslist = bundle.getParcelableArrayList("coupsons");
        couponsList= (ArrayList<OrderCouponsEntity>) coupsonslist.get(0);
        ArrayList payTypelist = bundle.getParcelableArrayList("payType");
        payTypeList= (ArrayList<PayTypeEntity>) payTypelist.get(0);



        Logs.i(orderList.size()+"============="+couponsList.size());
        initView();
    }
    private void initView(){
        couponsTv.setText(payTypeList.size()+"张");
        totalPriceTv.setText("¥"+fromOrder.getTransactionAmount());
        freightAmountTv.setText("¥"+fromOrder.getFreightAmount());
        priceTv.setText("¥"+fromOrder.getTransactionAmount());
        goodsNumTv.setText("共"+orderList.size()+"类");
        onLineRely.setOnClickListener(this);
        getToPayRely.setOnClickListener(this);
        orderDetailLin.setOnClickListener(this);
        LinearLayout  goback = (LinearLayout) findViewById(R.id.gopay_title_goback_linlay);
        goback.setOnClickListener(this);
        RelativeLayout reladdress = (RelativeLayout) findViewById(R.id.gopay_check_address_rel);
        reladdress.setOnClickListener(this);
        RelativeLayout useYhj = (RelativeLayout) findViewById(R.id.gopay_use_yhj_rel);
        useYhj.setOnClickListener(this);
        TextView paytv = (TextView) findViewById(R.id.gopay_btn);
        paytv.setOnClickListener(this);
        initData(fromOrder.getDefaultAddress());

        recycler.setLayoutManager(new LinearLayoutManager(activity));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        CGOrderGoodsListRecyclerViewAdapter simpAdapter = new CGOrderGoodsListRecyclerViewAdapter(activity,orderList,1);
        recycler.setAdapter(simpAdapter);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        String orderNo = (String) msg.obj;
                        Intent pay = new Intent(activity, TopUpActivity.class);
                        pay.putExtra("payTag","1");
                        pay.putExtra("paySum","1000");
                        pay.putExtra("orderNo",orderNo);
                        startActivity(pay);
                        break;
                    case 1001:
                        break;
                    case AppClient.ERRORCODE:
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }
    private void initData(AddressInfoEntity address){
        if(address == null){
            return;
        }
        addressName.setText(address.getConsigneeName());
        addressPhone.setText(address.getConsigneeMobile());
        addressInfo.setText(address.getProvinceName()+address.getCityName()+address.getDistrictName()+address.getAddress());
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gopay_title_goback_linlay:
                finish();
                break;
            case R.id.gopay_check_address_rel:
                Intent address = new Intent(activity, AddressListActivity.class);
                startActivityForResult(address, REQUESTCODE);
                // 意图实现activity的跳转
                break;
            case R.id.gopay_use_yhj_rel:
                Intent intent = new Intent(activity, YHJActivity.class);
                intent.putExtra("yhjTag","1");
                startActivity(intent);
                break;
            case R.id.gopay_btn:
                SXUtils.showMyProgressDialog(activity,false);
                submitOrder();
//                Intent pay = new Intent(activity, TopUpActivity.class);
//                pay.putExtra("payTag","1");
//                pay.putExtra("paySum","1000");
//                startActivity(pay);
                break;
            case R.id.gopay_online_rel:
                onlineTv.setVisibility(View.VISIBLE);
                gottoTv.setVisibility(View.GONE);
                break;
            case R.id.gopay_getto_rel:
                onlineTv.setVisibility(View.GONE);
                gottoTv.setVisibility(View.VISIBLE);
                break;
            case R.id.go_pay_order_detail_lin:
                //点击共多少类跳转订单详情
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
        // operation succeeded. 默认值是-1
        if (requestCode == REQUESTCODE) {
            if(data == null)
                return;
            Bundle bundle = data.getExtras();
            AddressInfoEntity address = (AddressInfoEntity) bundle.get("addressInfo");
            initData(address);
        }
    }
    /**
     * 订单结算
     */
    public void submitOrder() {
        HttpParams httpp = new HttpParams();
        httpp.put("couponNos","");//优化劵 用逗号隔开
        httpp.put("consigneeId",fromOrder.getDefaultAddress().getConsigneeId());//收货地址ID
        httpp.put("settlementMode","1");//支付方式
        httpp.put("skuBarcodes",returnSkuCode());//优化劵 用逗号隔开
        HttpUtils.getInstance(activity).requestPost(false, AppClient.ORDER_SUBMIT, httpp, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("提交订单成功返回参数======",jsonObject.toString());
                String   orderNo ="";
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                    orderNo = jsonObject1.getString("orderNo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                JSONObject jsonObject1 = null;
//                FromOrderEntity orderFrom = (FromOrderEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),FromOrderEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = orderNo;
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

    /**
     * 获取支付skucode
     * @return
     */
    private String returnSkuCode(){
        String skuStr = "";
        for(int i=0;i<orderList.size();i++){
            skuStr += orderList.get(i).getSkuBarcode()+",";
        }
        if(TextUtils.isEmpty(skuStr) || skuStr.equals(","))
            return "";
        return skuStr.substring(0,skuStr.length()-1);
    }

}
