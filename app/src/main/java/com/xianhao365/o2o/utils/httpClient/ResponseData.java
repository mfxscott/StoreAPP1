package com.xianhao365.o2o.utils.httpClient;


import android.content.Context;

import com.xianhao365.o2o.entity.GoodsTypeEntity;
import com.xianhao365.o2o.utils.Logs;
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
    /**
     * 首页商品一，而级分类
     * @param jsonobj  返回数据
     * @return
     */
    public static List<GoodsTypeEntity> getGoodsTypeData(Object jsonobj) throws JSONException {
        List<GoodsTypeEntity> goodsTypeList = new ArrayList<GoodsTypeEntity>();
        List<GoodsTypeEntity> childrengoodsTypeList= null;
        GoodsTypeEntity goodsType;
        Logs.i("解析返回json====="+jsonobj);
        JSONObject aaa = (JSONObject) jsonobj;


//            Object isobj = msgRspJson.get("localSearchGoods");
        if (jsonobj instanceof JSONArray) {
                JSONArray arr = (JSONArray) jsonobj;
                for (int i = 0; i < arr.length(); i++) {
                    goodsType = new GoodsTypeEntity();
                    JSONObject objdetail = arr.getJSONObject(i);
                    goodsType.setCatNo(objdetail.getString("catNo"));
                    goodsType.setName(objdetail.getString("name"));
                    goodsType.setParentId(objdetail.getString("parentId"));
                    goodsType.setId(objdetail.getString("id"));
                       if(objdetail.get("children") != null){
                           childrengoodsTypeList = new ArrayList<GoodsTypeEntity>();
                           GoodsTypeEntity CgoodsType;
                           JSONArray childrenarr = objdetail.getJSONArray("children");
                           for (int c = 0; c < childrenarr.length(); c++) {
                               CgoodsType = new GoodsTypeEntity();
                               JSONObject Cobjdetail = childrenarr.getJSONObject(c);
                               CgoodsType.setCatNo(Cobjdetail.getString("catNo"));
                               CgoodsType.setName(Cobjdetail.getString("name"));
                               CgoodsType.setParentId(Cobjdetail.getString("parentId"));
                               CgoodsType.setId(Cobjdetail.getString("id"));
                               childrengoodsTypeList.add(CgoodsType);
                           }
                       }
                    goodsType.setGoodsTypeList(childrengoodsTypeList);
                    goodsTypeList.add(goodsType);
                }
            } else if(jsonobj instanceof JSONObject){
                JSONObject objdetail = (JSONObject) jsonobj;
                goodsType = new GoodsTypeEntity();
                goodsType.setCatNo(objdetail.getString("catNo"));
                goodsType.setName(objdetail.getString("name"));
                goodsType.setParentId(objdetail.getString("parentId"));
                goodsType.setId(objdetail.getString("id"));

                if(objdetail.get("children") != null){
                    childrengoodsTypeList = new ArrayList<GoodsTypeEntity>();
                    GoodsTypeEntity CgoodsType;
                    JSONArray childrenarr = objdetail.getJSONArray("children");
                    for (int c = 0; c < childrenarr.length(); c++) {
                        CgoodsType = new GoodsTypeEntity();
                        JSONObject Cobjdetail = childrenarr.getJSONObject(c);
                        CgoodsType.setCatNo(Cobjdetail.getString("catNo"));
                        CgoodsType.setName(Cobjdetail.getString("name"));
                        CgoodsType.setParentId(Cobjdetail.getString("parentId"));
                        CgoodsType.setId(Cobjdetail.getString("id"));
                        childrengoodsTypeList.add(CgoodsType);
                    }
                }
                goodsType.setGoodsTypeList(childrengoodsTypeList);
                goodsTypeList.add(goodsType);
            }
            return goodsTypeList;
    }
}
