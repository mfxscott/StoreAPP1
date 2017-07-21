package com.scott.shopplat.fragment.car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.fragment.my.store.TopUpActivity;
import com.scott.shopplat.fragment.my.store.yhj.YHJActivity;

/**
 * 商品预下单成功去支付界面
 */
public class GoPayActivity extends BaseActivity implements View.OnClickListener{
        private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pay);
        activity = this;
        initView();
    }
    private void initView(){
        LinearLayout  goback = (LinearLayout) findViewById(R.id.gopay_title_goback_linlay);
        goback.setOnClickListener(this);
        RelativeLayout reladdress = (RelativeLayout) findViewById(R.id.gopay_check_address_rel);
        reladdress.setOnClickListener(this);
        RelativeLayout useYhj = (RelativeLayout) findViewById(R.id.gopay_use_yhj_rel);
        useYhj.setOnClickListener(this);
        TextView paytv = (TextView) findViewById(R.id.gopay_btn);
        paytv.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gopay_title_goback_linlay:
                finish();
                break;
            case R.id.gopay_check_address_rel:
                Intent address = new Intent(activity, AddressListActivity.class);
                startActivity(address);
                break;
            case R.id.gopay_use_yhj_rel:
                Intent intent = new Intent(activity, YHJActivity.class);
                intent.putExtra("yhjTag","1");
                startActivity(intent);
                break;
            case R.id.gopay_btn:
                Intent pay = new Intent(activity, TopUpActivity.class);
                pay.putExtra("payTag","1");
                pay.putExtra("paySum","1000");
                startActivity(pay);
                break;
        }

    }
}
