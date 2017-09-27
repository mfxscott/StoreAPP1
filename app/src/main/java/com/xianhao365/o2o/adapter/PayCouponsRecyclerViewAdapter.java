package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.car.OrderCouponsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 提交订单选择优惠券列表
 */
public  class PayCouponsRecyclerViewAdapter
        extends RecyclerView.Adapter<PayCouponsRecyclerViewAdapter.ViewHolder> {
    public Map<String,Boolean> couponsMap = new HashMap<String ,Boolean>();
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private ArrayList<OrderCouponsEntity>  mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView price;
        public final TextView des;
        public final TextView time;
        public final TextView  hs;
        public final  RelativeLayout rel;
        public final CheckBox  checkbox;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            rel = (RelativeLayout) mView.findViewById(R.id.pay_coupons_item_bg_rel);
            price = (TextView) mView.findViewById(R.id.pay_coupons_item_price_tv);
            des = (TextView) mView.findViewById(R.id.pay_coupons_item_des_tv);
            time = (TextView) mView.findViewById(R.id.pay_coupons_item_time_tv);
            hs = (TextView) mView.findViewById(R.id.pay_coupons_item_hs_tv);
            checkbox = (CheckBox) mView.findViewById(R.id.pay_coupons_item_checkbox);
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public PayCouponsRecyclerViewAdapter(Context context, ArrayList<OrderCouponsEntity> items, int tag) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
        initStoreDate();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gopay_check_coupons_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderCouponsEntity  couponInfo = mValues.get(position);
        holder.price.setText(couponInfo.getCouponMoney()+"");
        holder.des.setText(couponInfo.getCouponTerm());
        holder.time.setText(couponInfo.getCouponTime());
        if(tag==2){
            holder.rel.setBackgroundResource(R.mipmap.yhj_nouse_img);
            holder.hs.setText("已经使用");
        }else if(tag==3){
            holder.rel.setBackgroundResource(R.mipmap.yhj_nouse_img);
            holder.hs.setText("已经过期");
        }else{
            holder.rel.setBackgroundResource(R.mipmap.yhj_use_img);
            holder.hs.setText("立刻使用");
        }

        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(couponsMap.get(position+""));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                couponsMap.put(position+"",isChecked);
                notifyDataSetChanged();
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkbox.isChecked())
                    couponsMap.put(position+"",false);
                else
                    couponsMap.put(position+"",true);
                notifyDataSetChanged();
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
     * 初始化
     */
    public void initStoreDate() {
        for (int i = 0; i < mValues.size(); i++) {
            couponsMap.put(""+i,false);
        }
        notifyDataSetChanged();

    }
    /**
     * 获取选中商品skucode 用于结算订单
     * @return
     */
    public String getCarTotalStrSkucode(){
        String skuCodestr = "";
        Iterator<String> iter = couponsMap.keySet().iterator();
        for(int i=0;i<mValues.size();i++){
            while (iter.hasNext()) {
                String key = iter.next();
                Boolean value = couponsMap.get(key);
                if(value){
                    int postions = Integer.parseInt(key);
                    skuCodestr += mValues.get(postions).getCouponNo()+",";
                }
            }
        }
        if(TextUtils.isEmpty(skuCodestr) || skuCodestr.equals(","))
            return "";
        return skuCodestr.substring(0,skuCodestr.length()-1)+"";
    }
}