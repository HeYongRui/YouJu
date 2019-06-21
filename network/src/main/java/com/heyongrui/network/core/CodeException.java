package com.heyongrui.network.core;

/**
 * 自定义错误code类型:注解写法
 * <p>
 * 可自由扩展
 * Created by WZG on 2016/12/12.
 */

public class CodeException {

    /*网络错误*/
    public static final int NETWORD_ERROR = 504;
    /*http_错误*/
    public static final int HTTP_ERROR = 400;
    /*fastjson错误*/
    public static final int JSON_ERROR = 401;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 500;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 501;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 503;
    /*超时错误*/
    public static final int TIMEOUT_ERROR = 408;

}
