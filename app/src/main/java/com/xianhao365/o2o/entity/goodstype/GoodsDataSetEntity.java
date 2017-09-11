package com.xianhao365.o2o.entity.goodstype;

import com.xianhao365.o2o.entity.goodsinfo.GoodsInfoEntity;

import java.util.List;

/**
 * Created by NN on 2017/9/9.
 */

public class GoodsDataSetEntity {
     private List<GoodsInfoEntity> dataset;
    private String hasNextIndex;

    public List<GoodsInfoEntity> getDataset() {
        return dataset;
    }

    public void setDataset(List<GoodsInfoEntity> dataset) {
        this.dataset = dataset;
    }

    public String getHasNextIndex() {
        return hasNextIndex;
    }

    public void setHasNextIndex(String hasNextIndex) {
        this.hasNextIndex = hasNextIndex;
    }
}
