package com.jusenr.eg.demo.retrofit.subscriber;

import com.jusenr.eg.demo.retrofit.model.Model3;

/**
 * Created by riven_chris on 2017/4/14.
 */

public abstract class ApiSubscriber3<T> extends ApiSubscriber<Model3<T>> {

    @Override
    public final void onNext(Model3<T> t) {
        int code = t.getError_code();
        String msg = t.getMsg();
        boolean success = (code == 0);
        onResponse(success, code, msg, t);
    }
}