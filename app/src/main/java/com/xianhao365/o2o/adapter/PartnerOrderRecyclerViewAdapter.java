package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.orderlist.OrderInfoEntity;
import com.xianhao365.o2o.fragment.my.store.order.OrderDetailActivity;

import java.util.List;

/**
 * 合伙人订单列表
 * @author mfx
 * @time  2017/7/11 12:24
 */
public  class PartnerOrderRecyclerViewAdapter
        extends RecyclerView.Adapter<PartnerOrderRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<OrderInfoEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView  shopNameTv;
        public final TextView  orderTotal;
        public final TextView  orderTime;
        public final TextView  orderNo;
        public final TextView  sendOrderTime;
         public final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            shopNameTv = (TextView) view.findViewById(R.id.partner_order_wait_pay_shopname_tv);
            orderTotal = (TextView) view.findViewById(R.id.partner_order_wait_pay_ordertotal_tv);
            orderTime = (TextView) view.findViewById(R.id.partner_order_time_tv);
            orderNo = (TextView) view.findViewById(R.id.partner_order_no_tv);
            sendOrderTime = (TextView) view.findViewById(R.id.partner_order_send_time_tv);
            recyclerView = (RecyclerView) view.findViewById(R.id.order_item_recycler);
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public PartnerOrderRecyclerViewAdapter(Context context, List<OrderInfoEntity> items, int tag) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partner_order_wait_pay_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final OrderInfoEntity orderInfo = mValues.get(position);

        holder.shopNameTv.setText(orderInfo.getShopUserName()+"");
        holder.orderTime.setText("订单时间："+orderInfo.getOrderTime()+"");
        holder.orderTotal.setText("合计：¥ "+orderInfo.getGoodsTotalAmount());
        holder.orderNo.setText("订单号："+orderInfo.getOrderNo()+"");
        holder.sendOrderTime.setText("发货时间：");
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        final WaitPayGoodsRecyclerViewAdapter simpAdapter = new WaitPayGoodsRecyclerViewAdapter(context,orderInfo.getOrderLines());
        holder.recyclerView.setAdapter(simpAdapter);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( holder.mView.getContext(), OrderDetailActivity.class);
                intent.putExtra("orderTag",tag+"");
                intent.putExtra("orderId","123456");
                holder.mView.getContext().startActivity(intent);
            }
        });
        int states= Integer.parseInt(orderInfo.getStates());
            switch (states){
                case 0:
                    holder.sendOrderTime.setVisibility(View.GONE);
                    break;
                case 10:
                    holder.sendOrderTime.setVisibility(View.GONE);
                    break;
                case 20:
                    holder.sendOrderTime.setVisibility(View.VISIBLE);
                    break;
                case 4:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
                case 8:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
                case 16:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
                case 32:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
                case 30:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
                case 50:
//                    holder.btnLin.setVisibility(View.GONE);
                    break;
            }
//        holder.takeOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (tag) {
//                    case 1:
//                        Intent pay = new Intent(context, TopUpActivity.class);
//                        pay.putExtra("payTag","1");
//                        pay.putExtra("paySum","1000");
//                        context.startActivity(pay);
//                        break;
//                    case 2:
//                        SXUtils.getInstance(context).ToastCenter("提醒发货");
//                        new WaitSendFragment().getRemindHttp(orderInfo.getOrderNo(),orderInfo.getTradeNo());
//
//                        break;
//                    case 3:
////                        SXUtils.getInstance(context).ToastCenter("确认发货");
//                        new WaitTakeFragment().getOrderConfirmHttp(orderInfo.getOrderNo());
//                        break;
//                    case 4:
//                        break;
//                }
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    @Override
    public int getItemViewType(int position) {
        Log.i("========",position+"");
        return super.getItemViewType(position);
    }

}