package com.jusenr.eg.demo.dagger2Test.component;

import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.dagger2Test.module.BaseModule;
import com.jusenr.eg.demo.retrofit.api.GankApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 19:10
 * Project    ：RX_Demo.
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {
    ClothHandler getClothHandler();

    GankApi getGankApi();
}
