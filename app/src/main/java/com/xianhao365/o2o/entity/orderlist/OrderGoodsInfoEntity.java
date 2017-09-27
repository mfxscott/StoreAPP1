package com.xianhao365.o2o.entity.orderlist;

/**
 * 订单中的商品信息
 * Created by NN on 2017/9/27.
 */

public class OrderGoodsInfoEntity {
    private String id;
    private String goodsCode;
    private String skuCode;
    private String skuName;
    private String skuImage;
    private String skuPrice;
    private String  skuNumber;
    private String totalAmount;
    private String goodsUnit;
    private String removable;
    private String editable;
    private String addiable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public String getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(String skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getRemovable() {
        return removable;
    }

    public void setRemovable(String removable) {
        this.removable = removable;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getAddiable() {
        return addiable;
    }

    public void setAddiable(String addiable) {
        this.addiable = addiable;
    }
}
