package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.ObservableScrollView;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 * @author mfx
 * @time  2017/7/17 15:53
 */
public class GoodsDetailActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener,View.OnClickListener{
    private  Banner banner;
    private RelativeLayout titleRelay,disTitleRel;
    private Activity activity;
    private LinearLayout xxxxlin;//商品详细信息
    private TextView  xxTv,ggTv;
    private View   xxLine,ggLine;
    private ObservableScrollView  scro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        activity = this;
        setBanner();
        initView();
    }
    private void initView(){
        LinearLayout   tabXXXXLin = (LinearLayout) findViewById(R.id.goods_detail_tab_xxxx_lin);
        tabXXXXLin.setOnClickListener(this);
        LinearLayout    tabGGCSLin = (LinearLayout) findViewById(R.id.goods_detail_tab_ggcs_lin);
        tabGGCSLin.setOnClickListener(this);
        LinearLayout  backlin = (LinearLayout) findViewById(R.id.goods_detail_goback_linlay);
        LinearLayout  disBackLin = (LinearLayout) findViewById(R.id.goods_detail_dis_goback_linlay);
        backlin.setOnClickListener(this);
        disBackLin.setOnClickListener(this);
        TextView  feedback = (TextView) findViewById(R.id.goods_detail_feedback_tv);
        feedback.setOnClickListener(this);
        TextView  gocar = (TextView) findViewById(R.id.goods_detail_gocar_btn);
        gocar.setOnClickListener(this);


        xxTv = (TextView) findViewById(R.id.goods_detail_tab_xxxx_tv);
        ggTv = (TextView) findViewById(R.id.goods_detail_tab_ggcs_tv);
        xxLine = findViewById(R.id.goods_detail_tab_xxxx_line_v);
        ggLine = findViewById(R.id.goods_detail_tab_ggcs_line);


        scro = (ObservableScrollView) findViewById(R.id.goods_detail_scroll_view);
        scro.setScrollViewListener(this);
        titleRelay = (RelativeLayout) findViewById(R.id.goods_detial_title_rel);
        disTitleRel = (RelativeLayout) findViewById(R.id.goods_detail_dis_title_rel);
        xxxxlin = (LinearLayout) findViewById(R.id.goods_detail_xxxx_lin);

        ImageView img1 = (ImageView) findViewById(R.id.goods_detail_img1);
        ImageView img2 = (ImageView) findViewById(R.id.goods_detail_img2);
        ImageView img3 = (ImageView) findViewById(R.id.goods_detail_img3);
        ImageView img4 = (ImageView) findViewById(R.id.goods_detail_img4);
        Glide.with(activity).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_dy).into(img1);
        Glide.with(activity).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_hlg).into(img2);
        Glide.with(activity).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_dg).into(img3);
        Glide.with(activity).load("android.resource://com.xianhao365.o2o/mipmap/"+R.mipmap.img_jd).into(img4);

    }
    private void setBanner(){
        banner = (Banner) findViewById(R.id.goods_detail_banner);
//        List<String> images = new ArrayList<String>();
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497598051&di=136b6c564a6d8d59e77ce349616996e9&imgtype=jpg&er=1&src=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_0_72213646D1378690088_23.jpg");
//        images.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3145185115,3541103163&fm=26&gp=0.jpg");
//        images.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4280343775,3437702687&fm=26&gp=0.jpg");
        List<Integer> images = new ArrayList<Integer>();
        images.add(R.mipmap.img_jd);
        images.add(R.mipmap.img_dg);
        images.add(R.mipmap.img_dy);

        List<String> titlestr = new ArrayList<String>();
        titlestr.add("我是第一个图片");
        titlestr.add("我是第2个图片");
        titlestr.add("我是第3个图片");
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
//        //显示标题样式水平显示
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
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

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            disTitleRel.setVisibility(View.VISIBLE);
            titleRelay.setVisibility(View.GONE);
        } else if (y > 0 && y <= banner.getHeight()-disTitleRel.getHeight()) {
            titleRelay.setVisibility(View.GONE);
            disTitleRel.setVisibility(View.VISIBLE);
        } else {
            titleRelay.setVisibility(View.VISIBLE);
            disTitleRel.setVisibility(View.GONE);
        }
        Logs.i(y+"============"+(xxxxlin.getHeight()+"==="+banner.getHeight()));
        if(y >= xxxxlin.getHeight()/2){
            xxTv.setTextColor(getResources().getColor(R.color.col_999));
            xxLine.setBackgroundResource(R.color.transparent);
            ggLine.setBackgroundResource(R.color.orange);
            ggTv.setTextColor(getResources().getColor(R.color.orange));

        }else{
            xxTv.setTextColor(getResources().getColor(R.color.orange));
            xxLine.setBackgroundResource(R.color.orange);
            ggLine.setBackgroundResource(R.color.transparent);
            ggTv.setTextColor(getResources().getColor(R.color.col_999));
        }
        Logs.i("返回滑动值====="+y);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_detail_tab_xxxx_lin:
                xxTv.setTextColor(getResources().getColor(R.color.orange));
                xxLine.setBackgroundResource(R.color.orange);
                ggLine.setBackgroundResource(R.color.transparent);
                ggTv.setTextColor(getResources().getColor(R.color.col_999));
                scro.scrollTo(0,banner.getHeight()+10);
                break;
            case R.id.goods_detail_tab_ggcs_lin:
                xxTv.setTextColor(getResources().getColor(R.color.col_999));
                xxLine.setBackgroundResource(R.color.transparent);
                ggLine.setBackgroundResource(R.color.orange);
                ggTv.setTextColor(getResources().getColor(R.color.orange));
                scro.scrollTo(0, xxxxlin.getHeight());
                Logs.i(xxxxlin.getHeight()+banner.getHeight()+"=======");
                break;
            case  R.id.goods_detail_goback_linlay: case  R.id.goods_detail_dis_goback_linlay:
                finish();
                break;
            case R.id.goods_detail_feedback_tv:
                Intent intent = new Intent(activity,GoodsFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.goods_detail_gocar_btn:
                AppClient.TAG4 = true;
//                MainFragmentActivity.carRb.setChecked(true);
                finish();
                break;
        }
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
