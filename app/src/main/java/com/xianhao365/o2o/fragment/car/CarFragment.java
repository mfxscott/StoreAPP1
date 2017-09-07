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

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.CarRecyclerViewAdapter;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.entity.car.CarList;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;

import org.json.JSONObject;

import java.util.ArrayList;
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
    private CarRecyclerViewAdapter simpAdapter;
    private TextView payDelBtn;//购买删除按钮
    private LinearLayout delNumLin;
    public TextView   delNumTv;//显示选择条目
    private CheckBox allCheckBox;
    private RelativeLayout  allYhRel;//购物车优惠显示布局
    private  List<GoodsInfoEntity> carlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car, null);
        activity = getActivity();
        init();
        SXUtils.showMyProgressDialog(activity,false);
        GetCarList();
//        SXUtils.getInstance(activity).setSysStatusBar(activity,R.color.white);
        return view;
    }
    /**
     * 商品分类详情商品
     * @return
     */
    private List<GoodsInfoEntity> getTypeInfoData()
    {
        carlist=new ArrayList<>();
        for(int i=0;i<10;i++){
            GoodsInfoEntity type = new GoodsInfoEntity();
            switch (i){
                case 0:
                    type.setGoodsName("dsfdf");
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
            }
            type.setGoodsName("我是商品标题"+i);
            carlist.add(type);

        }
        return carlist;
    }
    private void init(){
        LinearLayout lin = (LinearLayout) view.findViewById(R.id.car_go_shop_lin);
        lin.setOnClickListener(this);

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
                    simpAdapter.selectAll();
                }else{
                    simpAdapter.initDate();
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.main_car_recyclerv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        simpAdapter = new CarRecyclerViewAdapter(getActivity(),getTypeInfoData(),delNumTv);
        simpAdapter.initDate();
        recyclerView.setAdapter(simpAdapter);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        SXUtils.getInstance(activity).ToastCenter("成功");
                        break;
                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_edit_del:
                if(simpAdapter.showCheckb){
                    allYhRel.setVisibility(View.VISIBLE);
                    delNumLin.setVisibility(View.GONE);
                    payDelBtn.setText("结算");
                    editDelTv.setText("编辑");
                    simpAdapter.showCheckb = false;
                    simpAdapter.notifyDataSetChanged();
                }else{
                    allYhRel.setVisibility(View.GONE);
                    delNumLin.setVisibility(View.VISIBLE);
                    editDelTv.setText("完成");
                    payDelBtn.setText("删除");
                    simpAdapter.showCheckb = true;
                    simpAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.car_pay_del_btn:
                if(simpAdapter.showCheckb) {
                    simpAdapter.removeAllData();

                }else{
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
        HttpUtils.getInstance(activity).requestPost(false,AppClient.CARLIST, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("购物车成功返回参数=======",jsonObject.toString());
                JSONObject jsonObject1 = null;
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

}
























