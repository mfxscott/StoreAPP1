package com.xianhao365.o2o.entity.bill;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NN on 2017/9/19.
 */

public class BillChirdrenEntity implements Parcelable{
    private String marketPrice;
    private String skuBarcode;
    private String shopPrice;
    private String goodsModel;

    protected BillChirdrenEntity(Parcel in) {
        marketPrice = in.readString();
        skuBarcode = in.readString();
        shopPrice = in.readString();
        goodsModel = in.readString();
    }

    public static final Creator<BillChirdrenEntity> CREATOR = new Creator<BillChirdrenEntity>() {
        @Override
        public BillChirdrenEntity createFromParcel(Parcel in) {
            return new BillChirdrenEntity(in);
        }

        @Override
        public BillChirdrenEntity[] newArray(int size) {
            return new BillChirdrenEntity[size];
        }
    };

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marketPrice);
        dest.writeString(skuBarcode);
        dest.writeString(shopPrice);
        dest.writeString(goodsModel);
    }
}
