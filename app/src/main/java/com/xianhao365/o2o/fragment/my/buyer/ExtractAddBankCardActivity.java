package com.xianhao365.o2o.fragment.my.buyer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.SXUtils;

public class ExtractAddBankCardActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_add_bank_card);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("添加银行卡");

        Button btn = (Button) findViewById(R.id.extract_add_card_detail_btn);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.extract_add_card_detail_btn:
                SXUtils.getInstance(activity).ToastCenter("添加成功");
                break;
        }
    }
}
