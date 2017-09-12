package com.xianhao365.o2o.fragment.car;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.CarStoreRecyclerViewAdapter;
import com.xianhao365.o2o.entity.MessageEvent;
import com.xianhao365.o2o.entity.car.CarList;
import com.xianhao365.o2o.entity.car.ShoppingListEntity;
import com.xianhao365.o2o.entity.car.TakeNoPartInActivitiesEntity;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * ***************************
 * 首页购物车
 * @author mfx
 * ***************************
 */
public class CarFragment extends Fragment implements View.OnClickListener{
    private  View view;
    private Activity activity;
    private Handler hand;
    private int indexPage= 1;
    private RecyclerView recyclerView;
    private TextView editDelTv;//编辑 确定
    private CarStoreRecyclerViewAdapter storesimpAdapter;
    private TextView payDelBtn;//购买删除按钮
    private LinearLayout delNumLin;
    public TextView   delNumTv;//显示选择条目
    private TextView noticTv;//满减活动
    private TextView totalTv;//购物车总价格
    private CheckBox allCheckBox;
    private RelativeLayout  allYhRel;//购物车优惠显示布局
    private  List<GoodsInfoEntity> carlist;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private List<ShoppingListEntity> shopList = new ArrayList<>();//总购物车数量

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car, null);
        activity = getActivity();
        init();
        SXUtils.showMyProgressDialog(activity,false);
        //注册事件
        EventBus.getDefault().register(this);
        if(SXUtils.getInstance(activity).IsLogin())
            GetCarList();

