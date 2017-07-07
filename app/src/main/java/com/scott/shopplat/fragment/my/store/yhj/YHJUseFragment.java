package com.scott.shopplat.fragment.my.store.yhj;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scott.shopplat.R;
import com.scott.shopplat.utils.SXUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class YHJUseFragment extends Fragment {
    private Activity activity;
    private View view;
    public YHJUseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_yhjuse, null);
        SXUtils.getInstance(activity).ToastCenter("已使用");
        return view;

    }

}
