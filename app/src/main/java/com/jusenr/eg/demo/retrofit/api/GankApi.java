package com.jusenr.eg.demo.retrofit.api;

import android.support.annotation.StringDef;

import com.alibaba.fastjson.JSONObject;
import com.jusenr.eg.demo.model.UserModel;
import com.jusenr.eg.demo.retrofit.model.Model1;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/3.
 */

public interface GankApi {
    String TYPE_ANDROID = "Android";
    String TYPE_IOS = "iOS";
    String TYPE_MATERIALBENEFITS = "福利";
    String TYPE_VIDEO = "休息视频";
    String TYPE_EXPAND_RESOURCES = "拓展资源";
    String TYPE_WEB = "前端";
    String TYPE_ALL = "all";

    @StringDef({
            TYPE_ANDROID,
            TYPE_IOS,
            TYPE_MATERIALBENEFITS,
            TYPE_VIDEO,
            TYPE_EXPAND_RESOURCES,
            TYPE_WEB,
            TYPE_ALL
    })
    @interface GankType {
    }

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(BaseApi.Url.URL_LOGIN)
    Observable<JSONObject> login(@FieldMap Map<String, String> map);

    /**
     * 登出
     */
    @FormUrlEncoded
    @POST(BaseApi.Url.URL_API_LOGIN)
    Observable<Model1<UserModel>> logout(@FieldMap Map<String, String> map);

    /**
     * Gank-福利 "data/{type}/{month}/{day}"
     */
    @GET(BaseApi.Url.URL_MATERIAL_BENEFITS)
    Observable<JSONObject> materialBenefits(@Path("type") @GankType String type,
                                            @Path("number") int number,
                                            @Path("page") int page);

}
