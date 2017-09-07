package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;

import java.util.List;

/**
 * 首页九宫格
 * @author mfx
 * @time  2017/7/10 10:13
 */
public class HomeBillGridViewAdapter extends BaseAdapter implements View.OnClickListener{
    private  List<GoodsInfoEntity> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    private FoodActionCallback callback;
    public HomeBillGridViewAdapter(Activity context,  List<GoodsInfoEntity> result,FoodActionCallback callback) {
        mLayoutInflater = LayoutInflater.from(context);
        this.result = result;
        this.context = context;
        this.callback = callback;
    }
    @Override
    public void onClick(View v) {
        if(callback==null) return;
        callback.addAction(v);
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
    public View getView(final int position, View view, ViewGroup parent) {
        GoodsInfoEntity goodsinfo = result.get(position);
        LifeViewHolder vh;
        if (view == null) {
            vh = new LifeViewHolder();
            view = mLayoutInflater.inflate(R.layout.main_bill_item, null);
            vh.mImageView = (ImageView) view.findViewById(R.id.main_bill_item_iv);
            vh.mTextView = (TextView) view.findViewById(R.id.main_bill_item_name);
            vh.delImageView = (ImageView) view.findViewById(R.id.main_bill_item_del_iv);
            vh.addcar1 = (TextView) view.findViewById(R.id.main_bill_addcar_tv);
            vh.addcar2 = (TextView) view.findViewById(R.id.main_bill_addcar_tv2);
            view.setTag(vh);
        } else {
            vh = (LifeViewHolder) view.getTag();
        }


        vh.mTextView.setText(goodsinfo.getGoodsName());
//
//        vh.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent( vh.mView.getContext(), GoodsDetailActivity.class);
//                vh.mView.getContext().startActivity(intent);
//            }
//        });
        vh.delImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.remove(position);
                notifyDataSetChanged();
//                removeData(position);
            }
        });
        if(position%2 ==0){
            Glide.with(context).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_dy).into(vh.mImageView);
        }else{
            Glide.with(context).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_whr).into(vh.mImageView);

        }
        vh.addcar1.setOnClickListener(this);
        vh.addcar2.setOnClickListener(this);
//        vh.addcar1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainFragmentActivity.getInstance().setBadge(true,1);
//            }
//        });
//        vh.addcar2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainFragmentActivity.getInstance().setBadge(true,1);
//            }
//        });
//        goodsinfo
        return view;
    }
    class LifeViewHolder{
       ImageView mImageView;
       ImageView delImageView;
       TextView mTextView;
       TextView addcar1,addcar2;
    }
}