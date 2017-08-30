package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.utils.SXUtils;

public class GoodsFeedbackActivity extends BaseActivity implements View.OnClickListener{
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_feedback);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("快速反馈");
        Button  btn = (Button) findViewById(R.id.feedback_submit_btn);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback_submit_btn:
                SXUtils.getInstance(activity).ToastCenter("反馈成功");
                finish();
                break;
        }
    }
}