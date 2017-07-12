package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.view.GlideRoundTransform;

public class AccInfoActivity extends BaseActivity {
      private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_info);
        activity = this;
        initView();
    }
    private void initView(){
        registerBack();
        setTitle("账户信息");

        ImageView headimg = (ImageView) findViewById(R.id.acc_info_headimg);
        Glide.with(activity).load(AppClient.headImg).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity, 60)).into(headimg);
    }

}
