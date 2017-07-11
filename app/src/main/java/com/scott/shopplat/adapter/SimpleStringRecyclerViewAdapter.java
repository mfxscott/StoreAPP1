package com.scott.shopplat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;

import java.util.List;

/**
 * 常用清单列表
 * @author mfx
 * @time  2017/7/11 12:24
 */
public  class SimpleStringRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<GoodsInfoEntity> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final ImageView delImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.main_bill_item_iv);
            mTextView = (TextView) view.findViewById(R.id.main_bill_item_name);
            delImageView = (ImageView) view.findViewById(R.id.main_bill_item_del_iv);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
    public SimpleStringRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_bill_item, parent, false);
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

        holder.delImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(position);
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
        Logs.i("删除id==="+position);
        mValues.remove(position);
//        notifyItemRemoved(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);
        if (position != mValues.size()) {
            notifyItemRangeChanged(position, mValues.size() - position);
        }
    }
}