package com.xianhao365.o2o.utils.httpClient;

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
    public static final  int ADDDELCAR = 2001;//添加或者删除购物车成功
    /**
     * EventBus 返回接收码说明
     */
    public static final  int COUPONS_RETRURN=3001;//提交订单进入优惠券选择返回劵couponsNo
    public static  String iv="";
    public static  String SecretKey="";

    public static String USER_ID = "";//用户ID
    public static String USER_SESSION = ""; //用户会话
    public static String CUST_TELEPHONE="10010";//我的 客服电话号码
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
    public static String USER_ISPPLY_NFO="svc.serch.supply.info";	//获取供货商用户信息
    public static String USER_NUM_NFO="svc.user.center.render";//获取我的中 用户的订单数量
    public static String USER_REMIND="svc.order.remind";//用户订单列表提醒发货
    public static String USER_CANCEL_ORDER="svc.order.cancel";//用户取消订单
//收货地址
    public static String ADDRESS_ADD="svc.add.shipping.address";//		添加收货地址
    public static String ADDRESS_UPDATE="svc.mod.shipping.address";//	修改收货地址
    public static String ADDRESS_DEL="svc.del.shipping.address";//		删除收货地址
    public static String ADDRESS_SELECT="svc.my.shipping.address";//		我的收货地址
    //优惠券

    public static String COUPONS_USE="svc.usable.coupons";//未使用优惠券
    public static String COUPONS_NOUSE="svc.used.coupons";//已使用优惠券
    public static String COUPONS_PASSUSE="svc.expired.coupons";//已过期

    /**
     *订单相关接口
     */
    public static String ORDER_FORM="svc.order.form";//订单结算
    public static String ORDER_SUBMIT = "svc.submit.order";//提交订单
    public static String  ORDER_LIST= "svc.order.list";//订单列表
    public static String  ORDER_DETAIL= "svc.order.detail";//订单详情

    public static String  UPDATE_USER_INFO = "svc.update.user.base.info";//修改用户基本信息
    public static String  UPDATE_PASSWORD="";//修改用户密码信息
    public static String  UPDATEPAYPSD="svc.user.wallet.updatepwd";//修改支付密码
    public static String  SETPAYPSD="svc.user.wallet.setpwd";//设置支付密码
    public static String USER_ORDERS="svc.get.user.order.info";//获取用户订单信息

    public static String  GYS_BILLLIST ="svc.purchase.mypurchaselist";//供应商的采购单列表
    public static String  GYS_CONFIRM_PURCHASE ="svc.purchase.confirmPurchase";//供应商确认采购订单
    //钱包相关接口
    public static String  MYTRADELOG="svc.user.list.mytradelog";//我的钱包-收入明细
    public static String  USER_WALLET="svc.get.user.wallet.info";//获取用户钱包信息
    public static String  WITHDRAWAPPLY="svc.user.withdraw.apply";//提现申请
    public static String  ADDBANKCARD="svc.user.wallet.addcard";//添加银行卡
    public static String  UPDATE_STORE_INFO="svc.user.add.shop";//添加修改获取门店信息
    public static String  GET_BANKLIST="svc.user.list.bank";//获取开户行
    public static String  GET_CITYAREA="svc.user.area";//获取省市区

    public static String  GYS_CPURCHASE_DELIVER ="svc.purchase.deliverGoods";//供应商采购单发货

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
     //常用清单
    public static String  COMMONBILL="svc.common.goods.list";
    //购物车
    public static String CARLIST = "svc.shoppingcart.get";//查询购物车
    public static String CARADDUPDATA="svc.shoppingcart.update";//增加和删除购物车
    public static String CLEARCAR="svc.shoppingcart.empty";//清空购物车
    //摊主相关接口
    public static String TZ_ORDER_LIST="svc.shopowner.order.list";//摊主订单列表查询
    public static String ORDER_CONFIRM="svc.shopowner.order.confirm";//确认订单到货
    public static String STOCK_SUBMIT="svc.order.lessGoodsApply";//摊主缺货少货上报
    //合伙人
    public static String HHR_ORDER_LIST="svc.partner.order.list";//合伙人订单列表

    public static String headImg  = "http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=0197b59000087bf47db95fedc7e37b1a/38dbb6fd5266d016152614f3952bd40735fa3529.jpg";

    public static String PAY = "svc.settlement.order";//支付接口

    public static int  fullWidth; //屏幕宽，高
    public static int  fullHigh;
    // 返回表示跳转指定目录
    public static boolean TAG1=false;//首页
    public static boolean TAG2=false;//所有菜品
    public static boolean TAG3=false;//购物清单
    public static boolean TAG4=false;//购物车
    public static boolean TAG5=false;//我的
//    public static final String CACHDATAPATH = getSDPath() + "/json/cacheData";//缓存json数据路径
    public static String MAINBANNER = "/main.txt";//首页广告内容缓存文件名



}