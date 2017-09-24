package com.xianhao365.o2o.fragment.my.partner;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.fragment.my.store.AccManageActivity;
import com.xianhao365.o2o.fragment.my.store.MyWalletActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 鲜好合伙人
 */
public class PartnerFragment extends Fragment {
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
    private void initData(){
        SXUtils.getInstance(activity).getUserInfoHttp(hand);
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
}
