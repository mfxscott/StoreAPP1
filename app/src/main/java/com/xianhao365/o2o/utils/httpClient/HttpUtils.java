package com.xianhao365.o2o.utils.httpClient;

import android.content.Context;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.xianhao365.o2o.utils.Logs;
import com.xianhao365.o2o.utils.SXUtils;

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
    public void requestPost(final requestCallBack callBack){
        OkHttpUtils.post(SXUtils.getInstance(mContext).getApp().getHttpUrl())
                .tag(this)
//                .params(httpParams)
//                .requestBody()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logs.i("=======onError=="+e.toString());
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
