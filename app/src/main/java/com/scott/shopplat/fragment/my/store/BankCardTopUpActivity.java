package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.utils.SXUtils;

public class BankCardTopUpActivity extends BaseActivity {
            private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_top_up);
        activity = this;
        initView();
    }
    private void  initView(){
        registerBack();
        setTitle("银行卡支付");
        Button btn = (Button) findViewById(R.id.bank_info_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SXUtils.getInstance(activity).ToastCenter("支付成功");
                finish();
            }
        });
    }
}
