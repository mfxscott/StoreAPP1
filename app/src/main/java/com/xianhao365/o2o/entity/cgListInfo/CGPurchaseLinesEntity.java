package com.xianhao365.o2o.entity.cgListInfo;

/**
 * Created by mfx-t224 on 2017/9/1.
 * 采购列表中订单详细商品列表
 */

public class CGPurchaseLinesEntity {
    private String id;
    private String actualNumber;
    private String skuCode;//sku编码
    private String goodsNumber;//商品数量
    private String purchaseCode;
    private String totalAmount;//商品总价
    private String thumbImg;//商品图片
    private String goodsPrice;//商品采购价
    private String goodsUnit;//商品重量/单位/
    private String goodsName;//商品名称
    private String goodsCode;//商品编码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
}
