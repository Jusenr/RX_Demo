package com.jusenr.eg.demo.dagger2Test.model;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 18:32
 * Project    ：RX_Demo.
 */
public class ClothHandler {

    public ClothesModel handle(ClothModel cloth) {
        return new ClothesModel(cloth);
    }
}
