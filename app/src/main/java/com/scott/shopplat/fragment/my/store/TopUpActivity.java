package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;

/**
 * 账号充值
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
    private Button topupBtn;



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
        topupBtn = (Button) findViewById(R.id.topup_btn);
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
                break;
            case R.id.topup_zfbpay_rel:
                topupWxCheckImg.setVisibility(View.GONE);
                topupZfbPayIv.setVisibility(View.VISIBLE);
                topupYhkPayIv.setVisibility(View.GONE);
                break;
            case R.id.topup_yhkpay_rel:
                topupWxCheckImg.setVisibility(View.GONE);
                topupZfbPayIv.setVisibility(View.GONE);
                topupYhkPayIv.setVisibility(View.VISIBLE);
                break;

        }

    }
}
