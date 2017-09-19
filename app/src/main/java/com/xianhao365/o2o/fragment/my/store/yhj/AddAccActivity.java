package com.xianhao365.o2o.fragment.my.store.yhj;

import android.os.Bundle;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;

public class AddAccActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc);
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账号管理");
    }
}
