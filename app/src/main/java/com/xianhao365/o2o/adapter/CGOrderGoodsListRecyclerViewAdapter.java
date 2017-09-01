package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.cgListInfo.CGPurchaseLinesEntity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 采购清单中的额商品列表
 *
 * @author mfx
 */
public class CGOrderGoodsListRecyclerViewAdapter
        extends RecyclerView.Adapter<CGOrderGoodsListRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();

    private int mBackground;
    private List<CGPurchaseLinesEntity> mValues;
    private Context context;
    private int tag;//标示订单类型进入显示不同按钮

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
      public ImageView itemImg;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemImg = (ImageView) mView.findViewById(R.id.item_img);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public CGOrderGoodsListRecyclerViewAdapter(Context context, List<CGPurchaseLinesEntity> items, int tag) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_item_layout, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CGPurchaseLinesEntity gcpurchase = mValues.get(position);
        SXUtils.getInstance(context).GlideSetImg(gcpurchase.getThumbImg(),holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}