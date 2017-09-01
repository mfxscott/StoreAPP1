package com.xianhao365.o2o.entity.cgListInfo;

import java.util.List;

/**
 * Created by mfx-t224 on 2017/9/1.
 * 采购列表
 */

public class CGListInfoEntity {
    private String id;
    private String created;//创建时间
    private String creator;//创建
    private String modified;//修正时间
    private String modifier;//修正人
    private String suggestCode;//采购建议单号
    private String vendorCode;//供应商编码
    private String vendorName;//供应商名称
    private String purchaseCode;//采购单号
    private String purchaseAmount;//采购金额
    private String actualAmount;//实际支付金额
    private String payState;//付款状态，payed：已付款；not_pay：未付款；
    private String receiveState;//收货状态(10:新建 20:供应商确认30:已发货 40:完成)
    private String receiveResult;//0:未收货；10:部分收货20:全部收货
    private String receiveTime;//收货时间
    private String requestTime;//要求到货时间
    private String receiver;//收货人名称
    private String receiverPhone;//收货人电话
    private String receiverAddr;//收货地址
    private String vehicleNo;//车牌号
    private String driverName;//司机名称
    private String driverPhone;//司机电话
    private String sendTime;//发货时间
    private String sendAddr;//发货人地址
    private String senderPhone;//发货人电话
    private String senderName;//发货人名称
    private String remark;//备注
    private String images;//司机车辆拍照
    private List<CGPurchaseLinesEntity> purchaseLines;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuggestCode() {
        return suggestCode;
    }

    public void setSuggestCode(String suggestCode) {
        this.suggestCode = suggestCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(String receiveState) {
        this.receiveState = receiveState;
    }

    public String getReceiveResult() {
        return receiveResult;
    }

    public void setReceiveResult(String receiveResult) {
        this.receiveResult = receiveResult;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<CGPurchaseLinesEntity> getPurchaseLines() {
        return purchaseLines;
    }

    public void setPurchaseLines(List<CGPurchaseLinesEntity> purchaseLines) {
        this.purchaseLines = purchaseLines;
    }
//public static class CGPurchaseLinesEntity{
//    private String id;
//    private String created;
//    private String skuCode;//sku编码
//    private String thumbImg;//商品图片
//    private String goodsPrice;//商品采购价
//    private String goodsUnit;//商品重量/单位/
//    private String goodsName;//商品名称
//    private String goodsCode;//商品编码
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCreated() {
//        return created;
//    }
//
//    public void setCreated(String created) {
//        this.created = created;
//    }
//
//    public String getSkuCode() {
//        return skuCode;
//    }
//
//    public void setSkuCode(String skuCode) {
//        this.skuCode = skuCode;
//    }
//
//    public String getThumbImg() {
//        return thumbImg;
//    }
//
//    public void setThumbImg(String thumbImg) {
//        this.thumbImg = thumbImg;
//    }
//
//    public String getGoodsPrice() {
//        return goodsPrice;
//    }
//
//    public void setGoodsPrice(String goodsPrice) {
//        this.goodsPrice = goodsPrice;
//    }
//
//    public String getGoodsUnit() {
//        return goodsUnit;
//    }
//
//    public void setGoodsUnit(String goodsUnit) {
//        this.goodsUnit = goodsUnit;
//    }
//
//    public String getGoodsName() {
//        return goodsName;
//    }
//
//    public void setGoodsName(String goodsName) {
//        this.goodsName = goodsName;
//    }
//
//    public String getGoodsCode() {
//        return goodsCode;
//    }
//
//    public void setGoodsCode(String goodsCode) {
//        this.goodsCode = goodsCode;
//    }
//}
}
