package com.xianhao365.o2o.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.GoodsInfoEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * @author mfx
 * @email mfx@ddbill.com
 * @time  2017/7/11 16:57
 */
public  class CarRecyclerViewAdapter
        extends RecyclerView.Adapter<CarRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    public List<GoodsInfoEntity> mValues;
    public boolean showCheckb = false;
    private Context context;
    private Map<String,Boolean>  map = new HashMap<String ,Boolean>();
    public int total=0;//统计选择总条数
    private TextView numTv;
    private int carTotalNum=0;//点击


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView nameTv;
        public final CheckBox  checkbox;
        public final TextView  sub;
        public final TextView  number;
        public final TextView  add;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.car_item_iv);
            nameTv = (TextView) view.findViewById(R.id.car_item_name);
            checkbox = (CheckBox) view.findViewById(R.id.car_item_checkbox);
            sub = (TextView) view.findViewById(R.id.car_item_sub_tv);
            number = (TextView) view.findViewById(R.id.car_item_num_edt);
            add = (TextView) view.findViewById(R.id.car_item_add_tv);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + nameTv.getText();
        }
    }
    public CarRecyclerViewAdapter(Context context, List<GoodsInfoEntity> items, TextView numTv) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.context = context;
        this.numTv = numTv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTv.setText(mValues.get(position).getGoodsname());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SXUtils.getInstance(context).ToastCenter("===="+position);
            }
        });

//        holder.modeTView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                removeData(position);
//            }
//        });


        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(map.get(position+""));
        getKeyValue();
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                if(map.containsKey(""+position)){
//                    map.remove(""+position);
//                }else{
                SXUtils.getInstance(holder.checkbox.getContext()).ToastCenter(position+"=="+mValues.get(position).getGoodsname());
                map.put(position+"",isChecked);
//                }

                if(isChecked){
                    if(mValues.size() != total){
                        total++;
                    }
                }else{
                    if(total != 0){
                        total--;
                    }
                }
                numTv.setText("已选"+total+"项");
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carNum(false,1,holder.number);

            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carNum(true,1,holder.number);

            }
        });

        if(position%2 ==0){
            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_dg).into(holder.mImageView);
        }else{
            Glide.with(holder.mImageView.getContext()).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_gdy).into(holder.mImageView);
        }

//        Glide.with(holder.mImageView.getContext())
//                .load("http://img4.imgtn.bdimg.com/it/u=3071322373,3354763627&fm=28&gp=0.jpg")
//                .fitCenter()
//                .into(holder.mImageView);
    }
    /**
     * 判断是否是减，还是加入购物车
     * @param issub  true 增加
     * @param strcount  增加条数
     */
    public void carNum(boolean issub,int strcount,TextView textView){
        String nowsize = textView.getText().toString();
        int carTotalNum = Integer.parseInt(nowsize);
        if(issub){
            carTotalNum = carTotalNum+1;
            if(carTotalNum >= 100){
                textView.setText("99+");
            }else{
                textView.setText(carTotalNum+"");
            }
            textView.setTextColor(Color.BLACK);
            MainFragmentActivity.getInstance().setBadge(true,1);
        }else{
            carTotalNum = carTotalNum-1;
            if(carTotalNum > 0){
                if(carTotalNum <= 99){
                    textView.setText(carTotalNum+"");
                }else{
                    textView.setText("99+");
                }
            }else{
                textView.setText("0");
                textView.setTextColor(Color.TRANSPARENT);
            }
            if(carTotalNum >= 0)
            MainFragmentActivity.getInstance().setBadge(false,1);
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
    //  添加数据
    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条
        notifyItemInserted(position);
    }
    public  void  getKeyValue(){
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            Boolean value = map.get(key);
//            Logs.i(key+"========================"+value);
        }
    }
    //  删除数据
    public void removeData() {
        int i=0;
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            Boolean value = map.get(key);
            i++;
            if(value){
                int postions = Integer.parseInt(key);
                Logs.i(mValues.size()+"删除的key是多少====","==="+postions+"====="+i++);
//                if(i!=0)
//                    postions= postions-1;

                map.remove(postions);
                mValues.remove(mValues.size() == i ?i -1:i);
//                notifyItemRemoved(postions);
//                notifyItemRangeChanged(postions, mValues.size());
            }
            System.out.println(key+" "+value);
        }
        map.clear();
        initDate();
        notifyDataSetChanged();
    }
    //  删除数据
    public void removeAllData() {
        for(int i=0;i<mValues.size();i++){
            map.remove(i);
            mValues.remove(i);
            notifyItemRemoved(i);
        }
        map.clear();
        initDate();
        notifyDataSetChanged();

    }

    /**
     * 全选商品
     */
    public void selectAll() {
        for (int i = 0; i < mValues.size(); i++) {
//            if(!map.containsKey(""+i))
            map.put(""+i,true);
        }
        total =mValues.size();
        numTv.setText("已选"+total+"项");
        notifyDataSetChanged();
    }
    /**
     * 初始化
     */
    public void initDate() {
        for (int i = 0; i < mValues.size(); i++) {
            map.put(""+i,false);
        }
        total = 0;
        numTv.setText("已选"+total+"项");
        Logs.i("size大小============"+mValues.size());
        notifyDataSetChanged();

    }
}