package com.xianhao365.o2o.entity.cgListInfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mfx-t224 on 2017/9/1.
 * 采购列表中订单详细商品列表
 */

public class CGPurchaseLinesEntity implements Parcelable {
    private String id;
    private String created;
    private String skuCode;//sku编码
    private String thumbImg;//商品图片
    private String goodsPrice;//商品采购价
    private String goodsUnit;//商品重量/单位/
    private String goodsName;//商品名称
    private String goodsCode;//商品编码
    private String totalAmount;//订单总价
    private String goodsNumber;//商品价格

    protected CGPurchaseLinesEntity(Parcel in) {
        id = in.readString();
        created = in.readString();
        skuCode = in.readString();
        thumbImg = in.readString();
        goodsPrice = in.readString();
        goodsUnit = in.readString();
        goodsName = in.readString();
        goodsCode = in.readString();
        totalAmount = in.readString();
        goodsNumber = in.readString();
        actualNumber = in.readString();
    }

    public static final Creator<CGPurchaseLinesEntity> CREATOR = new Creator<CGPurchaseLinesEntity>() {
        @Override
        public CGPurchaseLinesEntity createFromParcel(Parcel in) {
            return new CGPurchaseLinesEntity(in);
        }

        @Override
        public CGPurchaseLinesEntity[] newArray(int size) {
            return new CGPurchaseLinesEntity[size];
        }
    };

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(String actualNumber) {
        this.actualNumber = actualNumber;
    }

    private String actualNumber;//实际商品价格

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(created);
        dest.writeString(skuCode);
        dest.writeString(thumbImg);
        dest.writeString(goodsPrice);
        dest.writeString(goodsUnit);
        dest.writeString(goodsName);
        dest.writeString(goodsCode);
        dest.writeString(totalAmount);
        dest.writeString(goodsNumber);
        dest.writeString(actualNumber);
    }
}
