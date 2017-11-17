package com.jusenr.eg.demo.dagger2Test.module;

import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.okhttp.MyOkHttpClient;
import com.jusenr.eg.demo.retrofit.api.BaseApi;
import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.retrofit.api.PassportApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public PassportApi getPassportApi() {
        return build(BaseApi.PASSPORT_URL)
                .create(PassportApi.class);
    }

    @Singleton //单例
    @Provides
    public GankApi getGankApi() {
        return build(BaseApi.GANK_URL)
                .create(GankApi.class);
    }

    private Retrofit build(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(MyOkHttpClient.provideOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
