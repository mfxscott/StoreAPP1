package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.adapter.BankCardListAdapter;
import com.scott.shopplat.adapter.SRDetailListAdapter;
import com.scott.shopplat.fragment.my.store.yhj.YHJActivity;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWalletActivity extends BaseActivity implements View.OnClickListener{
    private Button walletTopupBtn;
    private RelativeLayout walletYhjRel;
    private Activity activity;
    private MyGridView gridView;
    private MyGridView detailGridView;//收入明细
    private Handler hand;
    private LinearLayout walletNoUseLin;
    private LinearLayout walletUseLin;
    private LinearLayout walletUsedLin;
    private RelativeLayout walletMyBankRel;
    private RelativeLayout walletAddBankRel;
    private RelativeLayout walletSrDetailRel;
    private LinearLayout bankListLay;
    private ImageView myBankArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        activity = this;

        initView();

    }
    private void initView(){
        bankListLay = (LinearLayout) findViewById(R.id.wallet_banklist_lin);
        myBankArrow = (ImageView) findViewById(R.id.wallet_mybank_arrow_iv);

        walletNoUseLin = (LinearLayout) findViewById(R.id.wallet_no_use_lin);
        walletUseLin = (LinearLayout) findViewById(R.id.wallet_use_lin);
        walletUsedLin = (LinearLayout) findViewById(R.id.wallet_used_lin);
        walletMyBankRel = (RelativeLayout) findViewById(R.id.wallet_my_bank_rel);
        walletAddBankRel = (RelativeLayout) findViewById(R.id.wallet_add_bank_rel);
        walletSrDetailRel = (RelativeLayout) findViewById(R.id.wallet_sr_detail_rel);
        walletNoUseLin.setOnClickListener(this);
        walletUseLin.setOnClickListener(this);
        walletUsedLin.setOnClickListener(this);
        walletMyBankRel.setOnClickListener(this);
        walletAddBankRel.setOnClickListener(this);
        walletSrDetailRel.setOnClickListener(this);

        registerBack();
        setTitle("我的钱包");
        gridView = (MyGridView) findViewById(R.id.wallet_bank_gridv);
        gridView.setAdapter(new BankCardListAdapter(activity,getBankData()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SXUtils.getInstance(activity).ToastCenter("=="+position);
            }
        });
        detailGridView = (MyGridView) findViewById(R.id.wallet_srdetail_gridv);
        detailGridView.setAdapter(new SRDetailListAdapter(activity,getSRDetailData()));
        detailGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SXUtils.getInstance(activity).ToastCenter("==-=="+position);
            }
        });

        walletTopupBtn = (Button) findViewById(R.id.wallet_topup_btn);
        walletYhjRel = (RelativeLayout) findViewById(R.id.wallet_yhj_rel);
        walletTopupBtn.setOnClickListener(this);
        walletYhjRel.setOnClickListener(this);
        LinearLayout banklay = (LinearLayout) findViewById(R.id.wallet_bank_card_lin);
        if(AppClient.TAG.equals("64")){
            banklay.setVisibility(View.GONE);
        }else if(AppClient.TAG.equals("32")){
            banklay.setVisibility(View.VISIBLE);
        }
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        SXUtils.getInstance(activity).ToastCenter("登录成功");
                        finish();
                        break;
                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }

    /**
     * 封装模拟银行卡数据
     * @return
     */
    private List<Map<String,String>> getBankData(){
        List<Map<String,String>> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            Map<String,String>  map = new HashMap<>();
            map.put("cardNum","62262654256"+i);
            map.put("cardName","招商银行"+i);
            map.put("cardId","cardId===="+i);
            list.add(map);
        }
        return list;
    }
    /**
     * 封装模拟银行卡数据
     * @return
     */
    private List<Map<String,String>> getSRDetailData(){
        List<Map<String,String>> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            Map<String,String>  map = new HashMap<>();
            map.put("name","鲜好惠"+i);
            map.put("orderId","20170707"+i);
            map.put("orderTime",SXUtils.getInstance(activity).GetNowDateTime());
            map.put("orderPrice","+10"+i);
            map.put("orderState","未到账");
            list.add(map);
        }
        return list;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wallet_topup_btn:
                Intent intent = new Intent(activity,TopUpActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_yhj_rel:
                Intent yhj = new Intent(activity,YHJActivity.class);
                startActivity(yhj);
                break;
            case R.id.wallet_my_bank_rel:
                if(bankListLay.getVisibility() == View.VISIBLE){
                    bankListLay.setVisibility(View.GONE);
                    myBankArrow.setBackgroundResource(R.mipmap.arrow_right);
                }else{
                    bankListLay.setVisibility(View.VISIBLE);
                    myBankArrow.setBackgroundResource(R.mipmap.arrow_down);
                }
                break;
        }

    }
}
