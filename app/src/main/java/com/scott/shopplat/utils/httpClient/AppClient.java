package com.scott.shopplat.utils.httpClient;

import android.os.Environment;

/**
 * ***************************
 * 公共资源
 * @author mfx
 * 深圳市优讯信息技术有限公司
 * 16/10/28 下午3:50
 * ***************************
 */
public class AppClient{
//        public static String HTTPIP = "https://www.sanxiapay.com/";//三峡付生产环境
//        public static String HTTPIP="http://10.64.9.16:80/";//银行系统环境
    //    public static String HTTPIP="http://10.64.9.27:80/";//银行环境克隆ip批量财富存
//    public static String HTTPIP = "http://118.178.248.237:9080/";//2公司云服务
//    public static String HTTPIP="http://10.64.2.88:9080/";//开发测试


    public static final  int GETCODEMSG = 1001;//获取短信验证码成功
    public static final  int ERRORCODE = 4040;//非正常数据返回码
    public static final  int UPDATEVER = 1002;//版本升级返回码
    public static final  int AUTHINFO = 1004;//查询实名认证信息


//    /**
//     * 接口请求
//     */
//    public static String HTTPURL =getHTTPIP()+"wscommon/ESB/UXUNDBF/UXUNCOMMONFRONT/common";
//    /**
//     * 中转地址
//     */
//    public static String INTENTURL=getHTTPIP()+"emallapp/ccqtgb/ccqtgbUrl.do";
//    /**
//     * 测试web跳转链接
//     */
//    public static String webUrlTest = "emallapp/pages/finanz/finance.jsp";
    public static  String iv="";
    public static  String SecretKey="";

    public static String USER_ID = "";//用户ID
    public static String USER_SESSION = ""; //用户会话
    /**
     *  用户登录不同角色区分
     *  32 商户摊主
     *  64 个人用户
     */
    public static String TAG = "32";//tag 用户类型
    public static String USER_LOGIN =  "svc.sign.in";//		用户登录
    public static String USER_LOGINOUAt = "svc.sign.out";//		用户登出
    public static String GET_TOKEN = "svc.pull.token";//		拉取令牌
    public static String GET_CODEMSG = "svc.pull.sms";//		拉取短信
    public static String FORGET_PSD1 = "svc.forget.password1";//		忘记密码步骤1
    public static String FORGET_PSD2 = "svc.forget.password2";//		忘记密码步骤2
    public static String USER_REGIST = "svc.sign.up";	//	用户注册
    public static String APP_UPDATE="svc.check.update";//app版本更新
    public static String APP_LAUNCH="svc.launch.image";//启动广告图
    public static String APP_SWIPER="svc.swiper";//轮播广告图
    public static String STORE_LOCATION="svc.shop.location";//查询门店地理位置




    public static int  fullWidth; //屏幕宽，高
    public static int  fullHigh;
    // 返回表示跳转指定目录
    public static boolean TAG1=false;//首页
    public static boolean TAG2=false;//所有菜品
    public static boolean TAG3=false;//购物清单
    public static boolean TAG4=false;//购物车
    public static boolean TAG5=false;//我的
    public static final String CACHDATAPATH = getSDPath() + "/json/cacheData";//缓存json数据路径
    public static String MAINBANNER = "/main.txt";//首页广告内容缓存文件名

    // 获取根目录路径
    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        // 如果有sd卡，则返回sd卡的目录
        if (hasSDCard){
            return Environment.getExternalStorageDirectory().getPath();
        } else
            // 如果没有sd卡，则返回存储目录
            return Environment.getDownloadCacheDirectory().getPath();
    }

//    public static String getHTTPIP() {
//        return HTTPIP;
//    }

}