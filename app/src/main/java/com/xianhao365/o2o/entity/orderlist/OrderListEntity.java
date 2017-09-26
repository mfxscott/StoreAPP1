package com.xianhao365.o2o.entity.orderlist;

/**
 * Created by NN on 2017/9/26.
 */

public class OrderListEntity {
    private String hasNextIndex;

    private OrderDatasetEntity dataset;

    public String getHasNextIndex() {
        return hasNextIndex;
    }

    public void setHasNextIndex(String hasNextIndex) {
        this.hasNextIndex = hasNextIndex;
    }

    public OrderDatasetEntity getDataset() {
        return dataset;
    }

    public void setDataset(OrderDatasetEntity dataset) {
        this.dataset = dataset;
    }
}
