package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.utils.SXUtils;

/**
 * 商品详情中的商品信息说明图
 * @author mfx
 */
public class GoodsDetailInfoRecyclerViewAdapter
        extends RecyclerView.Adapter<GoodsDetailInfoRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();

    private int mBackground;
    private String[] mValues;
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
            itemImg = (ImageView) mView.findViewById(R.id.item_add_img);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
    public GoodsDetailInfoRecyclerViewAdapter(Context context, String[] items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_item_add_layout, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SXUtils.getInstance(context).GlideSetImg(mValues[position],holder.itemImg);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
//                intent.putExtra("cno",gcpurchase.getId());
//                holder.mView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mValues.length;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}