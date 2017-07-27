package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianhao365.o2o.R;

import java.util.List;
import java.util.Map;

/**
 * 首页九宫格
 * @author mfx
 * @time  2017/7/10 10:13
 */
public class HomeGridViewAdapter extends BaseAdapter {
    private List<Map<String,String>> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    public HomeGridViewAdapter(Activity context, List<Map<String,String>> result) {
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
        Map<String,String> gridvlist = result.get(position);
        LifeViewHolder vh;
        if (convertView == null) {
            vh = new LifeViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.home_gridview, null);
            vh.imgv = (ImageView) convertView.findViewById(R.id.home_grid_item_iv);
            vh.name = (TextView) convertView.findViewById(R.id.home_name_item_tv);
            convertView.setTag(vh);
        } else {
            vh = (LifeViewHolder) convertView.getTag();
        }
        switch(position){
            case 0:
                vh.imgv.setImageResource(R.mipmap.start__pay);
//                Glide.with(context).load("android.resource://com.scott.shopplat/mipmap/"+R.mipmap.start__pay).into(vh.imgv);
                break;
            case 1:
                vh.imgv.setImageResource(R.mipmap.use_bill);
//                Glide.with(context).load("android.resource://com.scott.shopplat/mipmap/"+R.mipmap.use_bill).into(vh.imgv);
                break;
            case 2:
                vh.imgv.setImageResource(R.mipmap.my_hb);
//                Glide.with(context).load("android.resource://com.scott.shopplat/mipmap/"+R.mipmap.my_hb).into(vh.imgv);
                break;
            case 3:
                vh.imgv.setImageResource(R.mipmap.my_order);
//                Glide.with(context).load("android.resource://com.scott.shopplat/mipmap/"+R.mipmap.my_order).into(vh.imgv);
                break;
        }
//        Glide.with(context).load(gridvlist.get("imageUrl")).centerCrop().into(vh.imgv);
        vh.name.setText(gridvlist.get("name"));
        return convertView;
    }
    class LifeViewHolder{
        ImageView imgv;
        TextView name;
    }
}