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
public class YHJUsedFragment extends Fragment {

    private Activity activity;
    private View view;
    public YHJUsedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_yhjused, null);
        SXUtils.getInstance(activity).ToastCenter("已过期");
        return view;
    }

}
