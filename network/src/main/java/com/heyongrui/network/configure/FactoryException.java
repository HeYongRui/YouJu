package com.heyongrui.network.configure;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.heyongrui.network.core.CoreApiException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;


public class FactoryException {


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

    /**
     * 解析异常
     */
    public static CoreApiException analysisExcetpion(Throwable e) {
        e.printStackTrace();
        CoreApiException apiException;
        if (!NetworkUtils.isAvailableByPing()) {//无网络
            apiException = new CoreApiException(e, NETWORD_ERROR, "无网络连接");
            return apiException;
        }
        if (e instanceof CoreApiException) {
            apiException = (CoreApiException) e;
        } else if (e instanceof HttpException) {
            try {
                String errorBody = ((HttpException) e).response().errorBody().string();
                JSONObject jsonObject = new JSONObject(errorBody);
                int status = jsonObject.getInt("status");
                String info = jsonObject.getString("info");
                apiException = new CoreApiException(status, info);
            } catch (IOException ex) {
                ex.printStackTrace();
                apiException = new CoreApiException(e, TIMEOUT_ERROR, "网络错误");
            } catch (JSONException ex) {
                ex.printStackTrace();
                apiException = new CoreApiException(e, TIMEOUT_ERROR, "解析错误");
            }
        } else if (e instanceof UnknownHostException) {
            //主机挂了，也就是你服务器关了
            apiException = new CoreApiException(e, UNKOWNHOST_ERROR, "服务异常");
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException
                || e instanceof TimeoutException || e instanceof SocketException) {
            //连接超时等
            apiException = new CoreApiException(e, TIMEOUT_ERROR, "连接失败");
        } else if (e instanceof NumberFormatException || e instanceof IllegalArgumentException
                || e instanceof JsonSyntaxException || e instanceof JsonParseException
                || e instanceof JSONException || e instanceof ParseException) {
            apiException = new CoreApiException(e, JSON_ERROR, "解析错误");
        } else {
            //其他未知 HttpException RuntimeException等
            apiException = new CoreApiException(e, UNKNOWN_ERROR, "未知错误");
        }
        return apiException;
    }
}
