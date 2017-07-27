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
 * ***************************
 * 钱包收入明细适配器
 * @author mfx
 * ***************************
 */
public class SRDetailListAdapter extends BaseAdapter {
    private List<Map<String,String>> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    public SRDetailListAdapter(Activity context, List<Map<String,String>> result) {
        mLayoutInflater = LayoutInflater.from(context);
        this.result = result;
        this.context = context;
    }
    public int getCount() {
        if(result.size() >3){
            return 3;
        }else{
            return result.size();
        }
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String,String> bankinfo = result.get(position);
        LifeViewHolder vh;
        if (convertView == null) {
            vh = new LifeViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.wallet_srdetail_item, null);
            convertView.setTag(vh);
        } else {
            vh = (LifeViewHolder) convertView.getTag();
        }
//        vh.cardName.setText(bankinfo.get("cardName")+"");
//        vh.cardNum.setText(bankinfo.get("cardNum")+"");
        return convertView;
    }
    class LifeViewHolder{
        TextView cardNum;
        TextView cardName;
    }
}