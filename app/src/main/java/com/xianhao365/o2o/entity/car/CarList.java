package com.xianhao365.o2o.entity.car;

import java.util.List;

/**
 * Created by NN on 2017/9/7.
 */

public class CarList {
    private String  grandTotal;
    private String  discountAmount;
    private String  freightAmount;
    private List<ShoppingCartLinesEntity>  shoppingCartLines;
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

    public List<ShoppingCartLinesEntity> getShoppingCartLines() {
        return shoppingCartLines;
    }

    public void setShoppingCartLines(List<ShoppingCartLinesEntity> shoppingCartLines) {
        this.shoppingCartLines = shoppingCartLines;
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
