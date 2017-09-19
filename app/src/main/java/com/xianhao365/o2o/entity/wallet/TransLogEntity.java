package com.xianhao365.o2o.entity.wallet;

/**
 * 钱包交易明细
 * Created by mfx-t224 on 2017/9/19.
 */

public class TransLogEntity {
    private String billNo;
    private String tradeType;
    private String fundType;
    private String amt;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
