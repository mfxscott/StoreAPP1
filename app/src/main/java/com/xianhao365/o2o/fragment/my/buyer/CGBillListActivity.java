package com.xianhao365.o2o.fragment.my.buyer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.CGOrderListRecyclerViewAdapter;
import com.xianhao365.o2o.entity.GoodsInfoEntity;

import java.util.ArrayList;

/**
 * 个人中心采购清单列表，供应商使用
 * @author mfx
 * @time  2017/8/30 17:11
 */
public class CGBillListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgbill_list);
        activity = this;
        initView();
    }

    private void initView(){
        registerBack();
        setTitle("采购清单列表");
        recyclerView = (RecyclerView) findViewById(R.id.cg_order_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final CGOrderListRecyclerViewAdapter simpAdapter = new CGOrderListRecyclerViewAdapter(activity,getBankData(),1);
        recyclerView.setAdapter(simpAdapter);
    }
    /**
     * @return
     */
    private ArrayList<GoodsInfoEntity> getBankData(){
        ArrayList<GoodsInfoEntity> list = new ArrayList<>();

        for(int i=0;i<2;i++){
            GoodsInfoEntity  info = new GoodsInfoEntity();
            info.setGoodsname("新鲜上市的西红柿");
            info.setGoodsPrice("￥10.00");
            list.add(info);
        }
        return list;
    }
}
