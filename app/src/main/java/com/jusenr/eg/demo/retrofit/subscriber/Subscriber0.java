package com.jusenr.eg.demo.retrofit.subscriber;

import com.jusenr.toolslibrary.log.logger.Logger;

import rx.Subscriber;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/10/16
 * Time       : 20:06
 * Project    ：RX_Demo.
 */
public abstract class Subscriber0<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String message = e.getMessage();
        onResponse(false, -1, message, null);
        onCompleted();
    }

    @Override
    public void onNext(T t) {
        onResponse(true, 200, null, t);
    }

    final void onResponse(boolean success, int code, String msg, T data) {
        onCompleted();
        if (success) {
            Logger.d("####-Data:" + data.toString());
            onNext(msg, data);
        } else {
            onError(code, msg);
        }
    }

    public abstract void onNext(String msg, T t);

    public abstract void onError(int code, String msg);
}
