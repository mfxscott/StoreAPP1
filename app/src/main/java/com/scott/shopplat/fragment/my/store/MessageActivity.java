package com.scott.shopplat.fragment.my.store;

import android.os.Bundle;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

/**
 * 用户消息管理
 * @author mfx
 * @time  2017/7/6 16:42
 */
public class MessageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        registerBack();
        setTitle("消息中心");
    }
}
