package com.scott.shopplat.fragment.car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.adapter.AddressRecyclerViewAdapter;
import com.scott.shopplat.entity.AddressInfoEntity;
import com.scott.shopplat.utils.Logs;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends BaseActivity {
    private RecyclerView  addressRecy;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        activity = this;
        initView();

    }
    private void initView(){
        addressRecy = (RecyclerView) findViewById(R.id.address_list_recyclerv);

        addressRecy.setLayoutManager(new LinearLayoutManager(addressRecy.getContext()));
        addressRecy.setItemAnimator(new DefaultItemAnimator());
        List<AddressInfoEntity> listd = getAddress();
        Logs.i(listd.size()+"=======");
        final AddressRecyclerViewAdapter simpAdapter = new AddressRecyclerViewAdapter(activity,getAddress());
        addressRecy.setAdapter(simpAdapter);
        Button addBtn = (Button) findViewById(R.id.address_list_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditAddAddressActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("tag","0");//0是新增  1是编辑
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
    }
    private List<AddressInfoEntity> getAddress(){
        List<AddressInfoEntity>  addressList = new ArrayList<>();
        AddressInfoEntity  address;
        for(int i =0;i<3;i++){
            address = new AddressInfoEntity();
            if(i ==0) {
                address.setId("0");
                address.setCity("广东省深圳市福田区");
                address.setStreet("冰河大道2014");
                address.setName("鲜好");
                address.setPhone("123456789");
                addressList.add(address);
                address.setIsDefault("1");
            }else if(i == 1) {
                address.setId("1");
                address.setCity("广东省深圳市罗湖区");
                address.setStreet("冰河大道");
                address.setName("刘");
                address.setPhone("123456789");
                addressList.add(address);
                address.setIsDefault("1");
            }else if(i==2){
                address.setId("2");
                address.setCity("广东省深圳市福田区");
                address.setStreet("西乡2014");
                address.setName("马");
                address.setPhone("18682136973");
                address.setIsDefault("0");
            }
            addressList.add(address);
        }
        return addressList;
    }
}
