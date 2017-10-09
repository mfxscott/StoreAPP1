package com.xianhao365.o2o.fragment.my.store.order;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.WaitPayRecyclerViewAdapter;
import com.xianhao365.o2o.entity.orderlist.OrderInfoEntity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

public class WaitDoneFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Activity activity;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private int indexPage=0;
    private List<OrderInfoEntity> cgList = new ArrayList<>();//采购列表数据
    private WaitPayRecyclerViewAdapter simpAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_done, container, false);
        activity = getActivity();
        initView();
        initData();
        //注册事件
        return view;
    }
    public void initData(){
        new MyOrderActivity().getOrderListHttp(indexPage,"50",hand);
    }
    private void initView(){
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.order_list_wait_done_swipe);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    indexPage = 0;
                    initData();
                }else{
                    indexPage ++;
                    initData();
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.order_wait_done_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        final WaitPayRecyclerViewAdapter simpAdapter = new WaitPayRecyclerViewAdapter(getActivity(),getBankData(),4);
//        recyclerView.setAdapter(simpAdapter);

    }
    public Handler   hand = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
//                        List<CGListInfoEntity> gde = (List<CGListInfoEntity>) msg.obj;
                    List<OrderInfoEntity> gde = (List<OrderInfoEntity>) msg.obj;
                    if(gde == null || gde.size() <=0){
                        view.findViewById(R.id.buy_order_list_done_lin).setVisibility(View.VISIBLE);
                    }
                    if(indexPage > 0 && gde.size()>0){
                        cgList.addAll(gde);
                    }else{
                        cgList.clear();
                        cgList.addAll(gde);
                    }
                    if(gde.size()>=10){
                        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                    }else{
                        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
                    }
                    if(indexPage >=1){
                        if(simpAdapter != null)
                            simpAdapter.notifyDataSetChanged();
                    }else{
                        simpAdapter = new WaitPayRecyclerViewAdapter(getActivity(),cgList,4);
                        recyclerView.setAdapter(simpAdapter);
                    }
                    break;
                case 1001:
                    break;
                case AppClient.ERRORCODE:
                    String msgs = (String) msg.obj;
                    SXUtils.getInstance(activity).ToastCenter(msgs);
                    break;
            }
            if(mSwipyRefreshLayout != null){
                mSwipyRefreshLayout.setRefreshing(false);
            }
            return true;
        }
    });
}
