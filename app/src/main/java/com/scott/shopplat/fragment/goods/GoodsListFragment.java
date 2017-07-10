package com.scott.shopplat.fragment.goods;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidkun.xtablayout.XTabLayout;
import com.scott.shopplat.R;
import com.scott.shopplat.adapter.MainGoodsTypeAdapter;
import com.scott.shopplat.adapter.MainTypeInfoAdapter;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.entity.MainGoodsTypeEntity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;

import java.util.ArrayList;
import java.util.List;


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
    private GridView typeGridView;
    private GridView infoGridView;
    private  MainGoodsTypeAdapter  typeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods, null);
        activity = getActivity();
        initView();
        return view;
    }
    private List<MainGoodsTypeEntity> getTypeData()
    {
        List<MainGoodsTypeEntity> typeList=new ArrayList<>();
        for(int i=0;i<10;i++){
            MainGoodsTypeEntity type = new MainGoodsTypeEntity();
            switch (i){
                case 0:
                    type.setTypeName("促销");
                    break;
                case 1:
                    type.setTypeName("根茎");
                    break;
                case 2:
                    type.setTypeName("绿色");
                    break;
                case 3:
                    type.setTypeName("萝卜");
                    break;
                case 4:
                    type.setTypeName("菌类");
                    break;
                default:
                    type.setTypeName("其他");

            }
            typeList.add(type);

        }
        return typeList;
    }

    /**
     * 商品分类详情商品
     * @return
     */
    private List<GoodsInfoEntity> getTypeInfoData()
    {
        List<GoodsInfoEntity> typeList=new ArrayList<>();
        for(int i=0;i<10;i++){
            GoodsInfoEntity type = new GoodsInfoEntity();
            switch (i){
                case 0:
                    type.setGoodsname("dsfdf");
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:

            }
            typeList.add(type);

        }
        return typeList;
    }
    private void initView(){
        initViewPager();

        typeGridView = (GridView) view.findViewById(R.id.main_goods_type_gridv);
        typeAdapter= new MainGoodsTypeAdapter(activity,getTypeData());
        typeGridView.setAdapter(typeAdapter);
        typeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SXUtils.getInstance(activity).ToastCenter("=="+position);
                typeAdapter.changeSelected(position);//刷新
            }
        });

        infoGridView = (GridView) view.findViewById(R.id.main_goods_info_gridv);
        infoGridView.setAdapter(new MainTypeInfoAdapter(activity,getTypeInfoData()));
        infoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SXUtils.getInstance(activity).ToastCenter("=="+position);
            }
        });

    }
    private void initViewPager() {
        XTabLayout tabLayout = (XTabLayout) view.findViewById(R.id.goods_xTablayout);
//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("全部菜品"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 9"));
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                Logs.i("tab===============111111="+ tab.getPosition());
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
                Logs.i("tab===============222222222="+ tab.getPosition());
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {
                Logs.i("tab===============3333333333="+ tab.getPosition());
            }
        });

    }
}
