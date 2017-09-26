package com.xianhao365.o2o.fragment.my.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.SXUtils;

/**
 * 账号充值,商品支付  支付方式选择界面
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
    private String TagStr,paySum;//标识充值还是支付，金额
    private EditText sumEdit;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        TagStr = this.getIntent().getStringExtra("payTag");
        paySum = this.getIntent().getStringExtra("paySum");
        orderNo = this.getIntent().getStringExtra("orderNo");
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        sumEdit = (EditText) findViewById(R.id.top_pay_sum_edit);
        TextView  hintTv = (TextView) findViewById(R.id.top_hint_tv);
        if(TagStr.equals("0")){
            setTitle("充值");
            hintTv.setText("充值金额(元)");
        }else{
            setTitle("支付");
            hintTv.setText("金额(元)");
            sumEdit.setText(paySum+"");
            sumEdit.setEnabled(false);
        }
         if(!TextUtils.isEmpty(orderNo)){
             SXUtils.getInstance(activity).ToastCenter("订单号为"+orderNo);
         }
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
                switch (payTag){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent intent = new Intent(activity,BankCardTopUpActivity.class);
                        startActivity(intent);
                        break;
                }

                break;

        }

    }
}
