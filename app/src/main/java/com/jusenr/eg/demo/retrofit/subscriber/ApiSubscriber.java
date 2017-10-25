package com.jusenr.eg.demo.retrofit.subscriber;

import android.text.TextUtils;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by riven_chris on 2017/4/14.
 */

abstract class ApiSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        String message = e.getMessage();
        int code = 0;
        Log.e("#####", "ApiSubscriber-onError: " + message);
        if (e instanceof SocketTimeoutException) {
            code = 408;
        } else if (e instanceof UnknownHostException) {
            code = 1109001;
        } else if (!TextUtils.isEmpty(message)) {
            if (message.contains("400")) {
                code = 400;
            } else if (message.contains("404")) {
                code = 404;
            } else if (message.contains("500")) {
                code = 500;
            } else if (message.contains("502")) {
                code = 502;
            } else if (message.contains("503")) {
                code = 503;
            } else if (message.contains("504") && !message.contains("only-if-cached")) {
                code = 504;
            } else if (message.contains("resolve")
                    || message.contains("connect")
                    || message.contains("timeout")) {
                code = 408;
            } else {
                code = 1109001;
            }
        } else {
            code = 1109001;
        }
        onResponse(false, code, null, null);
        onCompleted();
    }

    final void onResponse(boolean success, int code, String msg, T data) {
        onCompleted();
        if (success) {
            onNext(msg, data);
        } else {
            if (code == 62002) {//单点登录
//                AccountHelper.logout();
            } else {
                onError(code, msg);
            }
        }
    }

    public abstract void onNext(String msg, T t);

    public abstract void onError(int code, String msg);
}
