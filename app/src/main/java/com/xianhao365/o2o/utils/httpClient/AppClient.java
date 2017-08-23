package com.xianhao365.o2o.utils.httpClient;

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

    public static final  int GETCODEMSG = 1001;//获取短信验证码成功
    public static final  int ERRORCODE = 4040;//非正常数据返回码
    public static final  int UPDATEVER = 1002;//版本升级返回码
    public static  String iv="";
    public static  String SecretKey="";

    public static String USER_ID = "";//用户ID
    public static String USER_SESSION = ""; //用户会话
    /**
     *  用户登录不同角色区分
     *  32 商户摊主
     *  64 个人用户
     *  用户标签，1:后台用户,2:城市采购中心,4:供应商,8:联创中心,16:合伙人,32:摊主店铺,64:消费者,128:供应商司机,256:采购中心司机
     */
    public static String USERROLETAG = "32";//tag 用户类型


    /**
     * 用户相关接口
     */
    public static String FORGET_PSD1 = "svc.forget.password1";//忘记密码步骤1
    public static String FORGET_PSD2 = "svc.forget.password2";//忘记密码步骤2
    public static String USER_LOGIN =  "svc.sign.in";//		用户登录
    public static String USER_REGIST = "svc.sign.up";	//	用户注册
    public static String USER_LOGINOUAt = "svc.sign.out";//		用户登出
    public static String GET_TOKEN = "svc.pull.token";//		拉取令牌
    public static String USER_INFO="svc.get.user.info";	//获取用户信息
    public static String  UPDATE_USER_INFO = "svc.update.user.base.info";//修改用户基本信息
    public static String  UPDATE_PASSWORD="";//修改用户密码信息
    public static String USER_ORDERS="svc.get.user.order.info";//获取用户订单信息
    public static String  USER_WALLET="svc.get.user.wallet.info";//获取用户钱包信息




    public static String GET_CODEMSG = "svc.pull.sms";//拉取短信
    public static String APP_UPDATE="svc.check.update";//app版本更新
    public static String APP_LAUNCH="svc.launch.image";//启动广告图
    public static String APP_SWIPER="svc.swiper";//轮播广告图
    public static String STORE_LOCATION="svc.shop.location";//查询门店地理位置
    public static String HOTSEARCH = "svc.search.word";//热门搜索词汇 svc.search.word --svc.search.keyword
    //商品信息相关接口
    public static String GOODS_DETAIL="svc.goods.ById";//商品详情
    public static String GOODS_TYPE="svc.cat.list";//商品分类
    public static String GOODS_LIST="svc.goods.list";//商品列表 根据参数进行分类查询



   public static String headImg  = "http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=0197b59000087bf47db95fedc7e37b1a/38dbb6fd5266d016152614f3952bd40735fa3529.jpg";


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


}