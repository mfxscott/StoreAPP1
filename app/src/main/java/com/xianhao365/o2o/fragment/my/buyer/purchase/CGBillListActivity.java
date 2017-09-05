package com.xianhao365.o2o.fragment.my.buyer.purchase;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.CGOrderListRecyclerViewAdapter;
import com.xianhao365.o2o.entity.GoodsInfoEntity;
import com.xianhao365.o2o.entity.cgListInfo.CGBillListEntity;
import com.xianhao365.o2o.entity.cgListInfo.CGListInfoEntity;
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
 * 个人中心采购清单列表，供应商使用
 * @author mfx
 * @time  2017/8/30 17:11
 */
public class CGBillListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Activity activity;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private int indexPage=0;
    private Handler hand;
    private CGOrderListRecyclerViewAdapter simpAdapter;
    private List<CGListInfoEntity> cgList = new ArrayList<>();//采购列表数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgbill_list);
        activity = this;
        initView();
        GetGYSBillListHttp(indexPage);
    }

    private void initView(){
        registerBack();
        setTitle("采购清单列表");

        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.cg_list_swipyrefreshlayout);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
//                httpChanner();
                    indexPage = 0;
//                    hand.sendEmptyMessage(1000);
                    GetGYSBillListHttp(indexPage);
//                    HttpLiveSp(indexPage);
                }else{
//                    hand.sendEmptyMessage(1000);
                    indexPage ++;
                    GetGYSBillListHttp(indexPage);
//                    HttpLiveSp(indexPage);
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.cg_order_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        final CGOrderListRecyclerViewAdapter simpAdapter = new CGOrderListRecyclerViewAdapter(activity,getBankData(),1);
//        recyclerView.setAdapter(simpAdapter);

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        List<CGListInfoEntity> gde = (List<CGListInfoEntity>) msg.obj;
                        if(indexPage > 0 && gde.size()>0){
                            cgList.addAll(gde);
                        }else{
                            cgList.clear();
                            cgList.addAll(gde);
                        }
                        Logs.i("采购列表数量========"+gde.size());
                        if(gde.size()>=10){
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);

                        }else{
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
                        }
                        if(indexPage >=1){
                            if(simpAdapter != null)
                            simpAdapter.notifyDataSetChanged();
                        }else{
                            simpAdapter = new CGOrderListRecyclerViewAdapter(activity,cgList,1);
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

        for(int i=0;i<2;i++){
            GoodsInfoEntity  info = new GoodsInfoEntity();
            info.setGoodsname("新鲜上市的西红柿");
            info.setGoodsPrice("￥10.00");
            list.add(info);
        }
        return list;
    }
    /**
     * 获取供应商采购列表
     */
    public void GetGYSBillListHttp(int indexPage) {
        HttpParams params = new HttpParams();
        params.put("pageSize","10");
        params.put("pageIndex",indexPage);
        HttpUtils.getInstance(activity).requestPost(false,AppClient.GYS_BILLLIST, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                CGBillListEntity gde = (CGBillListEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),CGBillListEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = gde.getDataset();
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
