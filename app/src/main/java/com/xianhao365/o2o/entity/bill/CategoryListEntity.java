package com.xianhao365.o2o.entity.bill;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by NN on 2017/9/19.
 */

public class CategoryListEntity implements Parcelable {
    private String  goodsUnit;
    private String id;
    private String goodsNickname;
    private String goodsCode;
    private String goodsName;
    private String originalImg;
    private List<BillChirdrenEntity> chirdren;

    protected CategoryListEntity(Parcel in) {
        goodsUnit = in.readString();
        id = in.readString();
        goodsNickname = in.readString();
        goodsCode = in.readString();
        goodsName = in.readString();
        originalImg = in.readString();
        chirdren = in.createTypedArrayList(BillChirdrenEntity.CREATOR);
        children = in.createTypedArrayList(BillChirdrenEntity.CREATOR);
    }

    public static final Creator<CategoryListEntity> CREATOR = new Creator<CategoryListEntity>() {
        @Override
        public CategoryListEntity createFromParcel(Parcel in) {
            return new CategoryListEntity(in);
        }

        @Override
        public CategoryListEntity[] newArray(int size) {
            return new CategoryListEntity[size];
        }
    };

    public List<BillChirdrenEntity> getChirdren() {
        return chirdren;
    }

    public void setChirdren(List<BillChirdrenEntity> chirdren) {
        this.chirdren = chirdren;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    private List<BillChirdrenEntity> children;

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsNickname() {
        return goodsNickname;
    }

    public void setGoodsNickname(String goodsNickname) {
        this.goodsNickname = goodsNickname;
    }

    public List<BillChirdrenEntity> getChildren() {
        return children;
    }

    public void setChildren(List<BillChirdrenEntity> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsUnit);
        dest.writeString(id);
        dest.writeString(goodsNickname);
        dest.writeString(goodsCode);
        dest.writeString(goodsName);
        dest.writeString(originalImg);
        dest.writeTypedList(chirdren);
        dest.writeTypedList(children);
    }
}
