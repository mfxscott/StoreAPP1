package com.xianhao365.o2o.entity.orderlist;

import java.util.List;

/**
 * 摊主普通订单列表
 * Created by NN on 2017/9/27.
 */

public class OrderInfoEntity {
    private String orderNo;
    private String states;
    private String vehicleNo;
    private String images;
    private String omsStatus;
    private String driverName;
    private String driverPhone;
    private String goodsTotalAmount;
    private String transactionAmount;
    private String partnerUserNo;
    private String partnerUserName;
    private String shopUserNo;
    private String shopUserName;
    private String remarks;
    private String printState;
    private String printExpressState;
    private String orderTime;
    private String orderAddress;
    private String  tradeNo;
    private List<OrderGoodsInfoEntity> orderLines;
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }



    public List<OrderGoodsInfoEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderGoodsInfoEntity> orderLines) {
        this.orderLines = orderLines;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getOmsStatus() {
        return omsStatus;
    }

    public void setOmsStatus(String omsStatus) {
        this.omsStatus = omsStatus;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getGoodsTotalAmount() {
        return goodsTotalAmount;
    }

    public void setGoodsTotalAmount(String goodsTotalAmount) {
        this.goodsTotalAmount = goodsTotalAmount;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPartnerUserNo() {
        return partnerUserNo;
    }

    public void setPartnerUserNo(String partnerUserNo) {
        this.partnerUserNo = partnerUserNo;
    }

    public String getPartnerUserName() {
        return partnerUserName;
    }

    public void setPartnerUserName(String partnerUserName) {
        this.partnerUserName = partnerUserName;
    }

    public String getShopUserNo() {
        return shopUserNo;
    }

    public void setShopUserNo(String shopUserNo) {
        this.shopUserNo = shopUserNo;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrintState() {
        return printState;
    }

    public void setPrintState(String printState) {
        this.printState = printState;
    }

    public String getPrintExpressState() {
        return printExpressState;
    }

    public void setPrintExpressState(String printExpressState) {
        this.printExpressState = printExpressState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}
