package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener{
    private Button walletTopupBtn;
    private RelativeLayout walletYhjRel;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("我的钱包");

        walletTopupBtn = (Button) findViewById(R.id.wallet_topup_btn);
        walletYhjRel = (RelativeLayout) findViewById(R.id.wallet_yhj_rel);
        walletTopupBtn.setOnClickListener(this);
        walletYhjRel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wallet_topup_btn:
                Intent intent = new Intent(activity,TopUpActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_yhj_rel:
                break;
        }

    }
}
