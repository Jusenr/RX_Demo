package com.jusenr.eg.demo.dagger2Test.model;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 16:52
 * Project    ：RX_Demo.
 */
public class ClothesModel {

    private ClothModel model;

    public ClothesModel(ClothModel model) {
        this.model = model;
    }

    public ClothModel getModel() {
        return model;
    }

    public void setModel(ClothModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return model.getColor() + "衣服";
    }
}
