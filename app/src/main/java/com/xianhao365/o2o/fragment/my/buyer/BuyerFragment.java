package com.xianhao365.o2o.fragment.my.buyer;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.BuyerBillGridViewAdapter;
import com.xianhao365.o2o.adapter.BuyerQHGridViewAdapter;
import com.xianhao365.o2o.entity.UserInfoEntity;
import com.xianhao365.o2o.fragment.my.buyer.purchase.CGBillListActivity;
import com.xianhao365.o2o.fragment.my.store.AccManageActivity;
import com.xianhao365.o2o.fragment.my.store.MyWalletActivity;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.GlideRoundTransform;
import com.xianhao365.o2o.utils.view.MyGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * ***************************
 * 初始我的界面 区分类型 合伙人，联创中心，供货商
 * 4:供应商,8:联创中心,16:合伙人,32:摊主店铺,64:消费者,
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 17/2/16 上午11:56
 * ***************************
 */
public class BuyerFragment extends Fragment implements View.OnClickListener{
    private  View view;
    private Activity activity;
    private OKManager manager;
    private Handler hand;
    private MyGridView gridView;
    private MyGridView qsgridView;
    private RelativeLayout cgOrderListLin;
    @BindView(R.id.my_acc_mamage_tv)
    TextView  accmanage;
    @BindView(R.id.buyer_hhr_liny)
    LinearLayout qhshLiny;
    //    @BindView(R.id.buyer_wallet_have_liny)//有钱包相关数据
    LinearLayout walletHaveLin;
    //    @BindView(R.id.buyer_wallet_null_liny)//没有钱包相关数据
    LinearLayout walletNullLin;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Unbinder unbinder;
    private UserInfoEntity userinfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        activity = getActivity();
        unbinder =   ButterKnife.bind(this, view);
        manager = new OKManager(activity);
//        SXUtils.getInstance().setSysStatusBar(activity,R.color.red);
        init();
        LoadData();
        return view;
    }
    /**
     * 加载接口数据
     */
    private void LoadData(){
        if(SXUtils.getInstance(activity).IsLogin()) {
//            GetUserInfoHttp();
            SXUtils.getInstance(activity).getUserInfoHttp(hand);
//        GetGYSBillListHttp();
//        GetOrderListHttp();
            GetUserWalletHttp();
        }
    }
    //初始化
    private void init(){
        accmanage.setOnClickListener(this);

        walletNullLin = (LinearLayout) view.findViewById(R.id.buyer_wallet_null_liny);
        walletHaveLin = (LinearLayout) view.findViewById(R.id.buyer_wallet_have_liny);
        if(AppClient.USERROLETAG.equals("16")){
            qhshLiny.setVisibility(View.VISIBLE);
        }
        TextView  buyerTv = (TextView) view.findViewById(R.id.buyer_topup_btn);
        buyerTv.setOnClickListener(this);
        RelativeLayout wallet = (RelativeLayout) view.findViewById(R.id.buyer_per_wallet);
        wallet.setOnClickListener(this);

        cgOrderListLin = (RelativeLayout) view.findViewById(R.id.my_cg_store_myorder_rel);
        cgOrderListLin.setOnClickListener(this);
        gridView = (MyGridView) view.findViewById(R.id.buyer_bille_gridv);
        gridView.setAdapter(new BuyerBillGridViewAdapter(activity,getGYSBillData()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent state1 = new Intent(activity,CGBillListActivity.class);
                        state1.putExtra("state","10");
                        startActivity(state1);
                        break;
                    case 1:
//                        MainFragmentActivity.billRb.setChecked(true);
                        Intent state2 = new Intent(activity,CGBillListActivity.class);
                        state2.putExtra("state","20");
                        startActivity(state2);
                        break;
                    case 2:
                        Intent state3 = new Intent(activity,CGBillListActivity.class);
                        state3.putExtra("state","30");
                        startActivity(state3);
                        break;
                    case 3:
                        Intent state4 = new Intent(activity,CGBillListActivity.class);
                        state4.putExtra("state","40");
                        startActivity(state4);
                        break;
                }
            }
        });
        qsgridView = (MyGridView) view.findViewById(R.id.buyer_qh_gridv);
        qsgridView.setAdapter(new BuyerQHGridViewAdapter(activity,getGrideData()));
        qsgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.buyer_center_swipe_container);
