package com.xianhao365.o2o.entity.orderlist;

import com.xianhao365.o2o.entity.cgListInfo.CGListInfoEntity;

import java.util.List;

/**
 */

public class OrderDatasetEntity {
    private String total;

    private List<CGListInfoEntity> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CGListInfoEntity> getRows() {
        return rows;
    }

    public void setRows(List<CGListInfoEntity> rows) {
        this.rows = rows;
    }

}
