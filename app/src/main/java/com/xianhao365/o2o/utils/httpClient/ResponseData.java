package com.xianhao365.o2o.utils.httpClient;


import android.content.Context;

import com.xianhao365.o2o.utils.SXUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 解析返回数据
 * @time  2017/8/2 11:17
 */
public class ResponseData {
    private static ResponseData mInstance;
    private Context mContext;

    private ResponseData(Context context) {
        this.mContext = context.getApplicationContext();
    }
    public static ResponseData getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SXUtils.class) {
                if (mInstance == null) {
                    mInstance = new ResponseData(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 生活模块特产商品
     * @param jsonobj  返回数据
     * @return
     */
    public static List<Map<String,String>> getHotSearchData(String jsonobj) {
        List<Map<String,String>> spList = new ArrayList<Map<String,String>>();
        Map<String,String> hotSearch;
        try {
            Object isobj = jsonobj;
//            Object isobj = msgRspJson.get("localSearchGoods");
            if (isobj instanceof JSONArray) {
                JSONArray arr = (JSONArray) isobj;
                for (int i = 0; i < arr.length(); i++) {
                    hotSearch = new HashMap<>();
                    JSONObject objdetail = arr.getJSONObject(i);
//                    spinfo.setShopName(objdetail.getString("shopname"));
//                    spinfo.setGoodsImg(objdetail.getString("goodsimage"));
//                    spinfo.setMarketPrice(objdetail.getString("marketprice"));
//                    spinfo.setPrice(objdetail.getString("price_money"));
//                    spinfo.setGoodsId(objdetail.getString("goodsid"));
//                    spinfo.setGoodsName(objdetail.getString("goodsname"));
//                    spinfo.setSales(objdetail.getString("sales"));
                    spList.add(hotSearch);
                }
            } else {
                JSONObject objdetail = (JSONObject) isobj;
               hotSearch = new HashMap<>();
//                spinfo.setShopName(objdetail.getString("shopname"));
//                spinfo.setGoodsImg(objdetail.getString("goodsimage"));
//                spinfo.setMarketPrice(objdetail.getString("marketprice"));
//                spinfo.setPrice(objdetail.getString("price_money"));
//                spinfo.setGoodsId(objdetail.getString("goodsid"));
//                spinfo.setGoodsName(objdetail.getString("goodsname"));
//                spinfo.setSales(objdetail.getString("sales"));
                spList.add(hotSearch);
            }
            return spList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spList;
    }
}
