package com.xianhao365.o2o.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.R;
import com.xianhao365.o2o.activity.MyApplication;
import com.xianhao365.o2o.fragment.MainFragmentActivity;
import com.xianhao365.o2o.utils.dncry.wsc.AESEDncryption;
import com.xianhao365.o2o.utils.httpClient.AppClient;
import com.xianhao365.o2o.utils.httpClient.HttpUtils;
import com.xianhao365.o2o.utils.httpClient.OKManager;
import com.xianhao365.o2o.utils.view.SwipyRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.xianhao365.o2o.utils.httpClient.RequestReqMsgData.getReqsn;

/**
 * des
 *
 * @author mfx  17/2/22 17:40
 *         深圳市优讯信息技术有限公司
 */
public class SXUtils {
    // 定义私有构造方法（防止通过 new SingletonTest()去实例化）
    // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
    // 定义一个静态的方法（调用时再初始化SingletonTest，但是多线程访问时，可能造成重复初始化问题）
    private static SXUtils mInstance;
    private Context mContext;

    private SXUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SXUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SXUtils.class) {
                if (mInstance == null) {
                    mInstance = new SXUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 判断当前摄像头能否被使用（摄像头权限是否开启）
     *
     * @return
     */
    public boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
    /**
     * @param context
     * @param str   提示文字
     */
//    public  void ToastCenter(Context context, String str) {
//                try {
////                    Utils.getInstance(.ToastshowDialogView((Activity) context, "温馨提示", str + "");
//                }catch (Exception e){
//                    Logs.i("弹出提示异常信息","==="+e.toString());
//                }
//            }

    /**
     * 提示信息
     */
    public void ToastCenter(String str) {
        Toast toast = Toast.makeText(mContext, TextUtils.isEmpty(str) ? "连接服务器异常" : str, Toast.LENGTH_SHORT);
        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        toast.setGravity(Gravity.CENTER, 0, 0);
        //屏幕居中显示，X轴和Y轴偏移量都是0
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static Dialog dialog;
    public static LayoutInflater inflater;
    public static LinearLayout.LayoutParams params;

    /**
     * @param str
     * @param isBack true可按返回取消
     */
    public static void showMyProgressDialog(Activity activity, String str, boolean isBack) {
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.common_loading, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.progressdialog_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_dialog_text);// 提示文字
        tipTextView.setText(str + "...");// 设置加载信息
        dialog = new Dialog(activity, R.style.common_progressloading_dialog);// 创建自定义样式dialog
        dialog.setCancelable(isBack);// 不可以用“返回键”取消
        dialog.setContentView(layout, params);// 设置布局
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * @param isBack true可按返回取消
     */
    public static void showMyProgressDialog(Context activity, boolean isBack) {
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.common_loading, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.progressdialog_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_dialog_text);// 提示文字
        tipTextView.setText("");// 设置加载信息
        dialog = new Dialog(activity, R.style.common_progressloading_dialog);// 创建自定义样式dialog
        dialog.setCancelable(isBack);// 不可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(layout, params);// 设置布局
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 取消加载匡
     *
     * @param
     * @author mfx
     */
    public static void DialogDismiss() {
        if (dialog == null) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 弹出提示 确定
     *
     * @param title
     * @param contentStr
     */
    public void ToastshowDialogView(Context activity, String title, String contentStr) {
        final Dialog tipDialog = new AlertDialog.Builder(activity).create();
        tipDialog.show();
        tipDialog.setCancelable(true);
        tipDialog.setCanceledOnTouchOutside(false);
        Window window = tipDialog.getWindow();
        window.setContentView(R.layout.common_dialog);
        LinearLayout cancel = (LinearLayout) window.findViewById(R.id.dialog_liny);
        TextView content = (TextView) window.findViewById(R.id.dialog_message_tv);
        TextView titletv = (TextView) window.findViewById(R.id.dialog_title_tv);
        TextView rightBtn = (TextView) window.findViewById(R.id.dialog_right_btn);
        TextView leftBtn = (TextView) window.findViewById(R.id.dialog_right_btn);
        View vi = window.findViewById(R.id.add_bank_dialog_view);
        vi.setVisibility(View.GONE);
        vi.setPadding(0, 2, 0, 2);
        leftBtn.setText("确定");
        titletv.setText(title);
        ImageView iv = (ImageView) window.findViewById(R.id.dialog_close_iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.dismiss();
            }
        });
        content.setText(contentStr);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
            }
        });
    }

