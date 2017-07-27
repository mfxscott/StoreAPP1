package com.xianhao365.o2o.fragment.my.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;

public class ExtractDetailActivity extends BaseActivity implements View.OnClickListener {
    private Activity activity;
    private TextView extractMoneyCardTv;
    private EditText extractPaySumEdit;
    private TextView extractNowMoneyTv;
    private TextView extractAllMoneyTv;
    private Button extractBtn;
    private LinearLayout extractNoCardLiny;
    private Button extractAddBankCardBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_detail);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("提现");
        extractMoneyCardTv = (TextView) findViewById(R.id.extract_money_card_tv);
        extractPaySumEdit = (EditText) findViewById(R.id.extract_pay_sum_edit);
        extractNowMoneyTv = (TextView) findViewById(R.id.extract_now_money_tv);
        extractAllMoneyTv = (TextView) findViewById(R.id.extract_all_money_tv);
        extractBtn = (Button) findViewById(R.id.extract_btn);
        extractNoCardLiny = (LinearLayout) findViewById(R.id.extract_no_card_liny);
        extractAddBankCardBtn = (Button) findViewById(R.id.extract_add_bank_card_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //提现按钮
            case R.id.extract_btn:
                break;
            //提现添加银行卡
            case R.id.extract_add_bank_card_btn:
                Intent intent = new Intent(activity,ExtractAddBankCardActivity.class);
                startActivity(intent);
                break;
            //提现全部金额
            case R.id.extract_all_money_tv:
                break;
        }
    }
}
