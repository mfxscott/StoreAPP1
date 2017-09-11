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
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 购物车中的店铺
 */
public  class CarStoreRecyclerViewAdapter
        extends RecyclerView.Adapter<CarStoreRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    public List<ShoppingListEntity> mValues;
    public boolean showCheckb = false;
    private Context context;
    private Map<String,Boolean>  storeMap = new HashMap<String ,Boolean>();
    private ShoppingListEntity  shopCarinfo;
    private TextView delNumTv;
    public  CarRecyclerViewAdapter simpAdapter;
    public int itemTotal;//总商品条数

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
        initStoreDate();
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
                SXUtils.getInstance(context).ToastCenter("====" + position);
            }
        });


        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (simpAdapter == null){
            //当第一次进入是才加载店铺中的商品，后面notifychange不设置子商品，房子店铺刷新子view重置
            simpAdapter = new CarRecyclerViewAdapter(context, shopCarinfo.getShoppingCartLines(), delNumTv);
            holder.recyclerView.setAdapter(simpAdapter);
            simpAdapter.initDate();
        }
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(storeMap.get(position+""));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    simpAdapter.selectAll();
                }else{
                    simpAdapter.initDate();
                }
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
    //  删除数据
    public void removeAllData() {
        for(int i=0;i<mValues.size();i++){
            storeMap.remove(i);
            mValues.remove(i);
            notifyItemRemoved(i);
        }
        storeMap.clear();
        initStoreDate();
        notifyDataSetChanged();

    }

    /**
     * 全选商品
     */
    public void selectStoreAll() {
        for (int i = 0; i < mValues.size(); i++) {
            storeMap.put(""+i,true);
            if(simpAdapter != null)
                simpAdapter.selectAll();
        }
        itemTotal =mValues.size();
        delNumTv.setText("已选"+itemTotal+"项");
        notifyDataSetChanged();
    }
    /**
     * 初始化
     */
    public void initStoreDate() {
        for (int i = 0; i < mValues.size(); i++) {
            storeMap.put(""+i,false);
            if(simpAdapter != null)
                simpAdapter.initDate();
        }
        itemTotal = 0;
        delNumTv.setText("已选"+itemTotal+"项");
        Logs.i("size大小============"+mValues.size());
        notifyDataSetChanged();

    }
    public int getCarSize(){
        int carsl =0;
        for (int i = 0; i <mValues.size(); i++) {
            carsl += mValues.get(i).getShoppingCartLines().size();

        }
        return  carsl;
    }
    public  void  getKeyValue(){
        Iterator<String> iter = storeMap.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            Boolean value = storeMap.get(key);
        }
    }
}