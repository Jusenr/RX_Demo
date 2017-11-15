package com.jusenr.eg.demo.dagger2Test.model;

import javax.inject.Inject;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 16:33
 * Project    ：RX_Demo.
 */
public class ShoeModel {

    private String shoe;

    @Inject
    public ShoeModel() {
    }

    public String getShoe() {
        return shoe;
    }

    public void setShoe(String shoe) {
        this.shoe = shoe;
    }

    @Override
    public String toString() {
        return "黑色鞋子";
    }
}
