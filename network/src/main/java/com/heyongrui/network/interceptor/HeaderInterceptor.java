package com.heyongrui.network.interceptor;

import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Brian Wu on 2016/6/1.
 */
public class HeaderInterceptor implements Interceptor {

    private String UniqueID = null;

    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        //添加统一请求头部
        Request.Builder requestBuilder = originalRequest.newBuilder();
        requestBuilder
                .addHeader("Accept-Charset", "utf-8")
                .addHeader("User-Agent", getUserAgent());
        //判断不同请求方式，进一步自定义操作处理
        String method = originalRequest.method();
        if (TextUtils.equals("POST", method)) {
            originalRequest = requestBuilder.build();
        } else if (TextUtils.equals("GET", method)) {
            originalRequest = requestBuilder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build();
        }
        return chain.proceed(originalRequest);
    }

    private String getUserAgent() {
        StringBuilder ua = new StringBuilder();
        ua.append('/' + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionName() + '_'
                + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionCode());
        ua.append("/Android");
        ua.append("/" + android.os.Build.VERSION.RELEASE);
        ua.append("/" + android.os.Build.MODEL);
        ua.append("/" + getUniqueID());
        return ua.toString();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String getUniqueID() {
        if (!TextUtils.isEmpty(UniqueID)) {
            return UniqueID;
        }
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            UniqueID = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            return UniqueID;
        } catch (Exception exception) {
            serial = "serial";
        }
        UniqueID = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        return UniqueID;
    }

}
