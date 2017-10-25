package com.jusenr.eg.demo.retrofit.api;

import com.jusenr.eg.demo.BuildConfig;

/**
 * Created by xiaopeng on 16/7/11.
 *
 * @see Url [http://gank.io/api]
 * create at 16/7/11 下午1:51
 */

public class BaseApi {
    public static final int HOST_FORMAL = 1;//正式环境
    public static final int HOST_TEST = 2;//测试环境
    public static final int HOST_DEV = 3;//开发环境

    public static int HOST_NOW = BuildConfig.HOST_NOW;//当前环境

    public static String API_LOCALE = "zh";
    public static String IMAGE_TYPE_100 = "?imageView2/0/w/100";
    public static String IMAGE_TYPE_200 = "?imageView2/0/w/200";
    public static String IMAGE_TYPE_300 = "?imageView2/0/w/300";
    public static String IMAGE_TYPE_500 = "?imageView2/0/w/500";
    public static String IMAGE_TYPE_800 = "?imageView2/0/w/800";

    /**
     * 通行证
     */
    public static String PASSPORT_URL = "";
    /**
     * 手环
     */
    public static String GANK_URL = "";

    /**
     * environment: 1，外网 2，测试内网 3，开发内网
     */
    public static void init() {
        switch (HOST_NOW) {
            case 1:
                PASSPORT_URL = "http://gank.io/api/";
                GANK_URL = "http://gank.io/api/";
                break;
            case 2:
                PASSPORT_URL = "http://gank.io/api/";
                GANK_URL = "http://gank.io/api/";
                break;
            case 3:
                PASSPORT_URL = "http://gank.io/api/";
                GANK_URL = "http://gank.io/api/";
                break;
        }
    }

    public static boolean isInnerEnvironment() {
        return HOST_NOW == HOST_DEV || HOST_NOW == HOST_TEST;
    }

    /*所有的相对URL*/
    public static class Url {
        //===================== PassportApi =====================//
        /**
         * Passport
         *
         * @see PassportApi {@link BaseApi#PASSPORT_URL}.
         */
        public static final String URL_REGISTER = "api/register";
        public static final String URL_GET_NICK_NAME = "api/getNickName";

        public static final String URL_LOGIN = "api/login";
        public static final String URL_API_LOGIN = "api/apilogin";

        //======================= GankApi =======================//
        /**
         * Gank
         *
         * @see GankApi {@link BaseApi#GANK_URL}.
         * @see "data/数据类型/请求个数/第几页"
         */
        public static final String URL_MATERIAL_BENEFITS = "data/{type}/{number}/{page}";//GankApi-福利

    }
}
