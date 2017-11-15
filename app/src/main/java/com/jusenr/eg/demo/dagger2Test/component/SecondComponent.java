package com.jusenr.eg.demo.dagger2Test.component;

import com.jusenr.eg.demo.dagger2Test.SecondActivity;
import com.jusenr.eg.demo.dagger2Test.module.SecondModule;
import com.jusenr.eg.demo.dagger2Test.type.PerActivity;

import dagger.Component;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/15
 * Time       : 18:34
 * Project    ：RX_Demo.
 */
@PerActivity
@Component(modules = SecondModule.class, dependencies = BaseComponent.class)
public interface SecondComponent {
    void inject(SecondActivity activity);
}
