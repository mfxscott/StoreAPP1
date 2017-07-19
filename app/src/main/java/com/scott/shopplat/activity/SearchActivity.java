package com.scott.shopplat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scott.shopplat.R;
import com.scott.shopplat.utils.view.FlowLayout;

import java.util.Random;

public class SearchActivity extends AppCompatActivity {
//    private GridView gridView;
    private Activity activity;
//    private SearchHotAdapter searchHotAdapter;
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
    }

    private void initView() {
        LinearLayout backLin = (LinearLayout) findViewById(R.id.search_goback_linlay);
        backLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow_layout);

        Random random = new Random();

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
                    Toast.makeText(activity, view.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

//            // 设置彩色背景
//            GradientDrawable normalDrawable = new GradientDrawable();
//            normalDrawable.setShape(GradientDrawable.RECTANGLE);
//            int a = 255;
//            int r = 50 + random.nextInt(150);
//            int g = 50 + random.nextInt(150);
//            int b = 50 + random.nextInt(150);
//            normalDrawable.setColor(Color.argb(a, r, g, b));
//
//            // 设置按下的灰色背景
//            GradientDrawable pressedDrawable = new GradientDrawable();
//            pressedDrawable.setShape(GradientDrawable.RECTANGLE);
//            pressedDrawable.setColor(Color.GRAY);
//
//            // 背景选择器
//            StateListDrawable stateDrawable = new StateListDrawable();
//            stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
//            stateDrawable.addState(new int[]{}, normalDrawable);

            // 设置背景选择器到TextView上
//            view.setBackground(stateDrawable);
            view.setBackgroundResource(R.drawable.search_hot_selector);

            flowLayout.addView(view);
        }

    }
}
