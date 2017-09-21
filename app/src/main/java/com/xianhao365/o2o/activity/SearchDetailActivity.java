package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.TypeInfoRecyclerViewAdapter;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.entity.goodstype.GoodsDataSetEntity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailActivity extends AppCompatActivity {
private Activity activity;
    private RecyclerView recyclerView;
    private String searchValueStr;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private Handler hand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        activity = this;
        searchValueStr = this.getIntent().getStringExtra("searchValue");
        initView();
        GetGoodsTypeInfoHttp(searchValueStr+"");
    }
    private void initView(){
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.search_detail_swipyrefreshlayout);
        SXUtils.getInstance(activity).setColorSchemeResources(mSwipyRefreshLayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
//                    indexPage = 1;
//                    hand.sendEmptyMessage(1);
////                    HttpLiveSp(indexPage);
//                }else{
//                    hand.sendEmptyMessage(1);
//                    indexPage ++;
////                    HttpLiveSp(indexPage);
                }
            }
        });

        EditText  searchEdt = (EditText) findViewById(R.id.search_detail_editv);
        searchEdt.setText(searchValueStr+"");
        //点击按下键盘搜索按键事件监听处理
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    Toast.makeText(activity,arg0.getText().toString()+"",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        LinearLayout backLin = (LinearLayout) findViewById(R.id.search_detail_goback_linlay);
        backLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.search_detail_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        final TypeInfoRecyclerViewAdapter simpAdapter = new TypeInfoRecyclerViewAdapter(activity,getTypeInfoData());
//        recyclerView.setAdapter(simpAdapter);
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    //热词搜索成功
                    case 1000:
                        List<GoodsInfoEntity> goodsDetaiLIst = (List<GoodsInfoEntity>) msg.obj;
                        if(goodsDetaiLIst == null || goodsDetaiLIst.size()<=0) {
                            break;
                        }
                        if(goodsDetaiLIst.size() >9){
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
                        }else{
                            mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);

                        }
                        TypeInfoRecyclerViewAdapter   simpAdapter = new TypeInfoRecyclerViewAdapter(activity,goodsDetaiLIst);
                        recyclerView.setAdapter(simpAdapter);
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
                    type.setGoodsName("鸡肉");
                    break;
                case 1:
                    type.setGoodsName("鲜蔬菜");
                    break;
                case 2:
                    type.setGoodsName("豆芽");
                    break;
                case 3:
                    type.setGoodsName("牛肉");
                    break;
                case 4:
                    type.setGoodsName("鸭肉");
                    break;
                default:
                    type.setGoodsName("西瓜");

            }
            typeList.add(type);

        }
        return typeList;
    }
    public void GetGoodsTypeInfoHttp(String goodsName){
        if(recyclerView != null)
            recyclerView.setAdapter(null);
        HttpParams httpParams = new HttpParams();
        httpParams.put("goodsName",goodsName);
//        httpParams.put("categoryCode",cid);
        HttpUtils.getInstance(activity).requestPost(true, AppClient.GOODS_LIST, httpParams, new HttpUtils.requestCallBack() {
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
