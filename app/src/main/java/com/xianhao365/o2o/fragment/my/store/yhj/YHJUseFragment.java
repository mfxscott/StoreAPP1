package com.xianhao365.o2o.fragment.my.store.yhj;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.adapter.YHJNoUseListAdapter;
import com.xianhao365.o2o.entity.car.UserCouponEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YHJUseFragment extends Fragment {
    private Activity activity;
    private View view;
    private ListView gridView;
    private ArrayList<UserCouponEntity> yhj ;
    public static int tag;
    private Handler hand;
    public YHJUseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_no_use, null);
        activity = getActivity();
        initView();
        getCouponsUse();
        return view;
    }
    private void initView(){
        gridView = (ListView) view.findViewById(R.id.yhj_nouse_gridv);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SXUtils.getInstance(activity).ToastCenter("=="+position);
            }
        });
        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        yhj = (ArrayList<UserCouponEntity>) msg.obj;
                        gridView.setAdapter(new YHJNoUseListAdapter(activity,yhj,2));
                        break;
                    case 1001:
                        break;
                    case AppClient.ERRORCODE:
                        String msgs = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(msgs);
                        break;
                }
                SXUtils.DialogDismiss();
                return true;
            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            Logs.i("****************************");
        } else {
            Logs.i("2222222222222222****************************");

        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Logs.i("88888888888888888888");
            System.out.println("不可见");

        } else {
            Logs.i("9999999999999999999999");
            System.out.println("当前可见");
        }
    }
    public void getCouponsUse() {
        HttpParams httpp = new HttpParams();
        HttpUtils.getInstance(activity).requestPost(false, AppClient.COUPONS_NOUSE, httpp, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("已使用返回参数======",jsonObject.toString());
                List<UserCouponEntity> couponsUse =  ResponseData.getInstance(activity).parseJsonArray(jsonObject.toString(),UserCouponEntity.class);
                Message msg = new Message();
                msg.what = 1000;
                msg.obj = couponsUse;
                hand.sendMessage(msg);
            }
            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                hand.sendMessage(msg);

            }
        });
    }

}
