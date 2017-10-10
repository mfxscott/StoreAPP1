package com.xianhao365.o2o.adapter;

import android.app.Activity;
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
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.MessageEvent;
import com.xianhao365.o2o.entity.car.ShoppingCartLinesEntity;
import com.xianhao365.o2o.entity.car.ShoppingListEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.view.MyFoodActionCallback;

import org.greenrobot.eventbus.EventBus;

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
    public   Map<String,Boolean>  storeMap = new HashMap<String ,Boolean>();
    private ShoppingListEntity  shopCarinfo;
    private TextView delNumTv;
    public  CarRecyclerViewAdapter simpAdapter;
    public int itemTotal;//总商品条数

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mImageView;
        public final TextView nameTv;
        public final CheckBox  checkbox;
        public final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (TextView) view.findViewById(R.id.car_item_store_flag_iv);
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
    public CarStoreRecyclerViewAdapter(){

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
        if(shopCarinfo.getIsOwner().equals("1")){
            holder.mImageView.setVisibility(View.VISIBLE);
        }else{
            holder.mImageView.setVisibility(View.GONE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
//                intent.putExtra("cno",mValues.get(position).getShoppingCartLines().get(position).getGoodsCode());
//                holder.mView.getContext().startActivity(intent);
            }
        });
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (simpAdapter == null){
            //当第一次进入是才加载店铺中的商品，后面notifychange不设置子商品，房子店铺刷新子view重置
            if(shopCarinfo.getShoppingCartLines() != null && shopCarinfo.getShoppingCartLines().size()>0){
                simpAdapter = new CarRecyclerViewAdapter(context, shopCarinfo.getShoppingCartLines(),position, delNumTv);
                simpAdapter.initDate();
                holder.recyclerView.setAdapter(simpAdapter);
            }
        }
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(storeMap.get(position+""));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storeMap.put(position+"",isChecked);
                //获取到点击店铺check的商品 按钮数量
                int storenum = Integer.parseInt(delNumTv.getText().toString());
                if(isChecked){
                    simpAdapter.selectAll();
                    delNumTv.setText(getCheckTrue()+"");
                }else{
                    simpAdapter.initDate();
                    delNumTv.setText(getCheckTrue()+"");
                }
                EventBus.getDefault().post(new MessageEvent(3,"car"));
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
    //子商品全部选中时调用 选择店铺多选框
    public void  addCheckBox(int postion,boolean value){
        storeMap.put(postion+"",value);
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
        delNumTv.setText(itemTotal+"");
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
        delNumTv.setText(itemTotal+"");
        Logs.i("size大小============"+mValues.size());
        notifyDataSetChanged();

    }
    /**
     * 得到所有商品被选中的条数
     * @return
     */
    public String getCheckTrue(){
        int priceTotal = 0;
        Iterator<String> iter = simpAdapter.goodsMap.keySet().iterator();
        for(int i=0;i<mValues.size();i++){
            while (iter.hasNext()) {
                String key = iter.next();
                Boolean value = simpAdapter.goodsMap.get(key);
                if(value){
                    priceTotal ++;

                }
            }
        }
        return priceTotal+"";
    }


    //*********店铺商品中的item****************************************************************************************
    public  class CarRecyclerViewAdapter
            extends RecyclerView.Adapter<CarRecyclerViewAdapter.GoodsViewHolder> {
        private FoodActionCallback callback;
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        public List<ShoppingCartLinesEntity> mValues;
        private Context context;
        public Map<String,Boolean>  goodsMap = new HashMap<String ,Boolean>();
        public int total=0;//统计选择总条数
        private TextView numTv;
        private int storePostion;//当前店铺索引
        public  class GoodsViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView nameTv;
            public final CheckBox  checkbox;
            public final TextView  sub;
            public final TextView  number;
            public final TextView  add;
            public final TextView modelTv;

            public GoodsViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.car_item_iv);
                nameTv = (TextView) view.findViewById(R.id.car_item_name);
                checkbox = (CheckBox) view.findViewById(R.id.car_item_checkbox);
                sub = (TextView) view.findViewById(R.id.car_item_sub_tv);
                number = (TextView) view.findViewById(R.id.car_item_num_edt);
                add = (TextView) view.findViewById(R.id.car_item_add_tv);
                modelTv = (TextView) view.findViewById(R.id.car_item_model_tv);
            }
            @Override
            public String toString() {
                return super.toString() + " '" + nameTv.getText();
            }
        }
        public CarRecyclerViewAdapter(Context context, List<ShoppingCartLinesEntity> items,int position, TextView numTv) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;
            this.numTv = numTv;
            this.storePostion = position;
        }

        @Override
        public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.car_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new GoodsViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final GoodsViewHolder holder, final int position) {
          final  ShoppingCartLinesEntity shopCarinfo = mValues.get(position);
            holder.nameTv.setText(shopCarinfo.getGoodsName());
            holder.modelTv.setText("￥"+shopCarinfo.getSkuPrice()+"/"+shopCarinfo.getGoodsModel());
            holder.number.setText(shopCarinfo.getQuantity()+"");
            MainFragmentActivity.totalCarNum += Integer.parseInt(shopCarinfo.getQuantity());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent( holder.mView.getContext(), GoodsDetailActivity.class);
//                    intent.putExtra("cno",shopCarinfo.getGoodsCode());
//                    holder.mView.getContext().startActivity(intent);
                }
            });
            holder.checkbox.setOnCheckedChangeListener(null);
            holder.checkbox.setChecked(goodsMap.get(position+""));
            getKeyValue();
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    total = Integer.parseInt(numTv.getText().toString());
                    goodsMap.put(position+"",isChecked);
                    if(isChecked){
                        if(mValues.size() != total){
                            total++;
                        }
                    }else{
                        if(total != 0){
                            total--;
                        }
                    }
                    numTv.setText(total+"");
                    //循环遍历如果店铺想的商品被全部选中则店铺chexkBox为选中状态，反之
                    if(getAllItem()){
                        addCheckBox(storePostion,true);
                    }else{
                        addCheckBox(storePostion,false);
                    }
                    EventBus.getDefault().post(new MessageEvent(3,"car"));
                }
            });
            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carNum(false,holder.number,shopCarinfo);

                }
            });
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carNum(true,holder.number,shopCarinfo);
                    callback = new MyFoodActionCallback((Activity) context,shopCarinfo.getSkuBarcode());
                    callback.addAction(v);
                }
            });
            SXUtils.getInstance(context).GlideSetImg(shopCarinfo.getGoodsImage(),holder.mImageView);
        }
        /**
         * 判断是否是减，还是加入购物车
         * @param issub  true 增加
         */
        public void carNum(boolean issub, TextView textView, final ShoppingCartLinesEntity  shopcar){
            String nowsize = textView.getText().toString();
            int carTotalNum = Integer.parseInt(nowsize);
            if(issub){
                textView.setText((carTotalNum++)+"");
                SXUtils.getInstance(context).AddOrUpdateCar(shopcar.getSkuBarcode(),"1");
            }else{
                if(carTotalNum == 1){
                    SXUtils.getInstance(context).MyDialogView(context,"温馨提示!", "是否删除此商品?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SXUtils.getInstance(context).tipDialog.dismiss();
                            SXUtils.getInstance(context).AddOrUpdateCar(shopcar.getSkuBarcode(),"0");
                        }
                    });
                }else {
                    textView.setText((carTotalNum - 1) + "");
                    SXUtils.getInstance(context).AddOrUpdateCar(shopcar.getSkuBarcode(),SXUtils.getInstance(context).getCarNum(shopcar.getQuantity(),1));
                }
            }
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
        public  void  getKeyValue(){
            Iterator<String> iter = goodsMap.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                Boolean value = goodsMap.get(key);
            }
        }


