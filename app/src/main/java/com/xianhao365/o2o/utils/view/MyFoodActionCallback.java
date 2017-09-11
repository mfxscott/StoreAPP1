package com.xianhao365.o2o.utils.view;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.SXUtils;

import static com.xianhao365.o2o.fragment.MainFragmentActivity.badge1;

/**
 * 自定义贝赛尔添加购物车效果
 */
public class MyFoodActionCallback implements FoodActionCallback {
    private String skuBarcode;
    private Activity activity;
    public MyFoodActionCallback (Activity activity,String skuBarcode){
       this.skuBarcode = skuBarcode;
        this.activity = activity;
    }
    @Override
    public void addAction(View view) {
        NXHooldeView nxHooldeView = new NXHooldeView(activity);
        int position[] = new int[2];
        view.getLocationInWindow(position);
        nxHooldeView.setStartPosition(new Point(position[0], position[1]));
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        rootView.addView(nxHooldeView);
        int endPosition[] = new int[2];
        badge1.getLocationInWindow(endPosition);
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
        MainFragmentActivity.getInstance().setBadge(true,1);
        SXUtils.getInstance(activity).AddOrUpdateCar(skuBarcode,"1");
    }
}
