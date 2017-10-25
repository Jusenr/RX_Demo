package com.jusenr.eg.demo.retrofit;

/**
 * Created by riven_chris on 2017/1/6.
 */

public class HttpCode {

    public static final int HTTP_DEFAULT_CODE = 0;

    /**
     * http_code = 200 || http_status_code = 200
     */
    public static final int HTTP_SUCCESS_CODE = 200;

    /**
     * error_code = 0
     */
    public static final int HTTP_NO_ERROR_CODE = 0;

    /**
     * 账号在其他设备上登录
     */
    public static final int HTTP_LOGIN_KICKED_CODE = 604;

    /**
     * 登录过期
     */
    public static final int HTTP_LOGIN_EXPIRE_CODE = 601;
}
