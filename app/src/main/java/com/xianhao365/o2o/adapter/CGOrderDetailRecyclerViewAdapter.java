package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.GoodsDetailActivity;
import com.xianhao365.o2o.entity.cgListInfo.CGPurchaseLinesEntity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.List;

/**
 * 采购订单详情
 * @author mfx
 */
public class CGOrderDetailRecyclerViewAdapter
        extends RecyclerView.Adapter<CGOrderDetailRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();

    private int mBackground;
    private List<CGPurchaseLinesEntity> mValues=null;
    private Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
      public ImageView itemImg;//
        public TextView name; //商品名称商品图片
        public TextView modelPrice;
        public TextView model;//售卖模式
        public TextView num;//数量
        public TextView tatol; //总金额
        public TextView markPrice;//市场价
        public TextView shopPrice;//商铺售价

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemImg = (ImageView) mView.findViewById(R.id.cg_order_detail_iv);
            name = (TextView) mView.findViewById(R.id.cg_order_goodsname_tv);
            modelPrice = (TextView) mView.findViewById(R.id.cg_order_detail_modelprice_tv);
            model = (TextView) mView.findViewById(R.id.cg_order_detail_model_tv);
            num = (TextView) mView.findViewById(R.id.cg_order_detail_num_tv);
            tatol = (TextView) mView.findViewById(R.id.cg_order_detail_tatol_tv);
            markPrice = (TextView) mView.findViewById(R.id.cg_order_detail_marketprice_tv);
            shopPrice = (TextView) mView.findViewById(R.id.cg_order_detail_shopprice_tv);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public CGOrderDetailRecyclerViewAdapter(Context context, List<CGPurchaseLinesEntity> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gc_order_detail_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CGPurchaseLinesEntity gcpurchase = mValues.get(position);
        holder.name.setText(gcpurchase.getGoodsName());
        holder.modelPrice.setText(gcpurchase.getTotalAmount());
        holder.model.setText("/"+gcpurchase.getGoodsUnit());
        holder.num.setText("x"+gcpurchase.getGoodsNumber());
        holder.tatol.setText(gcpurchase.getTotalAmount());
        holder.markPrice.setText(gcpurchase.getGoodsPrice());
        holder.shopPrice.setText(gcpurchase.getActualNumber());
        SXUtils.getInstance(context).GlideSetImg(gcpurchase.getThumbImg(),holder.itemImg);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
                intent.putExtra("cno",gcpurchase.getId());
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
        return super.getItemViewType(position);
    }

}