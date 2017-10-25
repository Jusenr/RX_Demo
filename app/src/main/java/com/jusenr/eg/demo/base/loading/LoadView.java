package com.jusenr.eg.demo.base.loading;

import android.support.annotation.UiThread;

/**
 * Created by mingjun on 168/9.
 */
public interface LoadView {

    @UiThread
    void showLoading();

    @UiThread
    void dismissLoading();
}
