package com.xianhao365.o2o.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.entity.FoodActionCallback;
import com.xianhao365.o2o.entity.goods.GoodsDetailEntity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.ObservableScrollView;
import com.xianhao365.o2o.utils.SXUtils;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.ResponseData;
import com.xianhao365.o2o.utils.view.NXHooldeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情
 * @author mfx
 * @time  2017/7/17 15:53
 */
public class GoodsDetailActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener,View.OnClickListener,FoodActionCallback {
    private  Banner banner;
    private RelativeLayout titleRelay,disTitleRel;
    private Activity activity;
    private LinearLayout xxxxlin;//商品详细信息
    private TextView  xxTv,ggTv;
    private View   xxLine,ggLine;
    private ObservableScrollView  scro;
    private Handler hand;
    private String cno;//商品id
    @BindView(R.id.goods_detail_add_tv)
    TextView addcar;
    @BindView(R.id.goods_detail_car_num_tv)
    TextView carNum;
    @BindView(R.id.goods_detail_name_tv)
    TextView goodsNameTv;//商品名称
    @BindView(R.id.goods_detail_pf_price_tv)
    TextView pfPriceTv;//商品批发价
    @BindView(R.id.goods_detail_marke_price_tv)
    TextView marketPriceTv;//商品市场价
    @BindView(R.id.goods_detail_model_tv)
    TextView goodsModelTv;//商品规格
    @BindView(R.id.goods_detail_address_tv)
    TextView goodsAddress;//商品产地
    @BindView(R.id.goods_detail_level_tv)
    TextView goodsLevel;//商品等级
    @BindView(R.id.goods_detail_unit_tv)
    TextView goodsUnit;//商品包装
    @BindView(R.id.goods_detail_gg_model_tv)
    TextView goodsggModel;//商品规格
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        cno = this.getIntent().getStringExtra("cno");
        activity = this;
        setBanner();
        initView();
        SXUtils.showMyProgressDialog(activity,false);
        getHttpGoodsDetail();
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
        addcar.setOnClickListener(this);

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

        hand = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1000:
                        GoodsDetailEntity goodsdetail = (GoodsDetailEntity) msg.obj;
                        if(goodsdetail.getSkuList() != null && goodsdetail.getSkuList().size()>0) {
                            Logs.i("多规格商品数量========="+goodsdetail.getSkuList().size());
                            goodsModelTv.setText(goodsdetail.getSkuList().get(0).getGoodsModel());
                            marketPriceTv.setText("¥"+goodsdetail.getSkuList().get(0).getMarketPrice());
                            pfPriceTv.setText("¥"+goodsdetail.getSkuList().get(0).getShopWholesalePrice());
                            goodsggModel.setText(goodsdetail.getSkuList().get(0).getGoodsModel());
                            marketPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                        }
                        goodsNameTv.setText(goodsdetail.getGoodsName()+"");
                        goodsAddress.setText(goodsdetail.getGoodsPlace());
                        goodsUnit.setText(goodsdetail.getGoodsUnit());
                        goodsLevel.setText(goodsdetail.getFoodGrade());

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
            //40 为清单两段间距距离
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
                return;
            case R.id.goods_detail_add_tv:
                this.addAction(v);
                break;
        }
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
        carNum.getLocationInWindow(endPosition);
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
        String carStr = carNum.getText().toString().trim();
        if(TextUtils.isEmpty(carStr)){
            carNum.setText("1");
        }else{
            int carn = Integer.parseInt(carStr);
            carn ++;
            carNum.setText(carn+"");
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

    /**
     * 获取商品详情
     */
//    public void getHttpGoodsDetail(){
//        if(TextUtils.isEmpty(cno)) {
//            SXUtils.getInstance(activity).ToastCenter("商品id为空");
//            return;
//        }
//        RequestBody requestBody = new FormBody.Builder()
//
//                .add("catNo", cno)//二级分类查询00002
////                .add("vcode", codeStr)
////                .add("registerType", "0")//0=手机,1=微信,2=QQ
////                .add("password", psdStr)
////                .add("tag","64")
//                .build();
//        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.GOODS_DETAIL, new OKManager.Func4() {
//            @Override
//            public void onResponse(Object jsonObject) {
//                Logs.i("商品详情发送成功返回参数=======",jsonObject.toString());
//                JSONObject jsonObject1 = null;
////                try {
////                    List<GoodsTypeEntity> goodsTypeList =  ResponseData.getInstance(activity).getGoodsTypeData(jsonObject);
////                    Message msg = new Message();
////                    msg.what = 1000;
////                    msg.obj = goodsTypeList;
////                    hand.sendMessage(msg);
////                } catch (JSONException e) {
////                    Message msg = new Message();
////                    msg.what = AppClient.ERRORCODE;
////                    msg.obj = e.toString();
////                    hand.sendMessage(msg);
////                }
//
//            }
//            @Override
//            public void onResponseError(String strError) {
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = strError;
//                hand.sendMessage(msg);
//            }
//        });
//    }
    /**
     * 获取商品详情
     */
    public void getHttpGoodsDetail(){
        if(TextUtils.isEmpty(cno)){
            return;
        }
        HttpParams params = new HttpParams();
        params.put("id", cno);
        HttpUtils.getInstance(activity).requestPost(false,AppClient.GOODS_DETAIL, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                GoodsDetailEntity gde = (GoodsDetailEntity) ResponseData.getInstance(activity).parseJsonWithGson(jsonObject.toString(),GoodsDetailEntity.class);
                if(gde != null){
                    Message msg = new Message();
                    msg.what = 1000;
                    msg.obj = gde;
                    hand.sendMessage(msg);
                }else{
                    Message msg = new Message();
                    msg.what = AppClient.ERRORCODE;
                    msg.obj = "数据解析异常";
                    hand.sendMessage(msg);
                }

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
