package com.xianhao365.o2o.fragment.goods;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.SearchActivity;
import com.xianhao365.o2o.adapter.MainGoodsTypeAdapter;
import com.xianhao365.o2o.adapter.TypeInfoRecyclerViewAdapter;
import com.xianhao365.o2o.entity.GoodsInfoEntity;
import com.xianhao365.o2o.entity.GoodsTypeEntity;
import com.xianhao365.o2o.entity.MainGoodsTypeEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * ***************************
 * 主页金融
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 16/10/30 上午11:30
 * ***************************
 */
public class GoodsListFragment extends Fragment {
    private  View view;
    private Activity activity;
    private GridView typeGridView;
    private RecyclerView recyclerView;
    private  MainGoodsTypeAdapter  typeAdapter;
    private int indexPage= 1;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private Handler hand;
    private XTabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods, null);
        activity = getActivity();
        initView();
        GetGoodsType();
        return view;
    }

    /**
     * 二级分类
     * @return
     */
    private List<MainGoodsTypeEntity> getTypeData()
    {
        List<MainGoodsTypeEntity> typeList=new ArrayList<>();
        for(int i=0;i<10;i++){
            MainGoodsTypeEntity type = new MainGoodsTypeEntity();
            switch (i){
                case 0:
                    type.setTypeName("促销");
                    break;
                case 1:
                    type.setTypeName("根茎");
                    break;
                case 2:
                    type.setTypeName("绿色");
                    break;
                case 3:
                    type.setTypeName("萝卜");
                    break;
                case 4:
                    type.setTypeName("豆类");
                    break;
                case 5:
                    type.setTypeName("特菜");
                    break;
                case 6:
                    type.setTypeName("肉类");
                    break;
                case 7:
                    type.setTypeName("鱼类");
                    break;
                case 8:
                    type.setTypeName("油点类");
                    break;
                default:
                    type.setTypeName("其他");

            }
            typeList.add(type);

        }
        return typeList;
    }

    /**
     * 商品分类详情商品 一级分类
     * @return
     */
    private List<GoodsInfoEntity> getTypeInfoData()
    {
        List<GoodsInfoEntity> typeList=new ArrayList<>();
        for(int i=0;i<10;i++){
            GoodsInfoEntity type = new GoodsInfoEntity();
            switch (i){
                case 0:
                    type.setGoodsname("鸡肉");
                    break;
                case 1:
                    type.setGoodsname("鲜蔬菜");
                    break;
                case 2:
                    type.setGoodsname("豆芽");
                    break;
                case 3:
                    type.setGoodsname("牛肉");
                    break;
                case 4:
                    type.setGoodsname("鸭肉");
                    break;
                default:
                    type.setGoodsname("西瓜");

            }
            typeList.add(type);

        }
        return typeList;
    }
    private void initView(){

        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.goods_type_swipyrefreshlayout);
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


        LinearLayout searchLin = (LinearLayout) view.findViewById(R.id.all_goods_search_lin);
        searchLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });

         tabLayout = (XTabLayout) view.findViewById(R.id.goods_xTablayout);
        typeGridView = (GridView) view.findViewById(R.id.main_goods_type_gridv);
//        typeAdapter= new MainGoodsTypeAdapter(activity,getTypeData());
//        typeGridView.setAdapter(typeAdapter);
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SXUtils.getInstance(activity).ToastCenter("=="+position);
                typeAdapter.changeSelected(position);//刷新

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.main_goods_info_gridv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final TypeInfoRecyclerViewAdapter simpAdapter = new TypeInfoRecyclerViewAdapter(getActivity(),getTypeInfoData());
        recyclerView.setAdapter(simpAdapter);


//        infoGridView = (GridView) view.findViewById(R.id.main_goods_info_gridv);
//        infoGridView.setAdapter(new MainTypeInfoAdapter(activity,getTypeInfoData()));
//        infoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SXUtils.getInstance(activity).ToastCenter("=="+position);
//            }
//        });

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        List<GoodsTypeEntity> TypeList= (List<GoodsTypeEntity>) msg.obj;
                        Logs.i("分类size======================"+TypeList.size());
                        initViewPager(TypeList);
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
    private void initViewPager(final List<GoodsTypeEntity> typeList) {
        if (typeList != null) {
            for (int i = 0; i < typeList.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(typeList.get(i).getName() + ""));
            }
        }else{
            tabLayout.addTab(tabLayout.newTab().setText("肉类"));
            tabLayout.addTab(tabLayout.newTab().setText("鲜蔬菜"));
        }
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                Logs.i("tab===============111111="+ tab.getPosition());
                typeAdapter= new MainGoodsTypeAdapter(activity,typeList.get(tab.getPosition()).getGoodsTypeList());
                typeGridView.setAdapter(typeAdapter);
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

    /**
     * 获取商品分类
     */
    public void GetGoodsType(){
        RequestBody requestBody = new FormBody.Builder()
//                .add("catNo", "00002-00001")//二级分类查询00002
//                .add("vcode", codeStr)
//                .add("registerType", "0")//0=手机,1=微信,2=QQ
//                .add("password", psdStr)
//                .add("tag","64")
                .build();
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.GOODS_TYPE, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("商品分类发送成功返回参数=======",jsonObject.toString());
                JSONObject jsonObject1 = null;
                try {
                    List<GoodsTypeEntity> goodsTypeList =  ResponseData.getGoodsTypeData(jsonObject);
                    Message msg = new Message();
                    msg.what = 1000;
                    msg.obj = goodsTypeList;
                    hand.sendMessage(msg);
                } catch (JSONException e) {
                    Message msg = new Message();
                    msg.what = AppClient.ERRORCODE;
                    msg.obj = e.toString();
                    hand.sendMessage(msg);
                }

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
