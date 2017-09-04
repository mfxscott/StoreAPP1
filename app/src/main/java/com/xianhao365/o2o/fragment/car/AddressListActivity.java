package com.xianhao365.o2o.fragment.car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.adapter.AddressRecyclerViewAdapter;
import com.xianhao365.o2o.entity.AddressInfoEntity;
import com.xianhao365.o2o.utils.Logs;

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
        registerBack();
        setTitle("收货信息");
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

//        AddressInfoEntity   address = new AddressInfoEntity();
//        address.setId("0");
//        address.setCity("广东省深圳市福田区");
//        address.setStreet("冰河大道2014");
//        address.setName("鲜好");
//        address.setPhone("123456789");
//        addressList.add(address);
//        address.setIsDefault("1");

        AddressInfoEntity   addresss = new AddressInfoEntity();
        addresss.setId("1");
        addresss.setCity("广东省深圳市罗湖区");
        addresss.setStreet("冰河大道");
        addresss.setName("刘");
        addresss.setPhone("123456789");
        addresss.setIsDefault("1");

        AddressInfoEntity   addressss = new AddressInfoEntity();
        addressss.setId("2");
        addressss.setCity("广东省深圳市福田区");
        addressss.setStreet("西乡2014");
        addressss.setName("马");
        addressss.setPhone("18682136973");
        addressss.setIsDefault("0");
//        addressList.add(address);
        addressList.add(addresss);
        addressList.add(addressss);
        return addressList;
    }
}
