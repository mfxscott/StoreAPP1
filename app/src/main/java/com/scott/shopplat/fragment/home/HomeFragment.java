package com.scott.shopplat.fragment.home;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.scott.shopplat.R;
import com.scott.shopplat.activity.GoodsDetailActivity;
import com.scott.shopplat.activity.SearchActivity;
import com.scott.shopplat.adapter.HomeBillGridViewAdapter;
import com.scott.shopplat.adapter.HomeGridViewAdapter;
import com.scott.shopplat.entity.GoodsInfoEntity;
import com.scott.shopplat.fragment.MainFragmentActivity;
import com.scott.shopplat.fragment.my.store.order.MyOrderActivity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.ObservableScrollView;
import com.scott.shopplat.utils.SXUtils;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;
import com.scott.shopplat.utils.view.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * ***************************
 * 首页
 * @author mfx
 * ***************************
 */
public class HomeFragment extends Fragment implements View.OnClickListener,ObservableScrollView.ScrollViewListener {
    private Handler hand;//接受请求返回
    private View view;
    private Activity activity;
    private OKManager manager;//工具类
    private Button scanBtn;
    private RelativeLayout bannerLin;//广告位布局
    private ObservableScrollView scro;
    private Button mainGowebBtn;
    private Button mainGopayBtn;
    private Banner channelBanner ,banner;
    private RelativeLayout searchRel,searchRels;
    private MyGridView gridView;
    private MyGridView homebillRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        activity = getActivity();
        manager = new OKManager(activity);
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        SXUtils.getInstance().setSysStatusBar(activity,R.color.dialog_btn);
        httpChanner();
        initView(view);
        int height = bannerLin.getMeasuredHeight();
        Logs.i("==========","========="+height);
        return view;
    }
    /**
     * 首页九宫格
     * @return
     */
    private List<Map<String,String>> getGrideData(){
        List<Map<String,String>> list = new ArrayList<>();

        for(int i=0;i<4;i++){
            Map<String,String>  map = new HashMap<>();
            switch (i){
                case 0:
                    map.put("name","开始购买");
                    map.put("imageUrl","http://pic.qiantucdn.com/58pic/11/72/82/37I58PICgk5.jpg");
                    break;
                case 1:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","常购清单");
                    break;
                case 2:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","我的红包");
                    break;
                case 3:
                    map.put("imageUrl"," http://pic2.cxtuku.com/00/07/42/b701b8c89bc8.jpg");
                    map.put("name","我的订单");
                    break;
            }
            map.put("state",""+i);
            list.add(map);
        }
        return list;
    }
    /**
     * 轮播图
     */
    private void httpChanner() {
        RequestBody requestBody = new FormBody.Builder()
                .add("position", "1")//1 首页
                .build();
        manager.sendStringByPostMethod(requestBody, AppClient.APP_SWIPER, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
//                CacheData.getInstance().WirtCacheData(AppClient.CACHDATAPATH, AppClient.MAINBANNER, jsonObject.toString());

                Message msg = new Message();
                msg.what = 1000;
                msg.obj = "";
                hand.sendMessage(msg);
            }

            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                if(hand != null)
                    hand.sendMessage(msg);
                Logs.i("服务器连接异常", "========" + strError.toString());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (AppClient.TAG1) {
            MainFragmentActivity.homeRb.setChecked(true);
            AppClient.TAG1 = false;
            return;
        }
        if (AppClient.TAG2) {
            MainFragmentActivity.goodsRb.setChecked(true);
            AppClient.TAG2 = false;
            return;
        }
        if (AppClient.TAG3) {
            MainFragmentActivity.billRb.setChecked(true);
            AppClient.TAG3 = false;
            return;
        }
        if (AppClient.TAG4) {
            MainFragmentActivity.carRb.setChecked(true);
            AppClient.TAG4 = false;
            return;
        }
        if (AppClient.TAG5) {
            MainFragmentActivity.myRb.setChecked(true);
            AppClient.TAG5 = false;
            return;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_search_lin:case R.id.home_search_gone_lin:
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.home_go_bill_rel:
                MainFragmentActivity.billRb.setChecked(true);
                break;
//            case R.id.home_goweb_btn:
//                MainFragmentActivity.getInstance().setBadge(false,1);
//                Intent aa = new Intent(activity, StoreMapActivity.class);
//                activity.startActivity(aa);
//                break;
//            case R.id.home_gopay_btn:
//
//                Intent intent = new Intent(activity, LoginNameActivity.class);
//                activity.startActivity(intent);
//                //购物车远点加减
//                MainFragmentActivity.getInstance().setBadge(true,1);
//                break;
//            case R.id.home_scan_btn:
//
//                int h =  bannerLin.getHeight();
//                int hh = mainGowebBtn.getHeight();
//                //scrollview 滚动指定位置
//                scro.scrollTo(0, h+hh);
//
////                Intent intent = new Intent(activity, Regis1Activity.class);
////                activity.startActivity(intent);
//                break;
        }
    }
    private void initViewPager() {
        XTabLayout tabLayout = (XTabLayout) view.findViewById(R.id.main_xTablayout);
//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("肉禽类"));
        tabLayout.addTab(tabLayout.newTab().setText("新鲜蔬菜"));
        tabLayout.addTab(tabLayout.newTab().setText("米面粮油"));
        tabLayout.addTab(tabLayout.newTab().setText("水产冻货"));
        tabLayout.addTab(tabLayout.newTab().setText("休闲酒饮"));
        tabLayout.addTab(tabLayout.newTab().setText("面食面粉"));
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
    /**
     * 首页常用清单
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
            type.setGoodsname("我是商品标题"+i);
            typeList.add(type);

        }
        return typeList;
    }
    private void initView(View view) {
        LinearLayout searchLin = (LinearLayout) view.findViewById(R.id.home_search_lin);
        searchLin.setOnClickListener(this);
        LinearLayout searchGoneLin = (LinearLayout) view.findViewById(R.id.home_search_gone_lin);
        searchGoneLin.setOnClickListener(this);

        RelativeLayout goBillRel = (RelativeLayout) view.findViewById(R.id.home_go_bill_rel);
        goBillRel.setOnClickListener(this);

//        homebillRv = (RecyclerView) view.findViewById(R.id.home_list_recyclerv);
//        homebillRv.setLayoutManager(new LinearLayoutManager(homebillRv.getContext()));
//        homebillRv.setItemAnimator(new DefaultItemAnimator());
//        final HomeBillRecyclerViewAdapter simpAdapter = new HomeBillRecyclerViewAdapter(getActivity(),getTypeInfoData());
//        homebillRv.setAdapter(simpAdapter);


        homebillRv = (MyGridView) view.findViewById(R.id.home_list_recyclerv);
        HomeBillGridViewAdapter simpAdapter = new HomeBillGridViewAdapter(getActivity(),getTypeInfoData());
        homebillRv.setAdapter(simpAdapter);
        homebillRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });



        initViewPager();
        gridView = (MyGridView) view.findViewById(R.id.main_gridv);
        gridView.setAdapter(new HomeGridViewAdapter(activity,getGrideData()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        MainFragmentActivity.goodsRb.setChecked(true);
                        break;
                    case 1:
                        MainFragmentActivity.billRb.setChecked(true);
                        break;
                    case 2:
                        MainFragmentActivity.myRb.setChecked(true);
                        break;
                    case 3:
                        Intent order = new Intent(activity,MyOrderActivity.class);
                        order.putExtra("orderTag","1");
                        startActivity(order);
                        break;
                }
            }
        });

        searchRel = (RelativeLayout) view.findViewById(R.id.main_search_rel);
        searchRels = (RelativeLayout) view.findViewById(R.id.main_search_rels);




        scro = (ObservableScrollView) view.findViewById(R.id.main_scroll_view);
        scro.setScrollViewListener(this);

        bannerLin = (RelativeLayout) view.findViewById(R.id.main_banner_lin);


//        mainGowebBtn = (Button) view.findViewById(R.id.home_goweb_btn);
//        mainGowebBtn.setOnClickListener(this);
//        scanBtn = (Button) view.findViewById(R.id.home_scan_btn);
//        scanBtn.setOnClickListener(this);
////        RequestReqMsgData.UpdateVersion(manager, activity, hand);
//        mainGopayBtn = (Button) view.findViewById(R.id.home_gopay_btn);
//        mainGopayBtn.setOnClickListener(this);



        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        break;
                    case AppClient.ERRORCODE:
                        String str = (String) msg.obj;
                        SXUtils.getInstance(activity).ToastCenter(str);
                        break;
                    case AppClient.UPDATEVER:
//                        SXUtils.getInstance().ToastCenter(activity, "版本更新");
                        break;
                }
                return true;
            }
        });
        setBanner();
        setChannel();
    }



    private void setBanner(){
        banner = (Banner) view.findViewById(R.id.banner);
//        List<String> images = new ArrayList<String>();
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497598051&di=136b6c564a6d8d59e77ce349616996e9&imgtype=jpg&er=1&src=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_0_72213646D1378690088_23.jpg");
//        images.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3145185115,3541103163&fm=26&gp=0.jpg");
//        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4280343775,3437702687&fm=26&gp=0.jpg");
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(activity, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
        List<Integer> images = new ArrayList<Integer>();
        images.add(R.mipmap.banner11);
        images.add(R.mipmap.banner33);
        images.add(R.mipmap.banner44);
        images.add(R.mipmap.banner55);
        images.add(R.mipmap.banner66);
        List<String> titlestr = new ArrayList<String>();
        titlestr.add("我是第一个图片");
        titlestr.add("我是第2个图片");
        titlestr.add("我是第3个图片");
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
//        //显示标题样式水平显示
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        //设置标题文本
//        banner.setBannerTitles(titlestr);
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        //DepthPag折叠
        banner.setBannerAnimation(Transformer.Default);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    private void setChannel(){
        channelBanner = (Banner) view.findViewById(R.id.channel);
        channelBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(activity, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
        List<Integer> images = new ArrayList<Integer>();
        images.add(R.mipmap.banner22);
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497598051&di=136b6c564a6d8d59e77ce349616996e9&imgtype=jpg&er=1&src=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_0_72213646D1378690088_23.jpg");
//        images.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3145185115,3541103163&fm=26&gp=0.jpg");
//        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4280343775,3437702687&fm=26&gp=0.jpg");
        //设置图片加载器
        channelBanner.setImageLoader(new GlideImageLoader());
        channelBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
//        //显示标题样式水平显示
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        //设置标题文本
//        banner.setBannerTitles(titlestr);
        channelBanner.isAutoPlay(true);
        //设置图片集合
        channelBanner.setImages(images);
        //设置banner动画效果
        //DepthPag折叠
        channelBanner.setBannerAnimation(Transformer.Default);
        //banner设置方法全部调用完毕时最后调用
        channelBanner.start();
    }
    //接收线程回调

    /**
     * NAIN UI主线程
     BACKGROUND 后台线程
     POSTING 和发布者处在同一个线程
     ASYNC 异步线程
     * @param event 为Object  两个方法都会执行
     *
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(Object event) {
//        Logs.i("onMessageEvent11111111=============");
//        MessageEventEntity msg = (MessageEventEntity) event;
//        int msgint = msg.tag;
//        switch (msgint){
//            case 1:
//                Toast.makeText(activity,msg.tag+"=====",Toast.LENGTH_LONG).show();
//                break;
//            case 2:
//                Toast.makeText(activity,msg.obj.toString()+"=====",Toast.LENGTH_LONG).show();
//                break;
//        }
//    };
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainStringThread(MessageEventEntity event) {
//        Logs.i("onEventMainThread222222=============");
//        Toast.makeText(activity,event+"2222",Toast.LENGTH_LONG).show();
//    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainIntead(String event) {
        Logs.i("onEventMainThread3333=============");
        Toast.makeText(activity,event+"3333",Toast.LENGTH_LONG).show();
    };
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        channelBanner.startAutoPlay();
        banner.startAutoPlay();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        channelBanner.stopAutoPlay();
        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // Log.i("TAG", "y--->" + y + "    height-->" + height);
        if (y <= 0) {
//            searchRel.setBackgroundColor(Color.argb((int) y, y, y, y));//AGB由相关工具获得，或者美工提供
            searchRel.setVisibility(View.VISIBLE);
            searchRels.setVisibility(View.GONE);
        } else if (y > 0 && y <= bannerLin.getHeight()) {
            float scale = (float) y / bannerLin.getHeight();
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
//            searchRels.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
            searchRel.setVisibility(View.VISIBLE);
            searchRels.setVisibility(View.GONE);
        } else {
            searchRels.setVisibility(View.VISIBLE);
            searchRel.setVisibility(View.GONE);
//            searchRels.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
        }
        Log.i("====","==========="+y);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).error(R.mipmap.default_head).into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }
    }
}
