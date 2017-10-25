package com.jusenr.eg.demo.okhttp;

import android.content.Context;

import com.jusenr.eg.demo.okhttp.interceptor.CacheStrategyInterceptor;
import com.jusenr.eg.demo.okhttp.interceptor.HeaderInfoInterceptor;
import com.jusenr.toolslibrary.log.logger.Logger;
import com.jusenr.toolslibrary.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by riven_chris on 2017/4/13.
 */

public class MyOkHttpClient {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    private static Cache provideOkHttpCache() {
        int cacheSize = 20 * 1024 * 1024; // 20 MiB
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

        return cache;
    }

    public static okhttp3.OkHttpClient provideOkHttpClient() {
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();

        builder.connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        builder.addInterceptor(new CacheStrategyInterceptor(mContext));
//        builder.addInterceptor(new ProgressInterceptor(new ProgressResponseListener() {
//            @Override
//            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//
//            }
//        }));
        builder.addInterceptor(new HeaderInfoInterceptor(AppUtils.getVersionName(mContext)));

        builder.cache(provideOkHttpCache());

        return builder.build();
    }
}
