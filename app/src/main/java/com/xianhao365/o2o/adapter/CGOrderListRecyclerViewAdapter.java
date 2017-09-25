package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.cgListInfo.CGListInfoEntity;
import com.xianhao365.o2o.fragment.my.buyer.purchase.CGOrderDeliveActivity;
import com.xianhao365.o2o.fragment.my.buyer.purchase.CGOrderDetailActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购清单列表
 * @author mfx
 */
public  class CGOrderListRecyclerViewAdapter extends RecyclerView.Adapter<CGOrderListRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<CGListInfoEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView cgOrderNumTv;
        public TextView cgOrderTimeItemTv;
        public RecyclerView cgOrderItemRecycler;
        public TextView cgOrderPriceItemTv;
        public TextView cgOrderGetTimeItemTv;
        public TextView cgOrderAddressItemTv;
        public TextView cgOrderTakeItemTv;
        public TextView cgOrderDelBtn;
        public TextView cgOrderFeedbackBtn;
        public TextView cgOrderPayBtn;
        public LinearLayout orderBtnLin;
        public TextView orderDoneTv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cgOrderItemRecycler = (RecyclerView) view.findViewById(R.id.cg_order_item_recycler);
            cgOrderNumTv = (TextView) view.findViewById(R.id.cg_order_num_tv);
            cgOrderTimeItemTv =  (TextView) view.findViewById(R.id.cg_order_time_item_tv);
            cgOrderPriceItemTv      =  (TextView) view.findViewById(R.id.cg_order_price_item_tv);
            cgOrderGetTimeItemTv  =  (TextView) view.findViewById(R.id.cg_order_get_time_item_tv);
            cgOrderAddressItemTv  =  (TextView) view.findViewById(R.id.cg_order_address_item_tv);
            cgOrderTakeItemTv =  (TextView) view.findViewById(R.id.cg_order_take_item_tv);
            cgOrderFeedbackBtn =  (TextView) view.findViewById(R.id.cg_order_feedback_btn);

        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public CGOrderListRecyclerViewAdapter(Context context, List<CGListInfoEntity> items, int tag) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cg_order_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CGListInfoEntity cgInfo = mValues.get(position);
        Logs.i(cgInfo.getPurchaseLineVos().size()+"=============+++++++");
//        收货状态(10:新建 20:供应商确认30:已发货 40:完成)
        final String receiveState = cgInfo.getReceiveState();
        holder.cgOrderItemRecycler.setLayoutManager(new LinearLayoutManager(holder.cgOrderItemRecycler.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.cgOrderItemRecycler.setLayoutManager(linearLayoutManager);
        holder.cgOrderItemRecycler.setItemAnimator(new DefaultItemAnimator());
        final CGOrderGoodsListRecyclerViewAdapter simpAdapter = new CGOrderGoodsListRecyclerViewAdapter(context,cgInfo.getPurchaseLineVos());
        holder.cgOrderItemRecycler.setAdapter(simpAdapter);

        holder.cgOrderNumTv.setText(cgInfo.getPurchaseCode()+"");
        holder.cgOrderTimeItemTv.setText(cgInfo.getCreated()+"");
        holder.cgOrderPriceItemTv.setText(cgInfo.getPurchaseAmount()+"元");
        holder.cgOrderGetTimeItemTv.setText(cgInfo.getReceiveTime());
        holder.cgOrderAddressItemTv.setText(cgInfo.getReceiverAddr());
        holder.cgOrderTakeItemTv.setText(cgInfo.getReceiver());
        if (receiveState.equals("10")) {
            holder.cgOrderFeedbackBtn.setText("确认");
        }
        if (receiveState.equals("20")) {
            holder.cgOrderFeedbackBtn.setText("发货");
        } else if (receiveState.equals("30")) {
            holder.cgOrderFeedbackBtn.setText("待收货");
            holder.cgOrderFeedbackBtn.setTextColor(context.getResources().getColor(R.color.orange));
            holder.cgOrderFeedbackBtn.setBackgroundResource(R.color.transparent);
        } else if (receiveState.equals("40")) {
            holder.cgOrderFeedbackBtn.setText("已完成");
            holder.cgOrderFeedbackBtn.setTextColor(context.getResources().getColor(R.color.orange));
            holder.cgOrderFeedbackBtn.setBackgroundResource(R.color.transparent);
        }
        holder.cgOrderFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (receiveState.equals("10")) {
                    SXUtils.getInstance(context).ToastCenter("确认订单");
                }
                if (receiveState.equals("20")) {
                    Intent intent = new Intent(context, CGOrderDeliveActivity.class);
                    intent.putExtra("code",cgInfo.getPurchaseCode());
                    intent.putExtra("num",cgInfo.getPurchaseLineVos().get(0).getActualNumber());
                    intent.putExtra("skucode",cgInfo.getPurchaseLineVos().get(0).getSkuCode());
                    context.startActivity(intent);
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CGOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                ArrayList list = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
                list.add(cgInfo.getPurchaseLineVos());
                bundle.putParcelableArrayList("PurchaseList",list);
                bundle.putParcelable("orderList", cgInfo);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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