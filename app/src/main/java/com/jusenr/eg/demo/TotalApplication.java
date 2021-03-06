package com.jusenr.eg.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jusenr.eg.demo.base.ActivityManager;
import com.jusenr.eg.demo.dagger2Test.component.BaseComponent;
import com.jusenr.eg.demo.dagger2Test.component.DaggerBaseComponent;
import com.jusenr.eg.demo.dagger2Test.module.BaseModule;
import com.jusenr.eg.demo.okhttp.MyOkHttpClient;
import com.jusenr.eg.demo.retrofit.api.BaseApi;
import com.jusenr.eg.demo.widgets.fresco.ImagePipelineFactory;
import com.jusenr.toolslibrary.AndroidTools;
import com.umeng.analytics.MobclickAgent;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/10/23
 * Time       : 15:08
 * Project    ：RX_Demo.
 */
public class TotalApplication extends Application {

    private static Context mContext;
    private BaseComponent baseComponent;
    protected ActivityManager mActivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        MultiDex.install(getApplicationContext());

        baseComponent = DaggerBaseComponent.builder()
                .baseModule(new BaseModule())
                .build();

        BaseApi.init();

        //AndroidTools initialization
        AndroidTools.init(getApplicationContext(), getLogTag());

        //OkHttpClient initialization
        MyOkHttpClient.init(getApplicationContext());

        //Fresco initialization
        Fresco.initialize(getApplicationContext(),
                ImagePipelineFactory.imagePipelineConfig(getApplicationContext()
                        , MyOkHttpClient.provideOkHttpClient()
                        , getCacheDir().getAbsolutePath()));

        //UMeng initialization
        MobclickAgent.setDebugMode(BuildConfig.IS_TEST);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.openActivityDurationTrack(false);

        mActivityManager = new ActivityManager();
    }

    private String getLogTag() {
        return BuildConfig.LOG_TAG;
    }

    public static Context getInstance() {
        return mContext;
    }

    public BaseComponent getBaseComponent() {
        return baseComponent;
    }

    public ActivityManager getActivityManager() {
        return mActivityManager;
    }
}
