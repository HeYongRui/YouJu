package com.heyongrui.base.assist;

/**
 * Created by lambert on 2019/4/16.
 * 常量配置
 */

public class ConfigConstants {
    //Sp name
    public static final String SP_NAME_APP_DATA = "app_data";
    //Sp key
    public static final String SP_KEY_TOKEN = "device_token";
    public static final String SP_KEY_BASE_URL = "base_url";
    public static final String SP_KEY_FIRST_INTO_APP = "first_into_app";
    public final static String SP_KEY_DATA = "data";

    //Arouter拦截器标识和目标跳转路径
    public static final String IS_NEED_INTERCEPT = "is_need_intercept";
    public static final String PATH_TARGET = "target_path";

    //main模块路由路径
    public static final String PATH_MAIN = "/main/activity";
    public static final String PATH_HOME_PROVIDER = "/home/main/service";
    //user模块路由路径
    public static final String PATH_LOGIN = "/user/login";
    public static final String PATH_USER = "/user/activity";
    public static final String PATH_USER_PROVIDER = "/user/main/service";
    //module模块路由路径
    public static final String PATH_MONO_TEA = "/module/monoTea";
    public static final String PATH_MONO_TEA_HISTORY_DATE = "/module/monoTeaHistoryDate";
    public static final String PATH_MONO_CATEGORY = "/module/monoCategory";
    public static final String PATH_HITOKOTO = "/module/hitokoto";
    public static final String PATH_KAIYAN_LIST = "/module/kaiyanList";
    public static final String PATH_KAIYAN_DETAIL = "/module/kaiyanDetail";
    public static final String PATH_GARBAGE_CLASSIFY = "/module/garbageClassify";
    public static final String PATH_H5 = "/module/h5";
    public static final String PATH_MODULE_PROVIDER = "/module/main/service";
    //module2模块路由路径
    public static final String PATH_MODULE2_PROVIDER = "/module2/main/service";
    public static final String PATH_WELFARE = "/module2/welfare";
    public static final String PATH_ABOUT = "/module2/about";
    public static final String PATH_ENCOURAGE = "/module2/encourage";
}
