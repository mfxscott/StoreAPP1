package com.scott.shopplat.fragment.my.store;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.view.GlideRoundTransform;

/**
 * 摊主或者个人登录进入我的界面
 * 通过区分标识显示不同界面
 * @author mfx
 * @time  2017/7/5 16:22
 */
public class StoreMyFragment extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout myStoreMyorderRel;
    private LinearLayout myStoreWaitpayLin;
    private LinearLayout myStoreWaitsendLin;
    private LinearLayout myStoreWaitgetLin;
    private LinearLayout myStoreMykeyLin;
    private LinearLayout myStoreCybillLin;
    private LinearLayout myStoreReturnLin;
    private LinearLayout myStoreNewLin;
    private LinearLayout myStoreMyfpLin;
    private RelativeLayout myStoreHelpCenterRel;
    private LinearLayout myStoreQuesionLin;
    private LinearLayout myStoreFwcenterLin;
    private LinearLayout myStoreZxserviceLin;
    private LinearLayout myStoreKffwLin;
    private Activity activity;
    private LinearLayout storeLin; //店铺账号类型显示
    private LinearLayout perLin; //个人账号类型显示
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.BLACK);
//        }
        view = inflater.inflate(R.layout.fragment_store_my, null);
        initView();
        return view;
    }
    private void initView(){
        ImageView headimg = (ImageView) view.findViewById(R.id.my_head_img);
        Glide.with(activity).load(AppClient.headImg).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.default_head).transform(new GlideRoundTransform(activity, 60)).into(headimg);
//        Glide.with(activity).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(activity, 10)).into(headimg);

//        Glide.with(activity)
//                .load("")
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .bitmapTransform(new RoundedCornersTransformation(activity)) //使用圆形变换，还可以使用其他的变换
//                .into(headimg);
//
//
//        Glide.with(this).load("").bitmapTransform(new RoundedCornersTransformation(this, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(image5);

        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.my_per_wallet);
        rel.setOnClickListener(this);

        storeLin = (LinearLayout) view.findViewById(R.id.my_store_dis_lin);
        perLin = (LinearLayout) view.findViewById(R.id.my_per_dis_lin);
        if(AppClient.TAG .equals("64")){
            perLin.setVisibility(View.VISIBLE);
            storeLin.setVisibility(View.GONE);
        }else{
            perLin.setVisibility(View.GONE);
            storeLin.setVisibility(View.VISIBLE);
        }
        TextView accTv = (TextView) view.findViewById(R.id.my_acc_mamage_tv);
        accTv.setOnClickListener(this);
        ImageView messageIv = (ImageView) view.findViewById(R.id.per_my_message_iv);
        messageIv.setOnClickListener(this);

        myStoreMyorderRel = (RelativeLayout) view.findViewById(R.id.my_store_myorder_rel);
        myStoreWaitpayLin = (LinearLayout) view.findViewById(R.id.my_store_waitpay_lin);
        myStoreWaitsendLin = (LinearLayout) view.findViewById(R.id.my_store_waitsend_lin);
        myStoreWaitgetLin = (LinearLayout) view.findViewById(R.id.my_store_waitget_lin);
        myStoreMykeyLin = (LinearLayout) view.findViewById(R.id.my_store_mykey_lin);
        myStoreCybillLin = (LinearLayout) view.findViewById(R.id.my_store_cybill_lin);
        myStoreReturnLin = (LinearLayout) view.findViewById(R.id.my_store_return_lin);
        myStoreNewLin = (LinearLayout) view.findViewById(R.id.my_store_new_lin);
        myStoreMyfpLin = (LinearLayout) view.findViewById(R.id.my_store_myfp_lin);
        myStoreHelpCenterRel = (RelativeLayout) view.findViewById(R.id.my_store_help_center_rel);
        myStoreQuesionLin = (LinearLayout) view.findViewById(R.id.my_store_quesion_lin);
        myStoreFwcenterLin = (LinearLayout) view.findViewById(R.id.my_store_fwcenter_lin);
        myStoreZxserviceLin = (LinearLayout) view.findViewById(R.id.my_store_zxservice_lin);
        myStoreKffwLin = (LinearLayout) view.findViewById(R.id.my_store_kffw_lin);
        myStoreMyorderRel.setOnClickListener(this);
        myStoreWaitpayLin.setOnClickListener(this);
        myStoreWaitsendLin.setOnClickListener(this);
        myStoreWaitgetLin.setOnClickListener(this);
        myStoreMykeyLin.setOnClickListener(this);
        myStoreCybillLin.setOnClickListener(this);
        myStoreReturnLin.setOnClickListener(this);
        myStoreNewLin.setOnClickListener(this);
        myStoreMyfpLin.setOnClickListener(this);
        myStoreHelpCenterRel.setOnClickListener(this);
        myStoreQuesionLin.setOnClickListener(this);
        myStoreFwcenterLin.setOnClickListener(this);
        myStoreZxserviceLin.setOnClickListener(this);
        myStoreKffwLin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_per_wallet:
                Intent wall = new Intent(activity,MyWalletActivity.class);
                startActivity(wall);
                break;
            case R.id.my_acc_mamage_tv:
                Intent intent = new Intent(activity,AccManageActivity.class);
                startActivity(intent);
                break;
            case R.id.per_my_message_iv:
                Intent msg = new Intent(activity,MessageActivity.class);
                startActivity(msg);
                break;
        }
    }
}
