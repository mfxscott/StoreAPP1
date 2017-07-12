package com.scott.shopplat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * @author mfx
 * @email mfx@ddbill.com
 * @time  2017/7/11 16:57
 */
public  class CarRecyclerViewAdapter
        extends RecyclerView.Adapter<CarRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<GoodsInfoEntity> mValues;
    //false 为默认隐藏编辑选择按钮
    public boolean showCheckb = false;
    private Context context;
    private Map<String,Boolean>  map = new HashMap<String ,Boolean>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView nameTv;
        public final CheckBox  checkbox;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.car_item_iv);
            nameTv = (TextView) view.findViewById(R.id.car_item_name);
            checkbox = (CheckBox) view.findViewById(R.id.car_item_checkbox);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + nameTv.getText();
        }
    }
    public CarRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTv.setText(mValues.get(position).getGoodsname());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SXUtils.getInstance(context).ToastCenter("===="+position);
            }
        });

//        holder.modeTView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                removeData(position);
//            }
//        });
        if(showCheckb){
            holder.checkbox.setVisibility(View.VISIBLE);
        }else{
            holder.checkbox.setVisibility(View.GONE);
        }
        holder.checkbox.setChecked(map.get(position+""));
        getKeyValue();
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                if(map.containsKey(""+position)){
//                    map.remove(""+position);
//                }else{
                map.put(position+"",isChecked);
//                }

//                if(isChecked){
//                    SXUtils.getInstance(holder.checkbox.getContext()).ToastCenter("true==="+position);
//
//
//
//                }else{
//                    SXUtils.getInstance(holder.checkbox.getContext()).ToastCenter("false==="+position);
//                }
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
//        Log.i("========",position+"");
        return super.getItemViewType(position);
    }
    //  添加数据
    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条
        notifyItemInserted(position);
    }
    public  void  getKeyValue(){
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            Boolean value = map.get(key);
            Logs.i(key+"========================"+value);
        }
    }
    //  删除数据
    public void removeData(int position) {
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            Boolean value = map.get(key);
            if(value){
                int postions = Integer.parseInt(key);
                mValues.remove(postions);
                notifyItemRemoved(postions);
            }
            System.out.println(key+" "+value);
        }
        notifyDataSetChanged();




//
//        Iterator keys = map.keySet().iterator();
//        while(keys.hasNext()){
//            String key = (String)keys.next();
//            int postions = Integer.parseInt(key);
//            mValues.remove(postions);
//            notifyItemRemoved(postions);
//        }
////      mValues.remove(position);
//        notifyDataSetChanged();
//        map.clear();
    }

    /**
     * 全选商品
     */
    public void selectAll() {
        for (int i = 0; i < mValues.size(); i++) {
//            if(!map.containsKey(""+i))
            map.put(""+i,true);
        }
        notifyDataSetChanged();
    }
    /**
     * 初始化
     */
    public void initDate() {
        for (int i = 0; i < mValues.size(); i++) {
            map.put(""+i,false);
        }
        notifyDataSetChanged();
    }
}