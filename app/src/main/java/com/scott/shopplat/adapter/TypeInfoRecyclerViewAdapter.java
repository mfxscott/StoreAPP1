package com.scott.shopplat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.utils.SXUtils;

import java.util.List;

/**
 * 首页商品分类列表。商品详细
 * @author mfx
 * @time  2017/7/10 20:47
 */
public  class TypeInfoRecyclerViewAdapter
        extends RecyclerView.Adapter<TypeInfoRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<GoodsInfoEntity> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView modeTView;
        public final TextView mTextView;
        public  final LinearLayout  selectLin;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.type_info_item_iv);
            mTextView = (TextView) view.findViewById(R.id.type_info_item_name);
            modeTView = (TextView) view.findViewById(R.id.type_info_select_mode);
            selectLin = (LinearLayout) view.findViewById(R.id.type_goods_item_gg_liny);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
    public TypeInfoRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
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
        holder.mTextView.setText(mValues.get(position).getGoodsname());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SXUtils.getInstance(context).ToastCenter("===="+position);
            }
        });

        holder.modeTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.selectLin.isShown()){
                    holder.selectLin.setVisibility(View.VISIBLE);
                }else{
                    holder.selectLin.setVisibility(View.GONE);
                }
//                removeData(position);
            }
        });

        Glide.with(holder.mImageView.getContext())
                .load("http://img4.imgtn.bdimg.com/it/u=3071322373,3354763627&fm=28&gp=0.jpg")
                .fitCenter()
                .into(holder.mImageView);
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