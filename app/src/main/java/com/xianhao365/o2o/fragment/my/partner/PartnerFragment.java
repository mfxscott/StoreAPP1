package com.xianhao365.o2o.fragment.my.partner;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.fragment.my.store.AccManageActivity;
import com.xianhao365.o2o.fragment.my.store.MyWalletActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;
import com.xianhao365.o2o.utils.zxing.BaseQRScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 鲜好合伙人,联创中心
 */
public class PartnerFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Unbinder unbinder;
    private Activity activity;
    @BindView(R.id.partner_wallet_rely)
    RelativeLayout walletRely;
    @BindView(R.id.partner_acc_mamage_tv)
    TextView  accManageTv;
    @BindView(R.id.partner_center_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.partner_head_img)
    ImageView  headImg;
    @BindView(R.id.partner_name_tv)
    TextView name;
    @BindView(R.id.partner_scan_tv)
    TextView scanTv;
    @BindView(R.id.partner_orderlsit_rely)
    RelativeLayout  orderRel;
    @BindView(R.id.partner_wait_get_lin)
    LinearLayout  waitGetLin;
    @BindView(R.id.partner_wait_send_lin)
    LinearLayout  waitSendLin;
    @BindView(R.id.partner_wait_confirm_lin)
    LinearLayout  waitConfirmLin;
    @BindView(R.id.partner_scan_lin)
    LinearLayout  scanLin;
    @BindView(R.id.partner_buyer_topup_btn)
    TextView partnerWalletTv;
    private Handler hand;
    private UserInfoEntity userinfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_partner, null);
        activity = getActivity();
        unbinder =   ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }
    private void initData() {
        if (SXUtils.getInstance(activity).IsLogin()) {
            SXUtils.getInstance(activity).getUserInfoHttp(hand);
            SXUtils.getInstance(activity).getUserNumberHttp(hand);
        }
    }
    /**
     * 用户信息
     * @param userInfo
     */
    private void initUserInfo(UserInfoEntity userInfo){
        name.setText(userInfo.getUsername());
        Glide.with(activity).load((String)userInfo.getIcon()).placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head).transform( new GlideRoundTransform(activity)).into(headImg);
        name.setText(userInfo.getUsername());
    }
    private void initView(){
        orderRel.setOnClickListener(this);
        scanTv.setOnClickListener(this);
        waitGetLin.setOnClickListener(this);
        waitSendLin.setOnClickListener(this);
        waitConfirmLin.setOnClickListener(this);
        partnerWalletTv.setOnClickListener(this);
        if(AppClient.USERROLETAG.equals("8")){
            scanLin.setVisibility(View.GONE);
        }else{
            scanLin.setVisibility(View.VISIBLE);
        }
//        swipeRefreshLayout.setColorSchemeResources( R.color.qblue, R.color.red, R.color.btn_gray);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        userinfo = (UserInfoEntity) msg.obj;
                        initUserInfo(userinfo);
                        break;
                    case 1001:
                        break;
                    case AppClient.ERRORCODE:
                        String str = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(str+"");
                }
                if(swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
                return true;
            }
        });
    }
    @OnClick({R.id.partner_wallet_rely})
    public void RelOnclick(RelativeLayout rel) {
        Intent wall = new Intent(activity,MyWalletActivity.class);
        wall.putExtra("walletTag","1");
        startActivity(wall);
    }
    @OnClick({R.id.partner_acc_mamage_tv})
    public void TextViewOnclick(TextView tv) {
        Intent manage = new Intent(activity, AccManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("userinfo", userinfo);
        manage.putExtras(bundle);
        startActivity(manage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                Intent intent = new Intent(activity, BaseQRScanActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(activity, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.partner_scan_tv:
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA},
                            1000);
                } else {
                    //有权限，直接拍照
                    Intent intent = new Intent(activity, BaseQRScanActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.partner_orderlsit_rely:
                Intent order = new Intent(activity,PartnerOrderActivity.class);
                order.putExtra("orderTag","1");
                startActivity(order);
                break;
            case R.id.partner_wait_confirm_lin:
                Intent order2 = new Intent(activity,PartnerOrderActivity.class);
                order2.putExtra("orderTag","2");
                startActivity(order2);
                break;
            case R.id.partner_wait_send_lin:
                Intent order3 = new Intent(activity,PartnerOrderActivity.class);
                order3.putExtra("orderTag","3");
                startActivity(order3);
                break;
            case R.id.partner_wait_get_lin:
                Intent order4 = new Intent(activity,PartnerOrderActivity.class);
                order4.putExtra("orderTag","4");
                startActivity(order4);
                break;
            case R.id.partner_buyer_topup_btn:
                Intent wall = new Intent(activity,MyWalletActivity.class);
                wall.putExtra("walletTag","1");
                startActivity(wall);
                break;
        }
    }
}
