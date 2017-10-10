package com.xianhao365.o2o.entity.orderlist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单中的商品信息
 * Created by NN on 2017/9/27.
 */

public class OrderGoodsInfoEntity implements Parcelable {
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

    protected OrderGoodsInfoEntity(Parcel in) {
        id = in.readString();
        goodsCode = in.readString();
        skuCode = in.readString();
        skuName = in.readString();
        skuImage = in.readString();
        skuPrice = in.readString();
        skuNumber = in.readString();
        totalAmount = in.readString();
        goodsUnit = in.readString();
        removable = in.readString();
        editable = in.readString();
        addiable = in.readString();
    }

    public static final Creator<OrderGoodsInfoEntity> CREATOR = new Creator<OrderGoodsInfoEntity>() {
        @Override
        public OrderGoodsInfoEntity createFromParcel(Parcel in) {
            return new OrderGoodsInfoEntity(in);
        }

        @Override
        public OrderGoodsInfoEntity[] newArray(int size) {
            return new OrderGoodsInfoEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(goodsCode);
        dest.writeString(skuCode);
        dest.writeString(skuName);
        dest.writeString(skuImage);
        dest.writeString(skuPrice);
        dest.writeString(skuNumber);
        dest.writeString(totalAmount);
        dest.writeString(goodsUnit);
        dest.writeString(removable);
        dest.writeString(editable);
        dest.writeString(addiable);
    }
}
