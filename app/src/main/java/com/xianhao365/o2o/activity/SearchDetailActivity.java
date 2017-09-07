package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.TypeInfoRecyclerViewAdapter;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.view.NXHooldeView;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import static com.xianhao365.o2o.fragment.MainFragmentActivity.badge1;

public class SearchDetailActivity extends AppCompatActivity {
private Activity activity;
    private RecyclerView recyclerView;
    private String searchValueStr;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        activity = this;
        searchValueStr = this.getIntent().getStringExtra("searchValue");
        initView();
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
        final TypeInfoRecyclerViewAdapter simpAdapter = new TypeInfoRecyclerViewAdapter(activity,getTypeInfoData(),new FoodActionCallback(){

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
}
