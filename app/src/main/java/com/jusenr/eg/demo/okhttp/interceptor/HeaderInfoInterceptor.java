package com.jusenr.eg.demo.okhttp.interceptor;


import com.jusenr.eg.demo.retrofit.api.BaseApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * header信息拦截器
 * Created by luowentao on 2016/10/27.
 */
public class HeaderInfoInterceptor implements Interceptor {
    private static final String TAG = HeaderInfoInterceptor.class.getSimpleName();
    private String version;

    public HeaderInfoInterceptor(String version) {
        this.version = version;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newHeaderRequest = request.newBuilder()
                .addHeader("Accept-Language", BaseApi.API_LOCALE)
                .addHeader("app-version", version)
                .build();

        StringBuffer stringBuffer = new StringBuffer();
        Response response;
        long startNs = System.nanoTime();
        try {
            response = chain.proceed(newHeaderRequest);
        } catch (Exception e) {
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        stringBuffer.append(response.message())
                .append("\n")
                .append(response.request().url())
                .append("\n")
                .append("(")
                .append(tookMs)
                .append("ms)");

//        Logger.i(stringBuffer.toString());

        return response;
    }
}
