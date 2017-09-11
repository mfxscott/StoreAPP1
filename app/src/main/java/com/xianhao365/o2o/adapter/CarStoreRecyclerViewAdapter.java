package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.car.ShoppingListEntity;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class CarStoreRecyclerViewAdapter
        extends RecyclerView.Adapter<CarStoreRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    public List<ShoppingListEntity> mValues;
    public boolean showCheckb = false;
    private Context context;
    private Map<String,Boolean>  map = new HashMap<String ,Boolean>();
    private ShoppingListEntity  shopCarinfo;
    private TextView delNumTv;
    public  CarRecyclerViewAdapter simpAdapter;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView nameTv;
        public final CheckBox  checkbox;
        public final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.car_item_store_flag_iv);
            nameTv = (TextView) view.findViewById(R.id.car_item_store_name);
            checkbox = (CheckBox) view.findViewById(R.id.car_item_store_checkbox);
            recyclerView = (RecyclerView) view.findViewById(R.id.car_goods_recyclerv);

        }
        @Override
        public String toString() {
            return super.toString() + " '" + nameTv.getText();
        }
    }
    public CarStoreRecyclerViewAdapter(Context context, List<ShoppingListEntity> items,TextView delNumTv) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.delNumTv = delNumTv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_store_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        shopCarinfo = mValues.get(position);
        holder.nameTv.setText(shopCarinfo.getShopName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SXUtils.getInstance(context).ToastCenter("===="+position);
            }
        });



        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
          simpAdapter = new CarRecyclerViewAdapter(context,shopCarinfo.getShoppingCartLines(),delNumTv);
        simpAdapter.initDate();
        holder.recyclerView.setAdapter(simpAdapter);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    @Override
    public int getItemViewType(int position) {
//        Log.i("========",position+"");
        return super.getItemViewType(position);
    }
}