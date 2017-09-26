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

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.WaitPayRecyclerViewAdapter;
import com.xianhao365.o2o.entity.cgListInfo.CGListInfoEntity;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.entity.orderlist.OrderListEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

/**
 *待发货
 */
public class WaitSendFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Activity activity;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private int indexPage=0;
    private Handler hand;
    private List<CGListInfoEntity> cgList = new ArrayList<>();//采购列表数据
    private WaitPayRecyclerViewAdapter simpAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_send, container, false);
        initView();
        getOrderListHttp(indexPage);
        return view;
    }
    private void initView(){

        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.order_list_wait_send_swipe);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    indexPage = 0;
                    getOrderListHttp(indexPage);
                }else{
                    indexPage ++;
                    getOrderListHttp(indexPage);
                }
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.order_wait_send_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        final WaitPayRecyclerViewAdapter simpAdapter = new WaitPayRecyclerViewAdapter(getActivity(),getBankData(),2);
//        recyclerView.setAdapter(simpAdapter);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
//                        List<CGListInfoEntity> gde = (List<CGListInfoEntity>) msg.obj;
                        List<CGListInfoEntity> gde = (List<CGListInfoEntity>) msg.obj;
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
                            simpAdapter = new WaitPayRecyclerViewAdapter(getActivity(),getBankData(),2);
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
    /**
     * @return
     */
    private ArrayList<GoodsInfoEntity> getBankData(){
        ArrayList<GoodsInfoEntity> list = new ArrayList<>();

        for(int i=0;i<3;i++){
            GoodsInfoEntity  info = new GoodsInfoEntity();
            info.setGoodsName("新鲜上市的西红柿");
            info.setShopPrice("￥10.00");
            list.add(info);
        }
        return list;
    }

    public void getOrderListHttp(int indexPage) {
        HttpParams params = new HttpParams();
        params.put("pageSize","10");
        params.put("pageIndex",indexPage);
        params.put("status","10");
        HttpUtils.getInstance(activity).requestPost(false, AppClient.TZ_ORDER_LIST, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("订单列表========",jsonObject.toString());
                //                CGBillListEntity gde = (CGBillListEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),CGBillListEntity.class);
                OrderListEntity gde =  ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),OrderListEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = gde.getDataset().getRows();
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
