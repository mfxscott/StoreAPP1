package com.xianhao365.o2o.activity;

import android.app.Application;

/**
 * des
 *
 * @author mfx  17/2/21 16:33
 * 深圳市优讯信息技术有限公司
 */

public class MyApplication extends Application{

    private  String httpId = "https://www.sanxiapay.com/";
    /**
     * 接口请求
     */
    private  String HTTPURL =httpId+"wscommon/ESB/UXUNDBF/UXUNCOMMONFRONT/common";
    /**
     * 中转地址
     */
    private  String INTENTURL=httpId+"emallapp/ccqtgb/ccqtgbUrl.do";
    /**
     * 测试web跳转链接
     */
    private  String webUrlTest = "emallapp/pages/finanz/finance.jsp";

    public String getHttpId() {
        return httpId;
    }

    public void setHttpId(String httpId) {
        this.httpId = httpId;
    }

    public String getHTTPURL() {
        return HTTPURL;
    }

    public void setHTTPURL(String HTTPURL) {
        this.HTTPURL = HTTPURL;
    }

    public String getWebUrlTest() {
        return webUrlTest;
    }

    public void setWebUrlTest(String webUrlTest) {
        this.webUrlTest = webUrlTest;
    }

    public String getINTENTURL() {
        return INTENTURL;
    }

    public void setINTENTURL(String INTENTURL) {
        this.INTENTURL = INTENTURL;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        // Normal app init code...
    }
}
