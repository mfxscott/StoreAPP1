package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xianhao365.o2o.R;

import java.util.List;
import java.util.Map;

/**
 * 商户注册成功选择店铺地址
 */
public class StoreMapListAdapter extends BaseAdapter {
    private List<Map<String ,String >> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    public StoreMapListAdapter(Activity context, List<Map<String ,String >>  result ) {
        mLayoutInflater = LayoutInflater.from(context);
        this.result = result;
        this.context = context;
    }
    public int getCount() {
            return result.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String ,String > map = result.get(position);
        LifeViewHolder vh;
        if (convertView == null) {
            vh = new LifeViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.store_map_item, null);
            vh.title = (TextView) convertView.findViewById(R.id.store_map_item_title);
            vh.address = (TextView) convertView.findViewById(R.id.store_map_item_address);
            convertView.setTag(vh);
        } else {
            vh = (LifeViewHolder) convertView.getTag();
        }
        vh.title.setText(map.get("title"));
        vh.address.setText(map.get("address"));
        return convertView;
    }
    class LifeViewHolder{
        TextView title;
        TextView address;
    }
}