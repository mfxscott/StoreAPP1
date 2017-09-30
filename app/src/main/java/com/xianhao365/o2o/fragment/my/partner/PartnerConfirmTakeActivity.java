package com.xianhao365.o2o.fragment.my.partner;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.PartnerOrderScanRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerConfirmTakeActivity extends BaseActivity {
   private String result;//扫码获取到的订单号R.id.
    @BindView(R.id.partner_orderno_edt)
    EditText onderNoEdt;
    @BindView(R.id.partner_scan_recycler)
    RecyclerView recyclerView;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_confirm_take);
        activity = this;
        ButterKnife.bind(this);
        result = this.getIntent().getStringExtra("result");
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("扫码收货");
        onderNoEdt.setText(result+"");
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new PartnerOrderScanRecyclerViewAdapter(activity,null,1));
    }

}
