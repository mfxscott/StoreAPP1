package com.xianhao365.o2o.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.GoodsDetailActivity;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.bill.CategoryListEntity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 常用清单
 * @author mfx
 * @time  2017/7/10 10:13
 */
public class HomeBillGridViewAdapter extends BaseAdapter implements View.OnClickListener{
    private   List<CategoryListEntity> result;
    private final LayoutInflater mLayoutInflater;
    private Activity context;
    private FoodActionCallback callback;
    public HomeBillGridViewAdapter(Activity context,   List<CategoryListEntity> result,FoodActionCallback callback) {
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
        final CategoryListEntity categInfo = result.get(position);
        LifeViewHolder vh;
        if (view == null) {
            vh = new LifeViewHolder();
            view = mLayoutInflater.inflate(R.layout.main_bill_item, null);
            vh.mImageView = (ImageView) view.findViewById(R.id.main_bill_item_iv);
            vh.mTextView = (TextView) view.findViewById(R.id.main_bill_item_name);
            vh.delImageView = (ImageView) view.findViewById(R.id.main_bill_item_del_iv);
//            vh.addcar1 = (TextView) view.findViewById(R.id.main_bill_addcar_tv);
//            vh.addcar2 = (TextView) view.findViewById(R.id.main_bill_addcar_tv2);
            vh.recyclerView = (RecyclerView) view.findViewById(R.id.bill_item_recycler);
            vh.liny = (LinearLayout) view.findViewById(R.id.home_bill_item_liny);
            view.setTag(vh);
        } else {
            vh = (LifeViewHolder) view.getTag();
        }

        vh.mTextView.setText(categInfo.getGoodsName());
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(vh.recyclerView.getContext()));
        vh.recyclerView.setItemAnimator(new DefaultItemAnimator());
        BillItemRecyclerViewAdapter simpAdapter = new BillItemRecyclerViewAdapter(vh.recyclerView.getContext(), categInfo);
        vh.recyclerView.setAdapter(simpAdapter);
        SXUtils.getInstance(context).GlideSetImg(categInfo.getOriginalImg(),vh.mImageView);
        vh.delImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.remove(position);
                notifyDataSetChanged();
//                removeData(position);
            }
        });
        vh.liny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("cno",categInfo.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
    class LifeViewHolder{
       ImageView mImageView;
       ImageView delImageView;
       TextView mTextView;
       TextView addcar1,addcar2;
        RecyclerView recyclerView;
        LinearLayout liny;
    }
}