package com.xianhao365.o2o.fragment.my.store.order;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.utils.Logs;

import java.util.ArrayList;
import java.util.List;


public class MyOrderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private String orderTag;
    private Activity activity;
    private Handler hand;
    private int indexPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        activity = this;
        orderTag = this.getIntent().getStringExtra("orderTag");
        initView();
        initViewPager();
    }
    private void initView(){

    }
    private void initViewPager() {
        LinearLayout allTitleGobackLinlay = (LinearLayout) findViewById(R.id.all_title_goback_linlay);
        allTitleGobackLinlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView allTitleName = (TextView) findViewById(R.id.all_title_name);
        allTitleName.setText("我的订单");

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("待支付");
        titles.add("待发货");
        titles.add("待收货");
        titles.add("已完成");
        fragments.add(new WaitPayFragment());
        fragments.add(new WaitSendFragment());
        fragments.add(new WaitTakeFragment());
        fragments.add(new WaitDoneFragment());
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager = (ViewPager) findViewById(R.id.order_viewPager);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(1);//预加载界面
        //将TabLayout和ViewPager关联起来。
        XTabLayout tabLayout = (XTabLayout) findViewById(R.id.order_xtablayout);
        tabLayout.setupWithViewPager(viewPager);
        if(Integer.parseInt(orderTag) ==1){
            viewPager.setCurrentItem(0);
        }else if(Integer.parseInt(orderTag) ==2){
            viewPager.setCurrentItem(1);
        }
        else if(Integer.parseInt(orderTag) ==3){
            viewPager.setCurrentItem(2);
        }
//        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                Logs.i("========="+position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    class FragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);

        }
    }


}
