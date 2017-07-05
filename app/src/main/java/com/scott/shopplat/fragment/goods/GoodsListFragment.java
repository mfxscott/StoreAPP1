package com.scott.shopplat.fragment.goods;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scott.shopplat.R;



/**
 * ***************************
 * 主页金融
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 16/10/30 上午11:30
 * ***************************
 */
public class GoodsListFragment extends Fragment {
    private  View view;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods, null);
        activity = getActivity();
        return view;
    }

}
