package com.jusenr.eg.demo.retrofit.subscriber;

import com.jusenr.eg.demo.retrofit.model.Model2;

/**
 * Created by riven_chris on 2017/4/14.
 */

public abstract class ApiSubscriber2<T extends Model2> extends ApiSubscriber<T> {

    @Override
    public final void onNext(T data) {
        int code = data.getError_code();
        String msg = data.getMsg();
        boolean success = (code == 0);
        onResponse(success, code, msg, data);
    }
}
