package com.jusenr.eg.demo.dagger2Test.module;

import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.api.GankApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 19:10
 * Project    ：RX_Demo.
 */
@Module
public class BaseModule {

    @Singleton //单例
    @Provides
    public ClothHandler getClothHandler() {
        return new ClothHandler();
    }

    @Singleton //单例
    @Provides
    public GankApi getGankApi() {
        return RetrofitManager.getGankApi();
    }
}
