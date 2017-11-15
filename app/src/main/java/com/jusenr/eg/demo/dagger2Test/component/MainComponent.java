package com.jusenr.eg.demo.dagger2Test.component;

import com.jusenr.eg.demo.dagger2Test.Dagger2TestActivity;
import com.jusenr.eg.demo.dagger2Test.module.MainModule;
import com.jusenr.eg.demo.dagger2Test.type.PerActivity;

import dagger.Component;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 16:08
 * Project    ：RX_Demo.
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = BaseComponent.class)
public interface MainComponent {
    void inject(Dagger2TestActivity activity);
}
