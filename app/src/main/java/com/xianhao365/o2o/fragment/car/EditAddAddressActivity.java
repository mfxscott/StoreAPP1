package com.xianhao365.o2o.fragment.car;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.entity.AddressInfoEntity;

public class EditAddAddressActivity extends BaseActivity implements View.OnClickListener{
    private Button button;
    private AddressInfoEntity addressInfo;
    private EditText  nameEdt,phoneEdt,infoEdt;
    private String tag;//0 新增 1 编辑判断是编辑还是新增进入
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_address);
         tag = (String) this.getIntent().getExtras().get("tag");
        if(tag.equals("1")) {
            addressInfo = (AddressInfoEntity) this.getIntent().getExtras().get("address");
        }
        initView();
    }
    private  void initView(){
        registerBack();

        button = (Button) findViewById(R.id.edit_address_save_btn);
        button.setOnClickListener(this);
        nameEdt = (EditText) findViewById(R.id.edit_address_name_edt);
        phoneEdt = (EditText) findViewById(R.id.edit_address_phone_edt);
        infoEdt = (EditText) findViewById(R.id.edit_address_info_edt);
        if(tag.equals("1")) {
            nameEdt.setText(addressInfo.getName());
            phoneEdt.setText(addressInfo.getPhone());
            infoEdt.setText(addressInfo.getStreet());
            setTitle("编辑地址");
        }else{
            setTitle("新增地址");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_address_save_btn:
                finish();
                break;
        }
    }
}