//        swipeRefreshLayout.setColorSchemeResources( R.color.qblue, R.color.red, R.color.btn_gray);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
//                myWebView.reload();
//                getUserInfoHttp();
                LoadData();
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
    //获取到用户信息，更新相关UI
    private void initUserInfo(UserInfoEntity userInfo){
        TextView name = (TextView) view.findViewById(R.id.buyer_name_tv);
        ImageView headImg = (ImageView) view.findViewById(R.id.my_head_img);

        Glide.with(activity).load((String)userInfo.getIcon()).placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head).transform( new GlideRoundTransform(activity)).into(headImg);
        name.setText(userInfo.getUsername());

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buyer_topup_btn:
                Intent intent = new Intent(activity,ExtractDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.buyer_per_wallet:
                Intent wall = new Intent(activity,MyWalletActivity.class);
                wall.putExtra("walletTag","1");
                startActivity(wall);
                break;
            //采购清单列表
            case R.id.my_cg_store_myorder_rel:
                Intent cgbill = new Intent(activity,CGBillListActivity.class);
                cgbill.putExtra("state","");
                startActivity(cgbill);
                break;
            case R.id.my_acc_mamage_tv:
                Intent manage = new Intent(activity,AccManageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("userinfo",userinfo);
                manage.putExtras(bundle);
                startActivity(manage);
                break;
        }
    }
    /**
     * 供应商采购清单店铺
     * @return
     */
    private List<Map<String,String>> getGYSBillData(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            Map<String,String>  map = new HashMap<>();
            switch (i){
                case 0:
                    map.put("name","待接单");
                    map.put("imageUrl","http://pic.qiantucdn.com/58pic/11/72/82/37I58PICgk5.jpg");
                    break;
                case 1:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","待发货");
                    break;
                case 2:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","待收货");
                    break;
                case 3:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","已完成");
                    break;
            }
            map.put("state",""+i);
            list.add(map);
        }
        return list;
    }
    /**
     * 首页九宫格
     * @return
     */
    private List<Map<String,String>> getGrideData(){
        List<Map<String,String>> list = new ArrayList<>();
        for(int i=0;i<6;i++){
            Map<String,String>  map = new HashMap<>();
            switch (i){
                case 0:
                    map.put("name","鲜好店铺");
                    map.put("imageUrl","http://pic.qiantucdn.com/58pic/11/72/82/37I58PICgk5.jpg");
                    break;
                case 1:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","美好店铺");
                    break;
                case 2:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","鲜美店铺");
                    break;
                case 3:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","好鲜店铺");
                    break;
                case 4:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","好鲜店铺1");
                    break;
                case 5:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","好鲜店铺2");
                    break;
            }
            map.put("state",""+i);
            list.add(map);
        }
        return list;
    }
    /**
     * 获取供应商信息
     */
    public void GetUserInfoHttp() {
        HttpUtils.getInstance(activity).requestPost(false,AppClient.USER_ISPPLY_NFO, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                UserInfoEntity gde = null;
                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),UserInfoEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = gde;
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = "供应商信息="+strError;
                hand.sendMessage(msg);

            }
        });
    }


    /**
     * 获取用户余额
     */
    public void GetUserWalletHttp() {
        HttpUtils.getInstance(activity).requestPost(false,AppClient.USER_WALLET, null, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {

                walletHaveLin.setVisibility(View.VISIBLE);
//                WalletInfoEntity gde = null;
//                gde = ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),WalletInfoEntity.class);
//                Message msg = new Message();
//                msg.what = 1001;
//                msg.obj = gde;
//                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                walletNullLin.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}