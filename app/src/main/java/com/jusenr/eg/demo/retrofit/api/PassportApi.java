package com.jusenr.eg.demo.retrofit.api;

import com.alibaba.fastjson.JSONObject;
import com.jusenr.eg.demo.model.UserModel;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by riven_chris on 16/7/4.
 */
public interface PassportApi {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(BaseApi.Url.URL_REGISTER)
    Observable<JSONObject> register(@FieldMap Map<String, String> map);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(BaseApi.Url.URL_GET_NICK_NAME)
    Observable<UserModel> getNickname(@FieldMap Map<String, String> map);


}
