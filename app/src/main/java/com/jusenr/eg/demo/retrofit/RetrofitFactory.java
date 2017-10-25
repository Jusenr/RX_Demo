package com.jusenr.eg.demo.retrofit;


import com.jusenr.eg.demo.okhttp.MyOkHttpClient;
import com.jusenr.eg.demo.retrofit.api.BaseApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by riven_chris on 16/7/3.
 */
public class RetrofitFactory {

    static Retrofit getPassportRetrofit() {
        return PassportRetrofitPlaceHolder.RETROFIT;
    }

    static Retrofit getGankRetrofit() {
        return GankRetrofitPlaceHolder.RETROFIT;
    }

    private static class PassportRetrofitPlaceHolder {
        static final Retrofit RETROFIT = build(BaseApi.PASSPORT_URL);
    }

    private static class GankRetrofitPlaceHolder {
        static final Retrofit RETROFIT = build(BaseApi.GANK_URL);
    }

    private static Retrofit build(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(MyOkHttpClient.provideOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
