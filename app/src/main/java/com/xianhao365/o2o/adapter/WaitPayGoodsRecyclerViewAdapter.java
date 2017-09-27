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
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.orderlist.OrderGoodsInfoEntity;
import com.xianhao365.o2o.fragment.my.store.order.OrderDetailActivity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 订单列表中的商品信息
 */
public  class WaitPayGoodsRecyclerViewAdapter
        extends RecyclerView.Adapter<WaitPayGoodsRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<OrderGoodsInfoEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView marketPrice;
        public final TextView skuPrice;
        public final  TextView skuTotal;
        public final TextView   num;
        public final TextView unit;
        public final  TextView name;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mImageView = (ImageView) view.findViewById(R.id.order_wait_pay_item_iv);
            marketPrice = (TextView) view.findViewById(R.id.order_wait_pay_item_mp_tv);
            skuPrice = (TextView) view.findViewById(R.id.order_wait_pay_goods_skuprice_tv);
            unit = (TextView) view.findViewById(R.id.order_wait_pay_goods_unit_tv);
            skuTotal = (TextView) view.findViewById(R.id.order_wait_pay_goods_total_tv);
            num = (TextView) view.findViewById(R.id.order_wait_pay_num_tv);
            name = (TextView) view.findViewById(R.id.order_wait_pay_goods_name_tv);
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public WaitPayGoodsRecyclerViewAdapter(Context context, List<OrderGoodsInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_wait_pay_goods_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderGoodsInfoEntity orderInfo = mValues.get(position);

    holder.skuPrice.setText(orderInfo.getSkuPrice());
        holder.num.setText("x "+orderInfo.getSkuNumber());
        holder.name.setText(orderInfo.getSkuName());
        holder.skuTotal.setText((Float.parseFloat(orderInfo.getSkuNumber()))*Float.parseFloat(orderInfo.getSkuPrice())+"元");
        SXUtils.getInstance(context).GlideSetImg(orderInfo.getSkuImage(),holder.mImageView);
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