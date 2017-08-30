package com.xianhao365.o2o.adapter;

import android.content.Context;
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
import com.xianhao365.o2o.entity.GoodsInfoEntity;
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
    private List<GoodsInfoEntity> mValues;
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
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public CGOrderListRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items,int tag) {
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
        holder.cgOrderItemRecycler.setLayoutManager(new LinearLayoutManager(holder.cgOrderItemRecycler.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.cgOrderItemRecycler.setLayoutManager(linearLayoutManager);
        holder.cgOrderItemRecycler.setItemAnimator(new DefaultItemAnimator());
        final CGOrderGoodsListRecyclerViewAdapter simpAdapter = new CGOrderGoodsListRecyclerViewAdapter(context,getBankData(),1);
        holder.cgOrderItemRecycler.setAdapter(simpAdapter);
        holder.cgOrderNumTv.setText("9876543210");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(context).ToastCenter(position+"");
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
    /**
     * @return
     */
    private ArrayList<GoodsInfoEntity> getBankData(){
        ArrayList<GoodsInfoEntity> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            GoodsInfoEntity  info = new GoodsInfoEntity();
            info.setGoodsname("新鲜上市的西红柿");
            info.setGoodsPrice("￥10.00");
            list.add(info);
        }
        return list;
    }
}