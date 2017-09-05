package com.xianhao365.o2o.utils.httpClient;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * des
 * @author mfx
 * @time  2017/8/23 10:38
 */
public class HttpUtils{
    private static HttpUtils mInstance;
    private Context mContext;

    private HttpUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }
    public static HttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SXUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置请求头Head中的参数，传递方法名
     * @param method 方法名
     * @return  HttpHeaders
     */
    public HttpHeaders addHttpHeadData(String method){
        HttpHeaders httpHeaders =  SXUtils.getInstance(mContext).GetheadData(method);
        OkHttpUtils.getInstance().addCommonHeaders(httpHeaders);
        return httpHeaders;
    }

    /**
     * 封装请求和返回参数数据
     * @param method  方法名
     * @param httpParams 请求参数  没有参数传Null
     * @param callBack  结果回调
     * @param  isAll   是否返回所有数据
     */
    public void requestPost(final boolean isAll,final String method,HttpParams httpParams,final requestCallBack callBack){
        addHttpHeadData(method);
        HttpParams httpP =null;
        if(httpParams == null){
            httpP = new HttpParams();
        }else{
            httpP = httpParams;
            Logs.i(method+"==request=======",httpP.toString());
        }
        OkHttpUtils.post(SXUtils.getInstance(mContext).getApp().getHttpUrl())
                .tag(this)
                .params(httpP)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logs.i(method+"==success=======",s.toString());
                        String data="";
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String resultCode = jsonObject.getString("responseCode");
                            String resultText = jsonObject.getString("responseText");
                            data = jsonObject.getString("responseData");
                            if(TextUtils.isEmpty(resultCode) || !resultCode.equals("10000")){
                                callBack.onResponseError(resultText);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(isAll){
                            callBack.onResponse(s);
                        }else{
                            callBack.onResponse(data);
                        }
                        SXUtils.DialogDismiss();
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onResponseError(e.toString());

                    }
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        Logs.i(progress+"=====upProgress===");
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                    }
                });
    }
    /**
     * 请求带文字和图片接口上传
     * @param method  方法名
     * @param httpParams 请求参数  没有参数传Null
     * @param callBack  结果回调
     * @param  isAll   是否返回所有数据
     */
    public void requestUploadImgPost(final boolean isAll, final String method, HttpParams httpParams, final requestCallBack callBack){
        addHttpHeadData(method);
        OkHttpUtils.post(SXUtils.getInstance(mContext).getApp().getHttpUrl())
                .tag(this)
                .params(httpParams)
//                .addFileParams(fileKey,files)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logs.i(method+"==success=======",s.toString());
                        String data="";
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String resultCode = jsonObject.getString("responseCode");
                            String resultText = jsonObject.getString("responseText");
                            data = jsonObject.getString("responseData");
                            if(TextUtils.isEmpty(resultCode) || !resultCode.equals("10000")){
                                callBack.onResponseError(resultText);
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(isAll){
                            callBack.onResponse(s);
                        }else{
                            callBack.onResponse(data);
                        }
                        SXUtils.DialogDismiss();
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callBack.onResponseError(e.toString());

                    }
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        Logs.i(progress+"=====upProgress===");
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                    }
                });
    }
    public interface requestCallBack {
        void onResponse(Object jsonObject);
        void onResponseError(String strError);
    }
}
