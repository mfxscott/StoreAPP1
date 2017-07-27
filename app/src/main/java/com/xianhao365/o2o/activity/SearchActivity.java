package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.view.FlowLayout;
import com.xianhao365.o2o.utils.view.MyGridView;
import com.xianhao365.o2o.utils.view.MyViewGroup;

public class SearchActivity extends AppCompatActivity {
    //    private GridView gridView;
    private Activity activity;
    //    private SearchHotAdapter searchHotAdapter;
    private EditText  searchEdt;
    private MyGridView search_gridv;
    private MyViewGroup search_hist_lin;

    /**
     * 显示的文字
     */
    private String[] mDatas = new String[]{
            "油麦菜",
            "空心菜",
            "绿色纯大米",
            "有机胡萝卜",
            "粮油",
            "绿色纯天然绿豆",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        initView();
        searchHistHot(getSearchValue());
    }
    private void initView() {
//        search_gridv = (MyGridView) findViewById(R.id.search_hist_gridv);

        search_hist_lin = (MyViewGroup) findViewById(R.id.search_hist_lin);
        searchEdt = (EditText) findViewById(R.id.search_editv);
        searchEdt.addTextChangedListener(new EditChangedListener());
        //点击按下键盘搜索按键事件监听处理
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    Toast.makeText(activity,arg0.getText().toString()+"",Toast.LENGTH_SHORT).show();
                    // search pressed and perform your functionality.
                    String  searchValue = arg0.getText().toString().trim();
                    if(TextUtils.isEmpty(searchValue)){
                        SXUtils.getInstance(activity).ToastCenter("请输入要搜索的商品名称");
                    }else {
                        setSearchValue(searchValue);
                        Intent intent = new Intent(activity, SearchDetailActivity.class);
                        intent.putExtra("searchValue", arg0.getText().toString().trim() + "");
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
        TextView removeTv = (TextView) findViewById(R.id.search_remove_hist_tv);
        removeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(activity).removeSharePreferences("searchValue");
                search_hist_lin.removeAllViews();
                search_hist_lin.setVisibility(View.GONE);
            }
        });
        LinearLayout backLin = (LinearLayout) findViewById(R.id.search_goback_linlay);
        backLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchHot();
//        gridView = (GridView) findViewById(R.id.search_hot_mygridv);
//        searchHotAdapter= new SearchHotAdapter(activity,getTypeInfoData());
//        gridView.setAdapter(searchHotAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                SXUtils.getInstance(activity).ToastCenter("=="+position);
////                searchHotAdapter.changeSelected(position);//刷新
//            }
//        });
    }
    /**
     * 热门搜索词 按钮样式
     */
    private void searchHot() {
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        // 循环添加TextView到容器
        for (int i = 0; i < mDatas.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setTextColor(getResources().getColor(R.color.col_333));
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(14);
            // 设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchEdt.setText(view.getText().toString().trim());
                    setSearchValue(view.getText().toString().trim());
                    Intent intent = new Intent(activity, SearchDetailActivity.class);
                    intent.putExtra("searchValue", view.getText().toString().trim() + "");
                    startActivity(intent);
//                    Toast.makeText(activity, view.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            view.setBackgroundResource(R.drawable.search_hot_selector);
            flowLayout.addView(view);
        }
    }
    /**
     * 热门搜索词 按钮样式
     */
    private void searchHistHot(String[] searchList) {
        if(searchList == null || searchList.length <=0){
            return;
        }
        // 循环添加TextView到容器
        for (int i = 0; i < searchList.length; i++) {
            addLinView(searchList[i]);
        }
    }
    private void  addLinView(String text){
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(10, 10, 10, 10);
        final TextView view = new TextView(this);
        view.setText(text);
        view.setTextColor(getResources().getColor(R.color.col_333));
        view.setPadding(5, 5, 5, 5);
        view.setGravity(Gravity.CENTER);
        view.setTextSize(14);
//        view.setLayoutParams(params);
        // 设置点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdt.setText(view.getText().toString().trim());
                Intent intent = new Intent(activity, SearchDetailActivity.class);
                intent.putExtra("searchValue", view.getText().toString().trim() + "");
                startActivity(intent);
            }
        });
        view.setBackgroundResource(R.drawable.search_hot_selector);
        search_hist_lin.addView(view);
        search_hist_lin.setHorinzontalMargin(10);
        search_hist_lin.setVerticalMargin(10);
        search_hist_lin.setVisibility(View.VISIBLE);

    }
    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(s.toString())){
            }else{
            }
        }
    };
    private void setSearchValue(String value){
        String[] searchList = getSearchValue();
        if(searchList != null ){
            for(int i=0;i<searchList.length;i++){
                Logs.i(value+"==========="+searchList[i]);
                if(value.equals(searchList[i])){
                    return ;
                }
            }
        }

        if(searchList != null){
            String searchstr = SXUtils.getInstance(activity).getSharePreferences("searchValue");
            SXUtils.getInstance(activity).setSharePreferences("searchValue", searchstr+"&" + value);
        }
        else{
            SXUtils.getInstance(activity).setSharePreferences("searchValue", value);
        }
        addLinView(value);
    }
    /**
     * 得到搜索历史记录  通过| 进行截取
     * @return
     */
    private String[] getSearchValue(){
        String[] searchlist=null;
        String  searchStr =  SXUtils.getInstance(activity).getSharePreferences("searchValue");
        Logs.i("历史搜索词汇======",searchStr);
        if(TextUtils.isEmpty(searchStr) || searchStr.equals("0")){
            return  null;
        }else if(searchStr.indexOf("&") >0){
            searchlist= searchStr.split("&");
        }else{
            searchlist= new String[]{searchStr};
        }

        return searchlist;
    }
}
