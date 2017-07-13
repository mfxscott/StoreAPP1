package com.scott.shopplat.fragment.car;

import android.app.Activity;
import android.app.Fragment;
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

import com.scott.shopplat.R;
import com.scott.shopplat.adapter.CarRecyclerViewAdapter;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ***************************
 * 生活版块
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 16/10/29 下午9:28
 * ***************************
 */
public class CarFragment extends Fragment implements View.OnClickListener{
    private  View view;
    private Activity activity;
    private OKManager manager;//工具类
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
        manager = new OKManager(activity);
        init();
        HttpLiveSp();
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
                    type.setGoodsname("dsfdf");
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
            type.setGoodsname("我是商品标题"+i);
            carlist.add(type);

        }
        return carlist;
    }
    private void init(){
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
                return true;
            }
        });
    }
    private void HttpLiveSp() {
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
                simpAdapter.removeAllData();
                break;
        }

    }
}
























