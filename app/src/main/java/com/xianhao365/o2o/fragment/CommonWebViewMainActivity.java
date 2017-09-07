package com.xianhao365.o2o.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.BaseActivity;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;


public class CommonWebViewMainActivity extends BaseActivity {
    private WebView myWebView;
    private SwipeRefreshLayout SWwebv;
    public String postUrl = ""; // 第一次不同变量url
    private String LoginUrl = "";
    private String webTitleStr = "";
    private String tag = "";
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_main);
        activity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /**
         * 活动url 点击图片是传入URL
         */
        postUrl = this.getIntent().getStringExtra("postUrl");
        tag = this.getIntent().getStringExtra("tag");
        /**
         * 活动入口地址 测试环境传
         */
        init();
        myWebView = (WebView) findViewById(R.id.common_webview);
        SWwebv = (SwipeRefreshLayout) findViewById(R.id.common_swipe_container);
//		Utils.getInstance(activity).setColorSchemeResources(SWwebv);
//		SWwebv.setColorSchemeResources( R.color.blue, R.color.green, R.color.gray,R.color.red);
        SWwebv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
                myWebView.reload();
            }
        });
//		myWebView.setSaveEnabled(true);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//		webettings.setBuiltInZoomControls(false);// support zoom
//		webSettings.setUseWideViewPort(true);// 这个很关键
//		webSettings.setLoadWithOverviewMode(true);
//		// 开启 DOM storage API 功能
//		webSettings.setDomStorageEnabled(true);
//		manager = OKManager.getInstance(activity);
        //获取密码因子注册设置密码，和支付密码有需要
//		GETPassWordValue(activity,manager,1);
//		GETPassWordValue(activity,manager,2);
        Logs.i("weiview请求链接====",postUrl+"====");
        if(tag.equals("2")){
            myWebView.postUrl(postUrl,null);
        }else {
            myWebView.postUrl(SXUtils.getInstance(activity).getApp().getHttpUrl(), SXUtils.getInstance(activity).WebViewPostJSONObject(activity,postUrl).getBytes());
        }
//        myWebView.addJavascriptInterface(new IntentWebJavaScriptInterface(),
//                "intent");
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //触摸焦点起作用
        myWebView.requestFocus();
        //提供暴露接口给js调用
        myWebView.addJavascriptInterface(new WebViewJavaScriptInterface(myWebView,activity), "androidInterface");
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                webTitleStr = title;
            }
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    if(webTitleStr.contains("data:text/html")){
                        setTitle("网络连接失败");
                        SWwebv.setRefreshing(false);
                        return;
                    }
                    //隐藏进度条
                    SWwebv.setRefreshing(false);
                } else {
                    if (!SWwebv.isRefreshing())
                        SWwebv.setRefreshing(true);
                }
                setTitle(view.getTitle()+"");
            }
        });
        myWebView.setWebViewClient(new myWebViewClient());
    }
    /**
     * 初始化参数
     */
    private void init() {
        registerBack();



    }
    /**
     * 点击返回 返回上一页
     */
    private void returnBack() {
        //判断是生活订单 按系统返回键 直接退出
        if (myWebView.canGoBack()) {
            myWebView.goBackOrForward(-1);
        } else {
            finish();
        }
    }
    // 此按键监听的是返回键，能够返回到上一个网页（通过网页的hostlistery）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            returnBack();
        }
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    class myWebViewClient extends WebViewClient {
        @Override
        public void onFormResubmission(WebView view, Message dontResend,
                                       Message resend) {
            super.onFormResubmission(view, dontResend, resend);
            resend.getCallback();
        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            view.postUrl("file:///android_asset/networkerror.html",null);
        }
        /**
         * 信任https网页的证书
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受所有网站的证书
//			handler.cancel();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webTitleStr = view.getTitle();
            Logs.i("pagefinish========",webTitleStr+"");
        }
        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//			if(webmycount != null){
//				webmycount.cancel();
//			}
//			webmycount = new MyCount(1000*3,1000);
//			webmycount.start();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logs.i("webview跳转链接=====",url);
            view.loadUrl(url);
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            // 登录之后 传递参数值
            case 10:
                Logs.i("webview登录返回=======","======");
                //把请求的URL参数 &转换,连接符号  避免jsp取值失败
//				String str1 = LoginUrl.replace("&", ",");
                if (tag.equals("2")) {
                    myWebView.postUrl(LoginUrl, null);
                } else {
                    myWebView.postUrl(SXUtils.getInstance(activity).getApp().getHttpUrl(), SXUtils.getInstance(activity).WebViewPostJSONObject(activity,LoginUrl).getBytes());
                }
                break;
            case 1001:
                Logs.i("webview=========返回1001");
                myWebView.reload();

                break;
        }
    }





}

