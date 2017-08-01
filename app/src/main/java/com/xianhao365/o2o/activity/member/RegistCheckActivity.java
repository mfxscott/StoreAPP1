package com.xianhao365.o2o.activity.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xianhao365.o2o.R;

/**
 * 注册前选择注册角色  老板OR个人
 * @author mfx
 * @time  2017/7/6 10:03
 */
public class RegistCheckActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_check);
        Button sell = (Button) findViewById(R.id.regist_check_sell_btn);
        Button pay = (Button) findViewById(R.id.regist_check_pay_btn);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sellregist = new Intent(RegistCheckActivity.this, Regis1Activity.class);
                sellregist.putExtra("registRole","32");
                startActivity(sellregist);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regist = new Intent(RegistCheckActivity.this, Regis1Activity.class);
                regist.putExtra("registRole","64");
                startActivity(regist);
            }
        });
    }
}
