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

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.GoodsDetailActivity;
import com.xianhao365.o2o.entity.goods.GoodsDetailEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;

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
    private List<GoodsDetailEntity> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView modeTView;
        public final TextView mTextView;
        public  final LinearLayout  selectLin;
        public final ImageView typeadd1;
        public final ImageView addImage,addImage2;

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
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }
    public TypeInfoRecyclerViewAdapter(Context context, List<GoodsDetailEntity> items) {
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
        holder.mTextView.setText(mValues.get(position).getGoodsName());

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
                intent.putExtra("cno",mValues.get(position).getCno());
                holder.mView.getContext().startActivity(intent);
            }
        });
        holder.typeadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragmentActivity.getInstance().setBadge(true,1);
            }
        });
        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragmentActivity.getInstance().setBadge(true,1);
            }
        });
        holder.addImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragmentActivity.getInstance().setBadge(true,1);
            }
        });
        if(position%2 ==0){
            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_hlg).into(holder.mImageView);
        }else{
            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_jd).into(holder.mImageView);
        }

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