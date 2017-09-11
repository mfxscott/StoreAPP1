package com.xianhao365.o2o.entity.car;

import java.util.List;

/**
 * Created by NN on 2017/9/7.
 */

public class CarList {
    private String  grandTotal;
    private String  discountAmount;//折扣金额
    private String  freightAmount;  //运费金额
    private List<ShoppingListEntity> shoppingList;
    private List<TakeNoPartInActivitiesEntity>  takeNoPartInActivities;
    private List<TakePartInActivitiesEntity>  takePartInActivities;

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        this.freightAmount = freightAmount;
    }

    public List<ShoppingListEntity> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingListEntity> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<TakeNoPartInActivitiesEntity> getTakeNoPartInActivities() {
        return takeNoPartInActivities;
    }

    public void setTakeNoPartInActivities(List<TakeNoPartInActivitiesEntity> takeNoPartInActivities) {
        this.takeNoPartInActivities = takeNoPartInActivities;
    }

    public List<TakePartInActivitiesEntity> getTakePartInActivities() {
        return takePartInActivities;
    }

    public void setTakePartInActivities(List<TakePartInActivitiesEntity> takePartInActivities) {
        this.takePartInActivities = takePartInActivities;
    }
}
