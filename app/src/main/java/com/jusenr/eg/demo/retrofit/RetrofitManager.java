package com.jusenr.eg.demo.retrofit;

import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.retrofit.api.PassportApi;

/**
 * Created by riven_chris on 16/7/3.
 */
public class RetrofitManager {

    private static RetrofitManager instance = null;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public static PassportApi getPassportApi() {
        return RetrofitFactory.getPassportRetrofit().create(PassportApi.class);
    }

    public static GankApi getGankApi() {
        return RetrofitFactory.getGankRetrofit().create(GankApi.class);
    }

    public static GankApi getGank2Api() {
        return RetrofitFactory.getGank2Retrofit().create(GankApi.class);
    }
}
