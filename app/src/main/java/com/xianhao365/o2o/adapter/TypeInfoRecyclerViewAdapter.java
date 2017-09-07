package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.GoodsDetailActivity;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 首页商品分类列表。商品详细
 * @author mfx
 * @time  2017/7/10 20:47
 */
public  class TypeInfoRecyclerViewAdapter
        extends RecyclerView.Adapter<TypeInfoRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<GoodsInfoEntity> mValues;
    private FoodActionCallback callback;

    @Override
    public void onClick(View v) {
        if(callback==null) return;
        callback.addAction(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView modeTView;
        public final TextView mTextView;
        public  final LinearLayout  selectLin;
        public final ImageView typeadd1;
        public final ImageView addImage,addImage2;
        public final TextView  shopPrice;
        public final TextView  goodsUnit;
        public final TextView  goodsModel;
        public final TextView  marketPrice;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.type_info_item_iv);
            mTextView = (TextView) view.findViewById(R.id.type_info_item_name);
            modeTView = (TextView) view.findViewById(R.id.type_info_select_mode);
            selectLin = (LinearLayout) view.findViewById(R.id.type_goods_item_gg_liny);
            typeadd1 = (ImageView) view.findViewById(R.id.type_info_add);
            addImage = (ImageView) view.findViewById(R.id.type_info_check1);
            addImage2 = (ImageView) view.findViewById(R.id.type_info_check2);
            shopPrice = (TextView) view.findViewById(R.id.type_info_shop_price_tv);
            goodsUnit = (TextView) view.findViewById(R.id.type_info_item_goodsUnit_tv);
            goodsModel = (TextView) view.findViewById(R.id.type_info_item_goodsmodel_tv);
            marketPrice = (TextView) view.findViewById(R.id.type_info_item_market_price_tv);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
    public TypeInfoRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items, FoodActionCallback callback) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_type_info_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GoodsInfoEntity goodsdetail = mValues.get(position);
        holder.modeTView.setVisibility(View.GONE);
//        if(goodsdetail.getSkuList() != null && goodsdetail.getSkuList().size()>0) {
//            Logs.i("多规格商品数量========="+goodsdetail.getSkuList().size());
//            holder.goodsModel.setText(goodsdetail.getSkuList().get(0).getGoodsModel());
            holder.marketPrice.setText("¥"+goodsdetail.getMarketPrice());
            holder.shopPrice.setText("¥"+goodsdetail.getShopPrice());
//        }
//        else if(goodsdetail.getSkuList() != null && goodsdetail.getSkuList().size()>1){
//            holder.modeTView.setVisibility(View.VISIBLE);
//        }
        holder.mTextView.setText(goodsdetail.getGoodsName()+"");
        holder.goodsUnit.setText("/"+goodsdetail.getGoodsUnit()+"");

        holder.modeTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.selectLin.isShown()){
                    holder.modeTView.setText("收起");
                    holder.selectLin.setVisibility(View.VISIBLE);
                }else{
                    holder.modeTView.setText("选规格");
                    holder.selectLin.setVisibility(View.GONE);
                }
//                removeData(position);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
                intent.putExtra("cno",mValues.get(position).getGoodsCode());
                holder.mView.getContext().startActivity(intent);
            }
        });
        holder.typeadd1.setOnClickListener(this);
        holder.addImage.setOnClickListener(this);
        holder.addImage2.setOnClickListener(this);
//        holder.typeadd1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainFragmentActivity.getInstance().setBadge(true,1);
//            }
//        });
//        holder.addImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainFragmentActivity.getInstance().setBadge(true,1);
//            }
//        });
//        holder.addImage2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainFragmentActivity.getInstance().setBadge(true,1);
//            }
//        });
//        if(position%2 ==0){
//            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_hlg).into(holder.mImageView);
//        }else{
//            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_jd).into(holder.mImageView);
//        }
        SXUtils.getInstance(holder.mImageView.getContext()).GlideSetImg(goodsdetail.getThumbImg(),holder.mImageView);
//        Glide.with(holder.mImageView.getContext())
//                .load(goodsdetail.getThumbImg())
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
    //  添加数据
    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条
        notifyItemInserted(position);
    }
    //  删除数据
    public void removeData(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

}