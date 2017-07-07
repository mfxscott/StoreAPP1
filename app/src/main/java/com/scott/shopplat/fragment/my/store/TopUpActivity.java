package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

/**
 * 账号充值方式选择界面
 * @author mfx
 * @time  2017/7/6 17:42
 */
public class TopUpActivity extends BaseActivity implements OnClickListener {
     private Activity activity;
    private RelativeLayout topupWxpayRel;
    private ImageView topupWxCheckImg;
    private RelativeLayout topupZfbpayRel;
    private ImageView topupZfbPayIv;
    private RelativeLayout topupYhkpayRel;
    private ImageView topupYhkPayIv;
    private  int  payTag = 3;//1 微信 2 支付宝 3 银行卡选择支付方式标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("充值");

        topupWxpayRel = (RelativeLayout) findViewById(R.id.topup_wxpay_rel);
        topupWxCheckImg = (ImageView) findViewById(R.id.topup_wx_check_img);
        topupZfbpayRel = (RelativeLayout) findViewById(R.id.topup_zfbpay_rel);
        topupZfbPayIv = (ImageView) findViewById(R.id.topup_zfb_check_img);
        topupYhkpayRel = (RelativeLayout) findViewById(R.id.topup_yhkpay_rel);
        topupYhkPayIv = (ImageView) findViewById(R.id.topup_yhk_check_img);
        Button topupBtn = (Button) findViewById(R.id.topup_btn);
        topupBtn.setOnClickListener(this);
        topupWxpayRel.setOnClickListener(this);
        topupZfbpayRel.setOnClickListener(this);
        topupYhkpayRel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topup_wxpay_rel:
                topupWxCheckImg.setVisibility(View.VISIBLE);
                topupZfbPayIv.setVisibility(View.GONE);
                topupYhkPayIv.setVisibility(View.GONE);
                payTag = 1;
                break;
            case R.id.topup_zfbpay_rel:
                topupWxCheckImg.setVisibility(View.GONE);
                topupZfbPayIv.setVisibility(View.VISIBLE);
                topupYhkPayIv.setVisibility(View.GONE);
                payTag = 2;
                break;
            case R.id.topup_yhkpay_rel:
                topupWxCheckImg.setVisibility(View.GONE);
                topupZfbPayIv.setVisibility(View.GONE);
                topupYhkPayIv.setVisibility(View.VISIBLE);
                payTag = 3;
                break;
            case R.id.topup_btn:
                Intent intent = new Intent(activity,BankCardTopUpActivity.class);
                startActivity(intent);
                break;

        }

    }
}
