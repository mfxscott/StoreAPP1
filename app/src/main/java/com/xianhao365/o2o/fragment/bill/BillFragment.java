package com.xianhao365.o2o.fragment.bill;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.SearchActivity;
import com.xianhao365.o2o.adapter.HomeBillRecyclerViewAdapter;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.GoodsInfoEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.view.NXHooldeView;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import static com.xianhao365.o2o.fragment.MainFragmentActivity.badge1;
/**
 * ***************************
 * 首页采购清单
 * @author mfx
 * ***************************
 */
public class BillFragment extends Fragment {
    private  View view;
    private Activity activity;
    private OKManager manager;//工具类
    private Handler hand;
    private int indexPage= 1;
    private RecyclerView recyclerView;
    private HomeBillRecyclerViewAdapter billInfoAdapter;
    private SwipyRefreshLayout   mSwipyRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, null);
        activity = getActivity();
        manager = new OKManager(activity);
        init();
//        HttpLiveSp(indexPage);
//        SXUtils.getInstance().setSysStatusBar(activity,R.color.white);
        return view;
    }
    /**
     * 商品分类详情商品
     * @return
     */
    private List<GoodsInfoEntity> getTypeInfoData()
    {
        List<GoodsInfoEntity> typeList=new ArrayList<>();
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
            typeList.add(type);

        }
        return typeList;
    }
    private void init(){
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.bill_swipyrefreshlayout);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
//                httpChanner();
                    indexPage = 1;
                    hand.sendEmptyMessage(1);
//                    HttpLiveSp(indexPage);
                }else{
                    hand.sendEmptyMessage(1);
                    indexPage ++;
//                    HttpLiveSp(indexPage);
                }
            }
        });


        LinearLayout searchlin = (LinearLayout) view.findViewById(R.id.bill_search_liny);
        searchlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.main_bill_gridv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final HomeBillRecyclerViewAdapter simpAdapter = new HomeBillRecyclerViewAdapter(getActivity(),getTypeInfoData(),new FoodActionCallback(){

            @Override
            public void addAction(View view) {
                NXHooldeView nxHooldeView = new NXHooldeView(activity);
                int position[] = new int[2];
                view.getLocationInWindow(position);
                nxHooldeView.setStartPosition(new Point(position[0], position[1]));
                ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
                rootView.addView(nxHooldeView);
                int endPosition[] = new int[2];
                badge1.getLocationInWindow(endPosition);
                nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
                nxHooldeView.startBeizerAnimation();
                MainFragmentActivity.getInstance().setBadge(true,1);
            }
        });
        recyclerView.setAdapter(simpAdapter);

//
//        billInfoAdapter= new HomeBillRecyclerViewAdapter(activity,getTypeInfoData());
//        gridView.setAdapter(billInfoAdapter);
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                SXUtils.getInstance(activity).ToastCenter("=="+position);
////                billInfoAdapter.changeSelected(position);//刷新
//            }
//        });
        initViewPager();
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:

                }
                if(mSwipyRefreshLayout != null){
                    mSwipyRefreshLayout.setRefreshing(false);
                }
                return true;
            }
        });

    }
    private void initViewPager() {
        XTabLayout tabLayout = (XTabLayout) view.findViewById(R.id.bill_xTablayout);
//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("肉禽类"));
        tabLayout.addTab(tabLayout.newTab().setText("新鲜蔬菜"));
        tabLayout.addTab(tabLayout.newTab().setText("米面粮油"));
        tabLayout.addTab(tabLayout.newTab().setText("水产冻货"));
        tabLayout.addTab(tabLayout.newTab().setText("休闲酒饮"));
        tabLayout.addTab(tabLayout.newTab().setText("面食面粉"));
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                Logs.i("tab===============111111="+ tab.getPosition());
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
                Logs.i("tab===============222222222="+ tab.getPosition());
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {
                Logs.i("tab===============3333333333="+ tab.getPosition());
            }
        });

    }
}
























