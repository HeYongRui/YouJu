package com.heyongrui.network.configure;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.heyongrui.network.core.CodeException;
import com.heyongrui.network.core.CoreApiException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;


public class FactoryException {

    /**
     * 解析异常
     */
    public static CoreApiException analysisExcetpion(Throwable e) {
        e.printStackTrace();
        CoreApiException apiException;
        if (!NetworkUtils.isAvailableByPing()) {//无网络
            apiException = new CoreApiException(e, CodeException.NETWORD_ERROR, "无网络连接");
            return apiException;
        }
        if (e instanceof CoreApiException) {
            apiException = (CoreApiException) e;
        } else if (e instanceof HttpException) {
            apiException = new CoreApiException(e, CodeException.TIMEOUT_ERROR, "网络错误");
        } else if (e instanceof UnknownHostException) {
            //主机挂了，也就是你服务器关了
            apiException = new CoreApiException(e, CodeException.UNKOWNHOST_ERROR, "服务异常");
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException
                || e instanceof TimeoutException || e instanceof SocketException) {
            //连接超时等
            apiException = new CoreApiException(e, CodeException.TIMEOUT_ERROR, "连接失败");
        } else if (e instanceof NumberFormatException || e instanceof IllegalArgumentException
                || e instanceof JsonSyntaxException || e instanceof JsonParseException
                || e instanceof JSONException || e instanceof ParseException) {
            apiException = new CoreApiException(e, CodeException.JSON_ERROR, "解析错误");
        } else {
            //其他未知 HttpException RuntimeException等
            apiException = new CoreApiException(e, CodeException.UNKNOWN_ERROR, "未知错误");
        }
        return apiException;
    }
}
