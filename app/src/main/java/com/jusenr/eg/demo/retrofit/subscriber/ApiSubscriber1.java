package com.jusenr.eg.demo.retrofit.subscriber;

import com.jusenr.eg.demo.retrofit.model.Model1;

/**
 * Created by riven_chris on 2017/4/14.
 */

public abstract class ApiSubscriber1<T> extends ApiSubscriber<Model1<T>> {

    @Override
    public final void onNext(Model1<T> t) {
        int status = t.getStatus();
        if (status == 999) {
            onCompleted();
//            AccountHelper.logout();
        } else {
            int httpCode = t.getHttp_code();
            int httpStatusCode = t.getHttp_status_code();
            String msg = t.getMsg();
            boolean success = (httpCode == 200 || httpStatusCode == 200);
            onResponse(success, httpCode != 0 ? httpCode : httpStatusCode, msg, t);
        }
    }
}