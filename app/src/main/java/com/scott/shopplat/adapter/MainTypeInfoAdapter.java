package com.scott.shopplat.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.scott.shopplat.R;
import com.scott.shopplat.entity.GoodsInfoEntity;

import java.util.List;

/**
 * 首页商品分类列表。商品详细
 * @author mfx
 * @time  2017/7/10 20:47
 */
public class MainTypeInfoAdapter extends BaseAdapter {
    private List<GoodsInfoEntity> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    public MainTypeInfoAdapter(Activity context, List<GoodsInfoEntity> result) {
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
        GoodsInfoEntity info = result.get(position);
        LifeViewHolder vh;
        if (convertView == null) {
            vh = new LifeViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.main_type_info_item, null);
            convertView.setTag(vh);
        } else {
            vh = (LifeViewHolder) convertView.getTag();
        }
//        vh.cardName.setText(bankinfo.get("cardName")+"");
//        vh.cardNum.setText(bankinfo.get("cardNum")+"");
        return convertView;
    }
    class LifeViewHolder{
    }
}