//        SXUtils.getInstance(activity).setSysStatusBar(activity,R.color.white);
        return view;
    }

    private void init(){
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.car_swipyrefreshlayout);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    indexPage = 1;
                    GetCarList();
                }else{
                    indexPage ++;
                    GetCarList();
                }
            }
        });
        LinearLayout lin = (LinearLayout) view.findViewById(R.id.car_go_shop_lin);
        lin.setOnClickListener(this);
        noticTv = (TextView) view.findViewById(R.id.car_notice_tv);
        totalTv = (TextView) view.findViewById(R.id.car_total_tv);


        allYhRel = (RelativeLayout) view.findViewById(R.id.car_all_yh_rel);

        editDelTv = (TextView) view.findViewById(R.id.car_edit_del);
        editDelTv.setOnClickListener(this);

        payDelBtn = (TextView) view.findViewById(R.id.car_pay_del_btn);
        payDelBtn.setOnClickListener(this);

        delNumLin = (LinearLayout) view.findViewById(R.id.car_del_num_liny);
        delNumTv = (TextView) view.findViewById(R.id.car_del_num_tv);

        allCheckBox = (CheckBox) view.findViewById(R.id.car_all_checkbox);
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    storesimpAdapter.selectStoreAll();
                }else{
                    storesimpAdapter.initStoreDate();
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.main_car_recyclerv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        CarList car = (CarList) msg.obj;

                        if(car.getShoppingList().size() >=10){
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                        }else{
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
                        }
                        totalTv.setText("¥"+car.getGrandTotal());
                        List<TakeNoPartInActivitiesEntity> noticList = car.getTakeNoPartInActivities();
                        if(noticList !=null && noticList.size() >0){
                            //取第一个满减信息
                            noticTv.setText(noticList.get(0).getNotice());
                        }

                        if(indexPage == 1){
                            shopList.clear();
                            if(car.getShoppingList() != null && car.getShoppingList().size()>0){
                                shopList.addAll(car.getShoppingList());
                            }
                            MainFragmentActivity.totalCarNum = 0;
                            MainFragmentActivity.totalCarNum = getCarTotalItem();
                            storesimpAdapter = new CarStoreRecyclerViewAdapter(getActivity(),shopList,delNumTv);
                            recyclerView.setAdapter(storesimpAdapter);
                        }else{
                            if(car.getShoppingList() != null && car.getShoppingList().size()>0){
                                shopList.addAll(car.getShoppingList());
                            }
                            MainFragmentActivity.totalCarNum = getCarTotalItem();
                            if(storesimpAdapter != null)
                                storesimpAdapter.notifyDataSetChanged();
                        }
                        MainFragmentActivity.getInstance().setBadgeNum(MainFragmentActivity.totalCarNum);
                        break;

                    case 1001:
                        String clearcar = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(clearcar+"");
                        //清除购物车成功
                        recyclerView.setAdapter(null);
                        allYhRel.setVisibility(View.VISIBLE);
                        delNumLin.setVisibility(View.GONE);
                        payDelBtn.setText("结算");
                        editDelTv.setText("编辑");
                        storesimpAdapter.showCheckb = false;
                        MainFragmentActivity.getInstance().setBadgeNum(0);
                        storesimpAdapter.notifyDataSetChanged();
                        break;

                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
                        break;
                }
                if(mSwipyRefreshLayout != null){
                    mSwipyRefreshLayout.setRefreshing(false);
                }
                SXUtils.DialogDismiss();
                Logs.i("=====","====");
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_edit_del:
                if(storesimpAdapter.showCheckb){
                    allYhRel.setVisibility(View.VISIBLE);
                    delNumLin.setVisibility(View.GONE);
                    payDelBtn.setText("结算");
                    editDelTv.setText("编辑");
                    storesimpAdapter.showCheckb = false;
                    storesimpAdapter.notifyDataSetChanged();
                }else{
                    allYhRel.setVisibility(View.GONE);
                    delNumLin.setVisibility(View.VISIBLE);
                    editDelTv.setText("完成");
                    payDelBtn.setText("删除");
                    storesimpAdapter.showCheckb = true;
                    storesimpAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.car_pay_del_btn:
                if(storesimpAdapter.showCheckb) {
                    SXUtils.showMyProgressDialog(activity,false);
                    clearCarList();
                }else{
                    Logs.i("所有商品价格===="+getCarTotalMoney());
                    Intent pay = new Intent(activity,GoPayActivity.class);
                    startActivity(pay);
                }
                break;
            case R.id.car_go_shop_lin:
                MainFragmentActivity.goodsRb.setChecked(true);
                break;
        }
    }
    /**
     * 获取购物车列表
     */
    public void GetCarList() {
        HttpParams params = new HttpParams();
        params.put("pageSize","10");
        params.put("pageIndex",indexPage+"");
        HttpUtils.getInstance(activity).requestPost(false,AppClient.CARLIST, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("购物车成功返回参数=======",jsonObject.toString());
                CarList car = (CarList) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),CarList.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = car;
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                hand.sendMessage(msg);

            }
        });
    }
    /**
     * 清空购物车
     */
    public void clearCarList() {
        HttpUtils.getInstance(activity).requestPost(false,AppClient.CLEARCAR, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("清空购物车成功返回参数=======",jsonObject.toString());
                JSONObject jsonObject1 = null;
                Message msg = new Message();
                msg.what = 1001;
                msg.obj = "清空购物车成功";
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                hand.sendMessage(msg);

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        //传递1or2  都刷新购物车
        if(messageEvent.getTag()==1 ||messageEvent.getTag()==2){
            GetCarList();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取被选中的商品价格及商品skucode
     * @return
     */
    public String getCarTotalMoney(){
        float priceTotal = 0;
        Iterator<String> iter = storesimpAdapter.simpAdapter.goodsMap.keySet().iterator();
       for(int i=0;i<shopList.size();i++){
            while (iter.hasNext()) {
                String key = iter.next();
                Boolean value = storesimpAdapter.simpAdapter.goodsMap.get(key);
                 if(value){
                     int postions = Integer.parseInt(key);
                     priceTotal += Float.parseFloat(shopList.get(i).getShoppingCartLines().get(postions).getSkuPrice());
                 }
            }
        }
        return priceTotal+"";
    }
    public int getCarTotalItem(){
        int carItem = 0;
        for (int i = 0; i < shopList.size(); i++) {
            for (int j = 0; j < shopList.get(i).getShoppingCartLines().size(); j++) {
                carItem += Integer.parseInt(shopList.get(i).getShoppingCartLines().get(j).getQuantity());
            }
        }
        return carItem;
    }
}
























