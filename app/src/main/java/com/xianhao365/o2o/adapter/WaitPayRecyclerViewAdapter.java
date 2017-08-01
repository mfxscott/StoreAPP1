package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.GoodsInfoEntity;
import com.xianhao365.o2o.fragment.my.store.TopUpActivity;
import com.xianhao365.o2o.fragment.my.store.order.OrderDetailActivity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 常用清单列表
 * @author mfx
 * @time  2017/7/11 12:24
 */
public  class WaitPayRecyclerViewAdapter
        extends RecyclerView.Adapter<WaitPayRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<GoodsInfoEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final ImageView mImageView1;
        public final TextView marketPrice;
        public final TextView  cancelOrder;
        public final TextView  takeOrder,cancelTv;
        public final RelativeLayout  rel1,rel2;
        public final LinearLayout  btnLin;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.order_wait_pay_item_iv);
            marketPrice = (TextView) view.findViewById(R.id.order_wait_pay_item_mp_tv);
            mImageView1 = (ImageView) view.findViewById(R.id.order_wait_pay_item_iv2);
            cancelOrder = (TextView) view.findViewById(R.id.order_wait_pay_cancel_btn);
            takeOrder = (TextView) view.findViewById(R.id.order_wait_pay_take_btn);
            rel1 = (RelativeLayout) view.findViewById(R.id.order_wait_pay_rel1);
            rel2 = (RelativeLayout) view.findViewById(R.id.order_wait_pay_rel2);
            cancelTv = (TextView) view.findViewById(R.id.order_done_tv);
            btnLin = (LinearLayout) view.findViewById(R.id.order_btn_lin);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + marketPrice.getText();
        }
    }
    public WaitPayRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items,int tag) {
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
        Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_gdy).into(holder.mImageView);
        Glide.with(holder.mImageView1.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_hlg).into(holder.mImageView1);
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
                        break;
                    case 3:
                        SXUtils.getInstance(context).ToastCenter("确认发货");
                        break;
                    case 4:
                        break;
                }


            }
        });
        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(context).ToastCenter("取消订单");
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