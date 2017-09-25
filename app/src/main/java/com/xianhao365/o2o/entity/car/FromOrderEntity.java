package com.xianhao365.o2o.entity.car;

import android.os.Parcel;
import android.os.Parcelable;

import com.xianhao365.o2o.entity.address.AddressInfoEntity;

import java.util.List;

/**
 * 购物车订单结算
 * Created by NN on 2017/9/25.
 */
public class FromOrderEntity implements Parcelable{
    private AddressInfoEntity defaultAddress;
    private String discountAmount;
    private String freightAmount;
    private String settlementAmount;
    private String transactionAmount;
    private PayModelEntity settlementModes;
    private List<OrderLinesEntity> orderLines;

    protected FromOrderEntity(Parcel in) {
        defaultAddress = in.readParcelable(AddressInfoEntity.class.getClassLoader());
        discountAmount = in.readString();
        freightAmount = in.readString();
        settlementAmount = in.readString();
        transactionAmount = in.readString();
        orderLines = in.createTypedArrayList(OrderLinesEntity.CREATOR);
    }

    public static final Creator<FromOrderEntity> CREATOR = new Creator<FromOrderEntity>() {
        @Override
        public FromOrderEntity createFromParcel(Parcel in) {
            return new FromOrderEntity(in);
        }

        @Override
        public FromOrderEntity[] newArray(int size) {
            return new FromOrderEntity[size];
        }
    };

    public PayModelEntity getSettlementModes() {
        return settlementModes;
    }

    public void setSettlementModes(PayModelEntity settlementModes) {
        this.settlementModes = settlementModes;
    }

    public AddressInfoEntity getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressInfoEntity defaultAddress) {
        this.defaultAddress = defaultAddress;
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

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

//    public String getSettlementModes() {
//        return settlementModes;
//    }
//
//    public void setSettlementModes(String settlementModes) {
//        this.settlementModes = settlementModes;
//    }

    public List<OrderLinesEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLinesEntity> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(defaultAddress, flags);
        dest.writeString(discountAmount);
        dest.writeString(freightAmount);
        dest.writeString(settlementAmount);
        dest.writeString(transactionAmount);
        dest.writeTypedList(orderLines);
    }
}
