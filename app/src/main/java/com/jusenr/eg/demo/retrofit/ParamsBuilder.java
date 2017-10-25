package com.jusenr.eg.demo.retrofit;

import java.util.HashMap;

/**
 * 固定参数拼接封装
 * Created by riven_chris on 16/7/5.
 */
public class ParamsBuilder {
    public static final String PARAM_KEY_UID = "uid";                   //家长账户的UID
    public static final String PARAM_KEY_PARENT_UID = "parent_uid";     //家长账户的UID
    public static final String PARAM_KEY_APP_ID = "appid";              //平台id
    public static final String PARAM_KEY_TOKEN = "token";               //登录的token
    public static final String PARAM_KEY_DEVICE_ID = "device_id";       //设备id
    public static final String PARAM_KEY_LANGUAGE = "language";

    public static final String PARAM_KEY_SIGN = "sign";                 //令牌

    public static final String PARAM_KEY_PUSH_TOKEN = "push_token";     //推送时用的token
    public static final String PARAM_KEY_PUSH_APPID = "push_appid";     //推送时用的appid

    public static final String PARAM_KEY_QUESTION = "question";//反馈的意见
    public static final String PARAM_KEY_CONTACT_WAY = "contact_way";//联系方式
    public static final String PARAM_KEY_TYPE = "type";//类型
    public static final String PARAM_KEY_EXT = "ext";
    public static final String PARAM_KEY_FILE_NAME = "file_name";
    public static final String PARAM_KEY_HASH = "hash";

    public static final String VALUE_UPLOADPHOTOS = "uploadPhotos";

    private HashMap<String, String> mParams;

    /**
     * 添加固定参数 uid/parent_uid 、appid、token、device_id
     */
    private ParamsBuilder() {
        mParams = new HashMap<>();
        mParams.put(PARAM_KEY_DEVICE_ID, "");

    }

    /**
     * (由于参数字段不统一，因此需要再次添加相关字段参数)
     *
     * @return
     */
    public static ParamsBuilder start() {
        ParamsBuilder paramsBuilder = new ParamsBuilder();
        paramsBuilder.put(PARAM_KEY_PARENT_UID, "");
        return paramsBuilder;
    }

    /**
     * 添加固定参数 push_token、push_appid
     *
     * @return
     */
    public static ParamsBuilder gpush() {
        ParamsBuilder paramsBuilder = new ParamsBuilder();
        paramsBuilder.mParams.put(PARAM_KEY_PUSH_TOKEN, "");
        paramsBuilder.mParams.put(PARAM_KEY_PUSH_APPID, "");
        return paramsBuilder;
    }

    public ParamsBuilder put(String k, String v) {
        if (v == null) return this;
        mParams.put(k, v);
        return this;
    }

    public ParamsBuilder put(String k, int v) {
        mParams.put(k, String.valueOf(v));
        return this;
    }

    public ParamsBuilder put(String k, long v) {
        mParams.put(k, String.valueOf(v));
        return this;
    }

    public ParamsBuilder remove(Object k) {
        if (k == null) return this;
        mParams.remove(k);
        return this;
    }

    public ParamsBuilder mock(boolean mock) {
        if (mock) {
            mParams.clear();
        }
        return this;
    }

    public HashMap<String, String> build() {
        return mParams;
    }

}
