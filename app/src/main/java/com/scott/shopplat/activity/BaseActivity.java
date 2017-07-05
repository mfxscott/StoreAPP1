package com.scott.shopplat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.utils.httpClient.OKManager;

/**
 * Created by mfx-t224 on 2017/6/29.
 */

public class BaseActivity extends Activity{
    private MyApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) getApplicationContext();
    }
    /**
     * 设置点击左上角的返回事件.默认是finish界面
     */
    protected void registerBack() {
        LinearLayout   allTitleGobackLinlay = (LinearLayout) findViewById(R.id.all_title_goback_linlay);
        allTitleGobackLinlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.this.finish();
            }
        });
    }
    protected  void setTitle(String title){
        TextView allTitleName = (TextView) findViewById(R.id.all_title_name);
        allTitleName.setText(title+"");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OKManager.cancelTag(this);//取消以Activity.this作为tag的请求
    }
//    }
}
