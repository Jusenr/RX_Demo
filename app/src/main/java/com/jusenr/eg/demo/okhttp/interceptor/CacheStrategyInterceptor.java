package com.jusenr.eg.demo.okhttp.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by riven_chris on 16/7/5.
 */
public class CacheStrategyInterceptor implements Interceptor {
    public static final int MAX_AGE = 3600;
    private Context context;

    public CacheStrategyInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        if (!NetworkUtils.isNetworkReachable(context)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//        }
        Response originalResponse = chain.proceed(request);
//        if (NetworkUtils.isNetworkReachable(context)) {
//            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//            String cacheControl = request.cacheControl().toString();
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", cacheControl)
//                    .removeHeader("Pragma").build();
//        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + Integer.MAX_VALUE + ", max-age=" + MAX_AGE)
                    .removeHeader("Pragma").build();
//        }
    }
}
