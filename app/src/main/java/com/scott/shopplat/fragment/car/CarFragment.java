package com.scott.shopplat.fragment.car;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scott.shopplat.R;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;


/**
 * ***************************
 * 生活版块
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 16/10/29 下午9:28
 * ***************************
 */
public class CarFragment extends Fragment {
    private  View view;
    private Activity activity;
    private OKManager manager;//工具类
    private Handler hand;
    private int indexPage= 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car, null);
        activity = getActivity();
        manager = new OKManager(activity);
        init();
        HttpLiveSp();
//        SXUtils.getInstance(activity).setSysStatusBar(activity,R.color.white);
        return view;
    }
    private void init(){

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        SXUtils.getInstance(activity).ToastCenter("成功");
                        break;
                    case AppClient.ERRORCODE:
                        String errormsg = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(errormsg+"");
                        break;
                }
                return true;
            }
        });
    }
    private void HttpLiveSp() {
    }
}
























