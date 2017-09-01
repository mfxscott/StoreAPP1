package com.xianhao365.o2o.entity.cgListInfo;

import java.util.List;

/**
 * Created by mfx-t224 on 2017/9/1.
 */

public class GCListEntity {
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
