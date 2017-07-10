package com.scott.shopplat.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.entity.YHJEneity;

import java.util.ArrayList;

/**
 * ***************************
 * 优惠券未使用
 * @author mfx
 * ***************************
 */
public class YHJNoUseListAdapter extends BaseAdapter {
    private ArrayList<YHJEneity> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    public YHJNoUseListAdapter(Activity context, ArrayList<YHJEneity> result) {
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
        YHJEneity info = result.get(position);
        LifeViewHolder vh;
        if (convertView == null) {
            vh = new LifeViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.yhj_nouse_item, null);
            vh.rel = (RelativeLayout) convertView.findViewById(R.id.yhj_item_bg_rel);
            vh.price = (TextView) convertView.findViewById(R.id.yhj_item_price_tv);
            vh.des = (TextView) convertView.findViewById(R.id.yhj_item_des_tv);
            vh.time = (TextView) convertView.findViewById(R.id.yhj_item_time_tv);
            vh.hs = (TextView) convertView.findViewById(R.id.yhj_item_hs_tv);
            convertView.setTag(vh);
        } else {
            vh = (LifeViewHolder) convertView.getTag();
        }
        vh.price.setText(info.getPrice()+"");
        vh.des.setText(info.getDes());
        vh.time.setText(info.getTime());

        if(info.getState().equals("1")){
            vh.rel.setBackgroundResource(R.mipmap.yhj_nouse_img);
            vh.hs.setText("已经使用");
        }else if(info.getState().equals("3")){
            vh.rel.setBackgroundResource(R.mipmap.yhj_nouse_img);
            vh.hs.setText("已经过期");
        }else{
            vh.rel.setBackgroundResource(R.mipmap.yhj_use_img);
            vh.hs.setText("立刻使用");
        }

//        vh.cardName.setText(bankinfo.get("cardName")+"");
//        vh.cardNum.setText(bankinfo.get("cardNum")+"");
        return convertView;
    }
    class LifeViewHolder{
        TextView price;
        TextView des;
        TextView time;
        TextView  hs;
        RelativeLayout rel;
    }
}