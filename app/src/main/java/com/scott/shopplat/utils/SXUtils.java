package com.scott.shopplat.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
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

import com.scott.shopplat.R;
import com.scott.shopplat.activity.MyApplication;
import com.scott.shopplat.utils.dncry.wsc.AESEDncryption;
import com.scott.shopplat.utils.httpClient.AppClient;
import com.scott.shopplat.utils.httpClient.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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
     * @return
     */
    public  boolean isCameraCanUse() {
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
    public  void ToastCenter(String str) {
        Toast toast = Toast.makeText(mContext, TextUtils.isEmpty(str)?"连接服务器异常":str, Toast.LENGTH_SHORT);
        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        toast.setGravity(Gravity.CENTER, 0, 0);
        //屏幕居中显示，X轴和Y轴偏移量都是0
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static Dialog dialog ;
    public static LayoutInflater inflater;
    public static LinearLayout.LayoutParams params;
    /**
     * @param str
     * @param isBack  true可按返回取消
     */
    public static void showMyProgressDialog(Activity activity,String str,boolean isBack) {
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.common_loading, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.progressdialog_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tv_dialog_text);// 提示文字
        tipTextView.setText(str+"...");// 设置加载信息
        dialog = new Dialog(activity, R.style.common_progressloading_dialog);// 创建自定义样式dialog
        dialog.setCancelable(isBack);// 不可以用“返回键”取消
        dialog.setContentView(layout, params);// 设置布局
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }
    /**
     * @param isBack  true可按返回取消
     */
    public static void showMyProgressDialog(Activity activity,boolean isBack) {
        params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
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
        if(dialog == null){
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 弹出提示 确定
     * @param activity
     * @param title
     * @param contentStr
     */
    public  void ToastshowDialogView(Activity activity,String title,String contentStr){
//        if(tipDialog ==null) {
        final Dialog tipDialog = new AlertDialog.Builder(activity).create();
//        }
        tipDialog.setCancelable(true);
        tipDialog.setCanceledOnTouchOutside(false);
        if ( tipDialog.isShowing()) {
//                Msgialogg.show();
        }else{
            if(tipDialog != null)
                tipDialog.show();
        }
        Window window = tipDialog.getWindow();
        window.setContentView(R.layout.common_dialog);
        LinearLayout cancel = (LinearLayout) window.findViewById(R.id.addbankcard_dialog_cancel);
        TextView content = (TextView) window.findViewById(R.id.addbankcard_dialog_content);
        TextView  titletv = (TextView) window.findViewById(R.id.dialog_title_tv);
        TextView  oktv = (TextView) window.findViewById(R.id.pay_dialog_error_btn);
        View vi = window.findViewById(R.id.add_bank_dialog_view);
        vi.setVisibility(View.GONE);
        vi.setPadding(0,2,0,2);
        oktv.setText("确定");
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
     * @param context
     * @param url 跳转url
     * @return
     */
    public  String WebViewPostJSONObject(Context context,String url) {
        JSONObject jsonObject = new JSONObject();
        String encryptKey="";
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
            jsonObject.put("mobileuuid",getClientDeviceInfo());
            String json  = jsonObject.toString();
            Logs.i("web封装参数===="+json);AESEDncryption mAes = new AESEDncryption();

            try {
                encryptKey = mAes.encrypt(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "sxUrl="+encryptKey;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  encryptKey;
    }

    /**
     * 提示信息
     * @param intstr 文字
     */
    public  void ToastCenter(int intstr) {
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
     * @return
     */
    public MyApplication getApp() {
        return ((MyApplication)mContext.getApplicationContext());
    }
    public void setSharePreferences(String key,String value){
        SharedPreferences sharedPreferences= mContext.getSharedPreferences("sxsc",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(key, value);
        //提交当前数据
        editor.apply();
    }
    public String getSharePreferences(String key){
        SharedPreferences sharedPreferences= mContext.getSharedPreferences("sxsc",
                Activity.MODE_PRIVATE);
        String userId=sharedPreferences.getString(key,"0");
        return userId;
    }
    /**
     * 判断密码输入是否包含数字和密码
     * @param str
     * @return true  包含
     */
    public boolean testPsd(String str) {
        boolean isDigit=false;
        boolean isLetter=false;
        for (int i = 0; i < str.length(); i++) { //循环遍历字符串
            if (Character.isDigit(str.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if(isDigit == true && isLetter == true){
            return true;
        }
        return false;
    }
    /**
     * 得到设备信息
     * @return
     */
    public  String getClientDeviceInfo() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
        } catch (Exception e) {
            Logs.e("TAG", "get the system sn ERROR!", e);
        }
        String buildVersion = android.os.Build.VERSION.RELEASE;
        return ""  + buildVersion + "";
        //		return deviceID + "|android" + "|android|" + buildVersion + "|android";
    }
    /**
     *
     * 渠道标志为： 1，andriod（a）
     * @Description
     * @return
     */
    public  String getDeviceId() {
        String imei="";
        // 渠道标志 a
        try {
            // IMEI（imei）
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
             imei = "imei"+tm.getDeviceId();
            return ""+imei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logs.d("getDeviceId : ", imei.toString());
        return imei.toString();

    }
    /**
     * 获取系统版本号
     * @param
     * @author mfx
     * @throws Exception
     */
    public  String getVersionName()
    {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String version="1.0";
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    /**
     * 获取短信验证码
     * @param activity  上下文
     * @param mobile  手机号
     * @param type    (1=登录,2=注册,3=忘记密码,4=安全密码)
     * @param handler 回调
     */
    public void getCodeMsgHttp(Activity activity,String mobile,String type,final Handler handler){
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("type", type)//拉取类型(1=登录,2=注册,3=忘记密码,4=安全密码)
                .build();
        new OKManager(activity).sendStringByPostMethod(requestBody, AppClient.GET_CODEMSG, new OKManager.Func4() {
            @Override
            public void onResponse(Object jsonObject) {
                Logs.i("验证码发送成功返回参数=======",jsonObject.toString());
                Message msg = new Message();
                msg.what = AppClient.GETCODEMSG;
                msg.obj = jsonObject;
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
    }
}
