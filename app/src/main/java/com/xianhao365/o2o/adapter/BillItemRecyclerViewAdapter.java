package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.bill.BillChirdrenEntity;
import com.xianhao365.o2o.entity.bill.CategoryListEntity;
import com.xianhao365.o2o.utils.view.MyFoodActionCallback;

import java.util.List;

/**
 * 常用清单，model解析
 */
public  class BillItemRecyclerViewAdapter
        extends RecyclerView.Adapter<BillItemRecyclerViewAdapter.ViewHolder>{

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private CategoryListEntity category;
    private List<BillChirdrenEntity> billchirdrenList;
    private FoodActionCallback callback;
    private Context context;
    private String goodsUnit;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView addImage;
        public final TextView  shopPrice;
        public final TextView  goodsUnit;
        public final TextView  goodsModel;
        public final TextView  marketPrice;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            addImage = (ImageView) view.findViewById(R.id.main_bill_item_addcar_tv);
            shopPrice = (TextView) view.findViewById(R.id.type_info_shop_price_tv);
            goodsUnit = (TextView) view.findViewById(R.id.main_bill_item_unit_tv);
            goodsModel = (TextView) view.findViewById(R.id.main_bill_item_model_tv);
            marketPrice = (TextView) view.findViewById(R.id.main_bill_item_market_tv);
        }
        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
    public BillItemRecyclerViewAdapter(Context context, CategoryListEntity category) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        this.category = category;
        this.context = context;
        goodsUnit = category.getGoodsUnit();
        billchirdrenList = category.getChirdren();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_modle_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      final  BillChirdrenEntity billchirdren = billchirdrenList.get(position);
        holder.goodsUnit.setText("/"+goodsUnit);
        holder.marketPrice.setText(billchirdren.getMarketPrice());
//        holder.shopPrice.setText(billchirdren.getShopPrice());
        holder.goodsModel.setText(billchirdren.getGoodsModel());
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
//                intent.putExtra("cno",category.getId());
//                holder.mView.getContext().startActivity(intent);
//            }
//        });
        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback = new MyFoodActionCallback((Activity) context,billchirdren.getSkuBarcode());
                if(callback==null) return;
                callback.addAction(v);
            }
        });
    }
    @Override
    public int getItemCount() {
        return billchirdrenList.size();
    }
    @Override
    public int getItemViewType(int position) {
        Log.i("========",position+"");
        return super.getItemViewType(position);
    }

}