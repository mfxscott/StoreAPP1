package com.scott.shopplat.fragment.my.store.order;

import android.os.Bundle;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        registerBack();
        setTitle("订单详情");
    }
}
