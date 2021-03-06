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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.SearchActivity;
import com.xianhao365.o2o.adapter.MainGoodsTypeAdapter;
import com.xianhao365.o2o.adapter.TypeInfoRecyclerViewAdapter;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.entity.goodstype.GoodsDataSetEntity;
import com.xianhao365.o2o.entity.goodstype.TypeChildrenEntity;
import com.xianhao365.o2o.entity.goodstype.TypeDataSetEntity;
import com.xianhao365.o2o.entity.goodstype.TypeGoodsEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/**
 * ***************************
 * 首页商品分类
 * @author mfx
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
    private TypeInfoRecyclerViewAdapter simpAdapter;//商品列表
    private Handler hand;
    private XTabLayout tabLayout;
    private List<TypeChildrenEntity> typeTwoList ;
    private ProgressBar progressBar;
    private RelativeLayout noInfoRel;
    private String idStr;//品牌ID
    private String cnoStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods, null);
        activity = getActivity();
        initView();
        SXUtils.getInstance(activity).showMyProgressDialog(activity,false);
        GetGoodsType();
//        GetGoodsTypeInfoHttp();
//        HttpUtil();
        return view;
    }
    private void initView(){
        progressBar = (ProgressBar)view.findViewById(R.id.goods_type_pro);
        noInfoRel = (RelativeLayout) view.findViewById(R.id.goods_type_no_rel);
        mSwipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.goods_type_swipyrefreshlayout);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    indexPage = 1;
                    GetGoodsTypeInfoHttp(idStr,cnoStr);
                }else{
                    indexPage ++;
                    GetGoodsTypeInfoHttp(idStr,cnoStr);
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
        //左侧二级商品分类
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SXUtils.getInstance(activity).ToastCenter("=="+position);
                typeAdapter.changeSelected(position);//刷新
                indexPage =1;
                if(typeTwoList != null && typeTwoList.size()>0)
                    idStr = typeTwoList.get(position).getId();
                cnoStr = typeTwoList.get(position).getCategoryCode();
                GetGoodsTypeInfoHttp(typeTwoList.get(position).getId(),typeTwoList.get(position).getCategoryCode());


            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.main_goods_info_gridv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



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
                        List<TypeDataSetEntity> TypeList= (List<TypeDataSetEntity>) msg.obj;
                        initViewPager(TypeList);
                        break;
                    case 1001:
                        List<GoodsInfoEntity> goodsDetaiLIst = (List<GoodsInfoEntity>) msg.obj;
                        if(goodsDetaiLIst == null || goodsDetaiLIst.size()<=0) {
                            if(indexPage ==1){
                                noInfoRel.setVisibility(View.VISIBLE);
                            }
                            break;
                        }
                        noInfoRel.setVisibility(View.GONE);
                        if(goodsDetaiLIst.size() >9){
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                        }else{
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);

                        }
                        simpAdapter = new TypeInfoRecyclerViewAdapter(getActivity(),goodsDetaiLIst);
                        recyclerView.setAdapter(simpAdapter);
//                typeAdapter= new MainGoodsTypeAdapter(activity,typeList.get(tab.getPosition()).getGoodsTypeList());
//                typeGridView.setAdapter(typeAdapter);
                        break;
                    case AppClient.ERRORCODE:
                        noInfoRel.setVisibility(View.VISIBLE);
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                if(mSwipyRefreshLayout != null){
                    mSwipyRefreshLayout.setRefreshing(false);
                }
                if(progressBar != null)
                progressBar.setVisibility(View.GONE);
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }
    private void initViewPager(final List<TypeDataSetEntity> typeList) {
        if(typeList == null ||typeList.size() <=0){
            SXUtils.getInstance(activity).ToastCenter("未查询到相关数据");
            return;
        }
        typeTwoList = typeList.get(0).getChildren();
        //第一次都是显示默认第一个二级分类
        typeAdapter= new MainGoodsTypeAdapter(activity,typeTwoList);
        typeGridView.setAdapter(typeAdapter);
////第一次加载默认第一项分类商品


        idStr = typeTwoList.get(0).getId();
        cnoStr = typeTwoList.get(0).getCategoryCode();
        GetGoodsTypeInfoHttp(typeTwoList.get(0).getId(),typeTwoList.get(0).getCategoryCode());
//            GetGoodsTypeInfoHttp(typeList.get(0).getGoodsTypeList().get(0).getCatNo(),typeList.get(0).getGoodsTypeList().get(0).getId());

        if (typeList != null) {
            for (int i = 0; i < typeList.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(typeList.get(i).getCategoryName() + ""));
            }
        }else{
            tabLayout.addTab(tabLayout.newTab().setText("肉类"));
            tabLayout.addTab(tabLayout.newTab().setText("鲜蔬菜"));
        }
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                indexPage = 1;
                typeAdapter= new MainGoodsTypeAdapter(activity,typeList.get(tab.getPosition()).getChildren());
                typeGridView.setAdapter(typeAdapter);
                if(typeList.get(tab.getPosition()).getChildren() != null && typeList.get(tab.getPosition()).getChildren().size()>0){
                    typeTwoList = typeList.get(tab.getPosition()).getChildren();
                    idStr = typeTwoList.get(0).getId();
                    cnoStr = typeTwoList.get(0).getCategoryCode();
                    GetGoodsTypeInfoHttp(typeTwoList.get(0).getId(),typeTwoList.get(0).getCategoryCode());
                }else{
                    recyclerView.setAdapter(null);
                }
//                List<GoodsTypeEntity> typeGoods= typeList.get(positoin).getGoodsTypeList();
//                typeTwoList = (List<GoodsTypeEntity>) typeGoods != null || typeGoods.size()>0? (List<GoodsTypeEntity>) typeGoods.get(0) :null;
//                GetGoodsTypeInfoHttp(typeTwoList != null || typeTwoList.size()>0?typeTwoList.get(0).getCatNo():"",typeTwoList != null || typeTwoList.size()>0?typeTwoList.get(0).getId():"");
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
     * 获取商品分类一，二级类目
     */
    public void GetGoodsType() {
        HttpParams httpParams = new HttpParams();
//        if(!TextUtils.isEmpty(code)){
//            httpParams.put("cid",code);
//        }
        HttpUtils.getInstance(activity).requestPost(false,AppClient.GOODS_TYPE, httpParams, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("商品分类发送成功返回参数=======",jsonObject.toString());
                JSONObject jsonObject1 = null;
//                    List<GoodsTypeEntity> goodsTypeList =  ResponseData.getInstance(activity).getGoodsTypeData(jsonObject);
                TypeGoodsEntity goodsTypeList = (TypeGoodsEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(), TypeGoodsEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = goodsTypeList.getDataset();
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
     * 根据商品分类查询商品数据
     * cid    分类ID
     cno 分类编码(支持模糊查询)
     bid          品牌ID
     goodsName   商品名称
     goodsPlace  产地
     foodGrade   等级
     goodsType   类型（ 1:热销 2:新品）
     supplyName  供应商名称
     bPrice  本店售价开始（查询本店售价区间）
     ePrice  本店售价结束（查询本店售价区间）
     bWholesalePrice  批发售价开始（查询本店售价区间）
     eWholesalePrice   批发售价结束（查询本店售价区间）
     */
    public void GetGoodsTypeInfoHttp(String cid,String cno){
        if(recyclerView != null)
        recyclerView.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
        HttpParams httpParams = new HttpParams();
        httpParams.put("categoryId",cid);
//        httpParams.put("categoryCode",cid);
        HttpUtils.getInstance(activity).requestPost(true,AppClient.GOODS_LIST, httpParams, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                String jsobj="";
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                     jsobj = jsonObject1.getString("responseData");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GoodsDataSetEntity gde = (GoodsDataSetEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsobj,GoodsDataSetEntity.class);
                Message msg = new Message();
                msg.what = 1001;
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
