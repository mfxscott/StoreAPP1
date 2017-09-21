package com.xianhao365.o2o.entity.bill;

import android.os.Parcel;

import java.util.List;

/**
 * Created by NN on 2017/9/19.
 */
public class BillDataSetEntity{
    private String categoryName;
    private String categoryId;
    private String categoryCode;
    private List<CategoryListEntity> categoryList;

    protected BillDataSetEntity(Parcel in) {
        categoryName = in.readString();
        categoryId = in.readString();
        categoryCode = in.readString();
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CategoryListEntity> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListEntity> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

}
