package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.AddressInfoEntity;
import com.xianhao365.o2o.fragment.car.EditAddAddressActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 常用清单列表
 * @author mfx
 * @time  2017/7/11 12:24
 */
public  class AddressRecyclerViewAdapter
        extends RecyclerView.Adapter<AddressRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<AddressInfoEntity> mValues;
    private Activity activity;
    private int dfposiont=-1;
    private boolean isOnclick=false;//判断只要点击过设置默认地址就不已默认字段标示为判断

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView namePhone;
        public final TextView cityStreet;
        public final CheckBox  isDefaultCb;
        public final  TextView editTv;
        public final  TextView  delTv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            namePhone = (TextView) view.findViewById(R.id.address_item_name);
            cityStreet = (TextView) view.findViewById(R.id.address_item_city_street);
            isDefaultCb = (CheckBox) view.findViewById(R.id.address_item_cb);
            editTv = (TextView) view.findViewById(R.id.address_item_edit);
            delTv = (TextView) view.findViewById(R.id.address_item_del);

        }
        @Override
        public String toString() {
            return super.toString();
        }
    }
    public AddressRecyclerViewAdapter(Activity context, List<AddressInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.activity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AddressInfoEntity  addrss = mValues.get(position);
        holder.namePhone.setText(addrss.getName()+"    "+addrss.getPhone());
        holder.cityStreet.setText(addrss.getCity()+addrss.getStreet());


        holder.isDefaultCb.setOnCheckedChangeListener(null);
        if(isOnclick){
            if (dfposiont == position) {
                holder.isDefaultCb.setChecked(true);
            } else {
                holder.isDefaultCb.setChecked(false);
            }
        }else {
            if (addrss.getIsDefault().equals("0")) {
                holder.isDefaultCb.setChecked(true);
            } else {
                holder.isDefaultCb.setChecked(false);

            }
        }
        holder.isDefaultCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dfposiont = position;
                }else{
                    dfposiont = -1;
                }
                isOnclick = true;
                notifyDataSetChanged();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        holder.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(position);
            }
        });
        holder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), EditAddAddressActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("tag","1");
                mBundle.putSerializable("address", (Serializable) mValues.get(position));
                intent.putExtras(mBundle);
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
    //  添加数据
    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条
        notifyItemInserted(position);
    }
    //  删除数据
    public void removeData(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }
}