//    public void  setSysStatusBar(Activity activity,int color){
////        setTranslucentStatus(activity);
//
//        SystemBarTintManager tintManager=new SystemBarTintManager(activity);
//        tintManager.setStatusBarTintResource(color);
//        tintManager.setStatusBarTintEnabled(true);
//    }

    /**
     * webview 请求post参数进行加密传输
     *
     * @param context
     * @param url     跳转url
     * @return
     */
    public String WebViewPostJSONObject(Context context, String url) {
        JSONObject jsonObject = new JSONObject();
        String encryptKey = "";
        try {
//            jsonObject.put("latitude", AppClient.LATITUDE);
//            jsonObject.put("longitude", AppClient.LONGITUDE);
//            jsonObject.put("memberno", AppClient.MEMBERNO);
            jsonObject.put("channel", "APP");
//            jsonObject.put("tokenstr", AppClient.TOKENSTR);
//            jsonObject.put("loginname", AppClient.PHONENO);
            jsonObject.put("gourl", url);
//            jsonObject.put("customerid",AppClient.CUSTOMERSID);
//            jsonObject.put("user_id",AppClient.USERID);
            jsonObject.put("mobileuuid", getClientDeviceInfo());
            String json = jsonObject.toString();
            Logs.i("web封装参数====" + json);
            AESEDncryption mAes = new AESEDncryption();

            try {
                encryptKey = mAes.encrypt(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "sxUrl=" + encryptKey;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return encryptKey;
    }

    /**
     * 提示信息
     *
     * @param intstr 文字
     */
    public void ToastCenter(int intstr) {
        Toast toast = Toast.makeText(mContext, intstr, Toast.LENGTH_SHORT);
        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        toast.setGravity(Gravity.CENTER, 0, 0);
        //屏幕居中显示，X轴和Y轴偏移量都是0
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 保持状态值
     * 得到application对象
     *
     * @return
     */
    public MyApplication getApp() {
        return ((MyApplication) mContext.getApplicationContext());
    }

    public void setSharePreferences(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sxsc",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(key, value);
        //提交当前数据
        editor.apply();
    }

    public String getSharePreferences(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sxsc",
                Activity.MODE_PRIVATE);
        String userId = sharedPreferences.getString(key, "0");
        return userId;
    }

    public void removeSharePreferences(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sxsc",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 判断密码输入是否包含数字和密码
     *
     * @param str
     * @return true  包含
     */
    public boolean testPsd(String str) {
        boolean isDigit = false;
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) { //循环遍历字符串
            if (Character.isDigit(str.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if (isDigit == true && isLetter == true) {
            return true;
        }
        return false;
    }

    /**
     * 得到设备信息
     *
     * @return
     */
    public String getClientDeviceInfo() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
        } catch (Exception e) {
            Logs.e("TAG", "get the system sn ERROR!", e);
        }
        String buildVersion = android.os.Build.VERSION.RELEASE;
        return "" + buildVersion + "";
        //		return deviceID + "|android" + "|android|" + buildVersion + "|android";
    }

    /**
     * 渠道标志为： 1，andriod（a）
     *
     * @return
     * @Description
     */
    public String getDeviceId() {
        String imei = "";
        // 渠道标志 a
        try {
            // IMEI（imei）
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            imei = "imei" + tm.getDeviceId();
            return "" + imei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logs.d("getDeviceId : ", imei.toString());
        return imei.toString();

    }

    /**
     * 获取系统版本号
     *
     * @param
     * @throws Exception
     * @author mfx
     */
    public String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String version = "1.0";
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取短信验证码
     *
     * @param activity 上下文
     * @param mobile   手机号
     * @param type     (1=登录,2=注册,3=忘记密码,4=安全密码)
     * @param handler  回调
     */
    public void getCodeMsgHttp(Activity activity, String mobile, String type, final Handler handler) {
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("type", type)//拉取类型(1=登录,2=注册,3=忘记密码,4=安全密码)
                .build();
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.GET_CODEMSG, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("验证码发送成功返回参数=======", jsonObject.toString());
                Message msg = new Message();
                msg.what = AppClient.GETCODEMSG;
                msg.obj = jsonObject + "";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponseError(String strError) {
                Message msg = new Message();
                msg.what = AppClient.ERRORCODE;
                msg.obj = strError;
                handler.sendMessage(msg);
            }
        });
//        HttpParams params = new HttpParams();
//        params.put("mobile", mobile);
//        params.put("type", type);//拉取类型(1=登录,2=注册,3=忘记密码,4=安全密码)
//        HttpUtils.getInstance(activity).requestPost(false,AppClient.GET_CODEMSG, params, new HttpUtils.requestCallBack() {
//            @Override
//            public void onResponse(Object jsonObject) {
//                Logs.i("验证码发送成功返回参数=======", jsonObject.toString());
//                Message msg = new Message();
//                msg.what = AppClient.GETCODEMSG;
//                msg.obj = jsonObject + "";
//                handler.sendMessage(msg);
//            }
//            @Override
//            public void onResponseError(String strError) {
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = strError;
//                handler.sendMessage(msg);
//
//            }
//        });



    }

    /**
     * 公共fragment 跳转
     *
     * @param fm       FragmentManager
     * @param fragment 跳转fragment
     * @param tag      标签
     * @param bundle   传递数据
     */
    public void CommonFragment(FragmentManager fm, Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);
        if (fm != null) {
            fm.beginTransaction()
                    .add(R.id.my_content, fragment, tag)
                    .addToBackStack(null).commit();
//            utils.addFragmentTag(tag);
        }
    }

    /**
     * 已经是最后一个fragmentgetSupportFragmentManager()或者getFragmentManager()
     * 具体要看你add to back stack 是用哪个
     * fragmeng 返回一级级返回
     *
     * @param
     * @param activity
     * @author mfx
     */
    public void FragmentGoBack(Activity activity) {
        int num = activity.getFragmentManager().getBackStackEntryCount();
        //实名认证成功
        Fragment realAuthfinsh = activity.getFragmentManager().findFragmentByTag("realAuthfinsh");
        //上传银行成功
        Fragment uploadCardimgfinish = activity.getFragmentManager().findFragmentByTag("realNameUploadFinish");
        //支付成功点击返回关闭所有回到个人中心
        Fragment payresult = activity.getFragmentManager().findFragmentByTag("payresult");

//添加银行卡成功
        Fragment addbanksuc = activity.getFragmentManager().findFragmentByTag("addbankcardsucceed");

        Fragment realauthone = activity.getFragmentManager().findFragmentByTag("real1");
        Fragment realauthTwo = activity.getFragmentManager().findFragmentByTag("realAuthTwo");
        Fragment realNameBindCard = activity.getFragmentManager().findFragmentByTag("realNameBindCard");
        Fragment uploadcard = activity.getFragmentManager().findFragmentByTag("uploadcard");

        if (realauthTwo != null || realauthone != null || realNameBindCard != null || uploadcard != null) {
            activity.finish();
            return;
        }
        //等于1为第一个界面
        if (num == 1) {
            activity.finish();
        } else {
            activity.getFragmentManager().popBackStack();
        }
//        }

    }

    /**
     * 获取系统时间
     *
     * @return
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String GetNowDateTime() {
        return sdf.format(new Date());
    }


    public void setColorSchemeResources(SwipyRefreshLayout mSwipyRefreshLayout) {
        mSwipyRefreshLayout.setColorSchemeResources(R.color.qblue, R.color.blue, R.color.red);
    }

    /**
     * 获取header请求参数
     *
     * @param rspMsgName 方法名
     * @return
     */
    public HttpHeaders GetheadData(String rspMsgName) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-App-Key", "xianhao365");
        headers.put("X-Method", rspMsgName);
        headers.put("X-Timestamp", SXUtils.getInstance(mContext).GetNowDateTime());
        headers.put("X-Version", "1.0");
        headers.put("X-User-ID", TextUtils.isEmpty(AppClient.USER_ID) ? "" : AppClient.USER_ID);
        headers.put("X-User-Session", TextUtils.isEmpty(AppClient.USER_SESSION) ? "" : AppClient.USER_SESSION);
        headers.put("X-OS", "Android");
        headers.put("X-OS-Version", SXUtils.getInstance(mContext).getClientDeviceInfo());
        headers.put("X-App-Version", SXUtils.getInstance(mContext).getVersionName());
        headers.put("X-UDID", SXUtils.getInstance(mContext).getDeviceId());
        headers.put("X-Nonce", getReqsn());
        return headers;
    }

    /**
     * 公共调用图片装置
     *
     * @param imgUrl
     * @param view
     */
    public void GlideSetImg(String imgUrl, ImageView view) {
        Glide.with(mContext)
                .load(imgUrl)
                .placeholder(R.mipmap.loading_img)
                .error(R.mipmap.load_error)
                .fitCenter()
                .into(view);
    }

    //删除文件夹和文件夹里面的文件
    public void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 添加或者删除购物车
     *
     * @param skuBarcode SKU条码
     * @param quantity   更新数量（0=删除，X=设值）
     * @param
     */
    public void AddOrUpdateCar(String skuBarcode, final String quantity) {
        HttpParams params = new HttpParams();
        params.put("skuBarcode", skuBarcode + "");
        params.put("quantity", quantity + "");
        HttpUtils.getInstance(mContext).requestPost(false, AppClient.CARADDUPDATA, params, new HttpUtils.requestCallBack() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("购物车成功返回参数=======", jsonObject.toString());
//                JSONObject jsonObject1 = null;
//                CarList car = (CarList) ResponseData.getInstance(mContext).parseJsonWithGson(jsonObject.toString(), CarList.class);
//                Message msg = new Message();
//                msg.what = AppClient.ADDDELCAR;
//                msg.obj = car;
//                hand.sendMessage(msg);
                if(quantity.equals(0)){
                    MainFragmentActivity.getInstance().setBadge(false,1);
                }else{
                    MainFragmentActivity.getInstance().setBadge(true,1);
                }

            }
            @Override
            public void onResponseError(String strError) {
//                Message msg = new Message();
//                msg.what = AppClient.ERRORCODE;
//                msg.obj = strError;
//                hand.sendMessage(msg);

            }
        });
    }
}