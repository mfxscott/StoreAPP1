package com.xianhao365.o2o.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * 与H5交互工具类
 * 定义相关接口提供给H5调用
 * @author mfx
 */
public class WebViewJavaScriptInterface {
    private Activity context;
    private WebView webView;
    public WebViewJavaScriptInterface(WebView webview, Activity activity){
        this.context = activity;
        this.webView = webview;
    }
//     androidInterface.androidWebViewMethod("tag","jsonString");
    @JavascriptInterface
    public void androidWebViewMethod(String tag,String json) {
//        int resulttag = Integer.parseInt(str);
        switch (0){
            case 0:
//                支付成功
//                Contants.payResultCallBack.payResult(str+"");
                context.finish();
                break;
            case 1:
                //支付失败
//                Contants.payResultCallBack.payResult(str+"");
                context.finish();
                break;
            case 2:
                //支付处理中
//                Contants.payResultCallBack.payResult(str+"");
                context.finish();
                break;
            case 3:
                //取消支付
//                PayUtils.ToastshowDialogView(context);
                break;

        }
    }
    @JavascriptInterface
    public void  intentWebBrowser(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
