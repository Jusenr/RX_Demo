package com.jusenr.eg.demo.dagger2Test.module;

import com.jusenr.eg.demo.dagger2Test.model.ClothModel;
import com.jusenr.eg.demo.dagger2Test.model.ClothesModel;
import com.jusenr.eg.demo.dagger2Test.type.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 16:05
 * Project    ：RX_Demo.
 */
@Module
public class MainModule {

    private CompositeSubscription mSubscription;

    @PerActivity
    @Provides
    public CompositeSubscription getSubscription() {
        if (mSubscription == null) mSubscription = new CompositeSubscription();
        return mSubscription;
    }

    @PerActivity
    @Provides
    public ClothModel getRedCloth() {
        ClothModel cloth = new ClothModel();
        cloth.setColor("红色");
        return cloth;
    }

    @PerActivity
    @Provides
    @Named("blue")
    public ClothModel getBlueCloth() {
        ClothModel cloth = new ClothModel();
        cloth.setColor("蓝色");
        return cloth;
    }

    @PerActivity
    @Provides
    public ClothesModel getClothes(ClothModel model) {
        return new ClothesModel(model);
    }

}