//        //  删除数据
//        public void removeData() {
//            int i=0;
//            Iterator<String> iter = goodsMap.keySet().iterator();
//            while(iter.hasNext()){
//                String key=iter.next();
//                Boolean value = goodsMap.get(key);
//                i++;
//                if(value){
//                    int postions = Integer.parseInt(key);
//                    Logs.i(mValues.size()+"删除的key是多少====","==="+postions+"====="+i++);
//                    goodsMap.remove(postions);
//                    mValues.remove(mValues.size() == i ?i -1:i);
//                }
//                System.out.println(key+" "+value);
//            }
//            goodsMap.clear();
//            initDate();
//            notifyDataSetChanged();
//        }
        /**
         * 全选商品
         */
        public void selectAll() {
            for (int i = 0; i < mValues.size(); i++) {
                goodsMap.put(""+i,true);
            }
            numTv.setText(total-getMapKeyNum()+mValues.size()+"");
            total =mValues.size();
            notifyDataSetChanged();
        }
        /**
         * 初始化
         */
        public void initDate() {
            for (int i = 0; i < mValues.size(); i++) {
                goodsMap.put(""+i,false);
            }
            total = 0;
            notifyDataSetChanged();

        }

        /**
         * 获取店铺中商品被选中数量
         * @return
         */
        public int getMapKeyNum(){
            int num= 0;
            Iterator<String> iter = goodsMap.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                Boolean value = goodsMap.get(key);
                if(value){
                    num++;
                }
            }
            return num;
        }

        /**
         * 判断当前店铺下的商品是否全部选中,
         * 循环遍历如果店铺想的商品被全部选中则店铺chexkBox为选中状态，反之
         * @return
         */
        public boolean getAllItem(){
            Iterator<String> iter = goodsMap.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                Boolean value = goodsMap.get(key);
                if(!value){
                    return false;
                }
            }
            return true;
        }
    }
}