package com.scott.shopplat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.entity.AddressInfoEntity;

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView namePhone;
        public final TextView cityStreet;
        public final CheckBox  isDefaultCb;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            namePhone = (TextView) view.findViewById(R.id.address_item_name);
            cityStreet = (TextView) view.findViewById(R.id.address_item_city_street);
            isDefaultCb = (CheckBox) view.findViewById(R.id.address_item_cb);

        }
        @Override
        public String toString() {
            return super.toString();
        }
    }
    public AddressRecyclerViewAdapter(Context context, List<AddressInfoEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
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
        AddressInfoEntity  addrss = mValues.get(position);
        holder.namePhone.setText(addrss.getName()+"&#160;&#160;&#160;&#160;"+addrss.getPhone());
        holder.namePhone.setText(addrss.getCity()+addrss.getStreet());
        if(addrss.getIsDefault().equals("0")){
            holder.isDefaultCb.setVisibility(View.VISIBLE);
        }else{
            holder.isDefaultCb.setVisibility(View.GONE);
        }

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