package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.orderlist.OrderInfoEntity;
import com.xianhao365.o2o.fragment.my.store.TopUpActivity;
import com.xianhao365.o2o.fragment.my.store.order.OrderDetailActivity;
import com.xianhao365.o2o.fragment.my.store.order.WaitPayFragment;
import com.xianhao365.o2o.fragment.my.store.order.WaitSendFragment;
import com.xianhao365.o2o.fragment.my.store.order.WaitTakeFragment;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 *  摊主订单列表
 * @author mfx
 * @time  2017/7/11 12:24
 */
public  class WaitPayRecyclerViewAdapter
        extends RecyclerView.Adapter<WaitPayRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<OrderInfoEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final ImageView mImageView1;
        public final TextView  shopNameTv;
        public final TextView orderTime;
        public final TextView  orderTotal;
        public final TextView marketPrice;
        public final TextView  cancelOrder;
        public final TextView  takeOrder,cancelTv;
        public final RelativeLayout  rel1,rel2;
        public final LinearLayout  btnLin;
         public final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mImageView = (ImageView) view.findViewById(R.id.order_wait_pay_item_iv);
            marketPrice = (TextView) view.findViewById(R.id.order_wait_pay_item_mp_tv);
            mImageView1 = (ImageView) view.findViewById(R.id.order_wait_pay_item_iv2);
            shopNameTv = (TextView) view.findViewById(R.id.order_wait_pay_shopname_tv);
            orderTime = (TextView) view.findViewById(R.id.order_wait_pay_ordertime_tv);
            orderTotal = (TextView) view.findViewById(R.id.order_wait_pay_ordertotal_tv);
            cancelOrder = (TextView) view.findViewById(R.id.order_wait_pay_cancel_btn);
            takeOrder = (TextView) view.findViewById(R.id.order_wait_pay_take_btn);
            rel1 = (RelativeLayout) view.findViewById(R.id.order_wait_pay_rel1);
            rel2 = (RelativeLayout) view.findViewById(R.id.order_wait_pay_rel2);
            cancelTv = (TextView) view.findViewById(R.id.order_done_tv);
            btnLin = (LinearLayout) view.findViewById(R.id.order_btn_lin);
            recyclerView = (RecyclerView) view.findViewById(R.id.order_item_recycler);
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public WaitPayRecyclerViewAdapter(Context context, List<OrderInfoEntity> items, int tag) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_wait_pay_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final OrderInfoEntity orderInfo = mValues.get(position);

        holder.shopNameTv.setText(orderInfo.getOrderNo()+"");
        holder.orderTime.setText(orderInfo.getOrderTime()+"");
        holder.orderTotal.setText("¥"+orderInfo.getGoodsTotalAmount());

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        final WaitPayGoodsRecyclerViewAdapter simpAdapter = new WaitPayGoodsRecyclerViewAdapter(context,orderInfo.getOrderLines());
        holder.recyclerView.setAdapter(simpAdapter);


        holder.marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( holder.mView.getContext(), OrderDetailActivity.class);
                intent.putExtra("orderTag",tag+"");
                intent.putExtra("orderId","123456");
                holder.mView.getContext().startActivity(intent);
            }
        });
            switch (tag){
                case 1:
                    holder.btnLin.setVisibility(View.VISIBLE);
                    holder.takeOrder.setText("立刻付款");
                    holder.takeOrder.setBackgroundResource(R.drawable.comfirm_take_selector);
                    break;
                case 2:
                    holder.btnLin.setVisibility(View.VISIBLE);
                    holder.takeOrder.setText("提醒发货");
                    holder.takeOrder.setTextColor(context.getResources().getColor(R.color.orange));
                    holder.takeOrder.setBackgroundResource(R.drawable.cancel_order_selector);
                    break;
                case 3:
                    holder.btnLin.setVisibility(View.VISIBLE);
                    holder.takeOrder.setText("确定收货");
                    holder.takeOrder.setBackgroundResource(R.drawable.comfirm_take_selector);
                    break;
                case 4:
                    holder.btnLin.setVisibility(View.GONE);
                    holder.cancelTv.setVisibility(View.VISIBLE);
                    break;
            }
        holder.takeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tag) {
                    case 1:
                        Intent pay = new Intent(context, TopUpActivity.class);
                        pay.putExtra("payTag","1");
                        pay.putExtra("paySum","1000");
                        context.startActivity(pay);
                        break;
                    case 2:
                        SXUtils.getInstance(context).ToastCenter("提醒发货");
                        new WaitSendFragment().getRemindHttp(orderInfo.getOrderNo(),orderInfo.getTradeNo());

                        break;
                    case 3:
//                        SXUtils.getInstance(context).ToastCenter("确认发货");
                        new WaitTakeFragment().getOrderConfirmHttp(orderInfo.getOrderNo());
                        break;
                    case 4:
                        break;
                }
            }
        });
        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      new WaitPayFragment().getCancelOrderHttp(orderInfo.getOrderNo(),new WaitPayFragment().hand);
            }
        });
//        holder.rel1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, GoodsDetailActivity.class);
//                holder.mView.getContext().startActivity(intent);
//            }
//        });
//        holder.rel2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( context, GoodsDetailActivity.class);
//                holder.mView.getContext().startActivity(intent);
//            }
//        });
//        Glide.with(holder.mImageView.getContext())
//                .load("http://img4.imgtn.bdimg.com/it/u=3071322373,3354763627&fm=28&gp=0.jpg")
//                .fitCenter()
//                .into(holder.mImageView);
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