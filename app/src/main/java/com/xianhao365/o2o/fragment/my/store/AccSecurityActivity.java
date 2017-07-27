package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.os.Bundle;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;

public class AccSecurityActivity extends BaseActivity {
     private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_security);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");
    }
}
