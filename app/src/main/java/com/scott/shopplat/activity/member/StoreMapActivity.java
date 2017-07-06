package com.scott.shopplat.activity.member;

import android.os.Bundle;
import android.view.View;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

public class StoreMapActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);

        init();
    }
    /**
     * 初始化AMap对象
     */
    private void init() {
        registerBack();
        setTitle(getString(R.string.check_store_str));
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
