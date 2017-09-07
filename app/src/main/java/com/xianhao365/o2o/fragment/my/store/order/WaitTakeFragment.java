package com.xianhao365.o2o.fragment.my.store.order;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.WaitPayRecyclerViewAdapter;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitTakeFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_take, container, false);
        initView();
        return view;
    }
    private void initView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.order_wait_take_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final WaitPayRecyclerViewAdapter simpAdapter = new WaitPayRecyclerViewAdapter(getActivity(),getBankData(),3);
        recyclerView.setAdapter(simpAdapter);
    }
    /**
     * @return
     */
    private ArrayList<GoodsInfoEntity> getBankData(){
        ArrayList<GoodsInfoEntity> list = new ArrayList<>();

        for(int i=0;i<8;i++){
            GoodsInfoEntity  info = new GoodsInfoEntity();
            info.setGoodsName("新鲜上市的西红柿");
            info.setShopPrice("￥10.00");
            list.add(info);
        }
        return list;
    }
}
