package com.xianhao365.o2o.entity.main;

import java.util.List;

/**
 * Created by mfx-t224 on 2017/10/12.
 */

public class SpecialsEntity {
    private String imgUrl;
    private String theme;
    private List<GoodsEntity> goods;

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
