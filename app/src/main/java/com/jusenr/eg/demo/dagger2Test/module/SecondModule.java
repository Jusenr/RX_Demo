package com.jusenr.eg.demo.dagger2Test.module;

import com.jusenr.eg.demo.dagger2Test.model.ClothModel;
import com.jusenr.eg.demo.dagger2Test.type.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 18:30
 * Project    ：RX_Demo.
 */
@Module
public class SecondModule {

    @PerActivity
    @Provides
    public ClothModel getGreenCloth() {
        ClothModel cloth = new ClothModel();
        cloth.setColor("绿色");
        return cloth;
    }

}
