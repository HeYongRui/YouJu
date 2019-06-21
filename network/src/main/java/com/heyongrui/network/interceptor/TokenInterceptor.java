package com.heyongrui.network.interceptor;


import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Brian Wu on 2016/6/1.
 */
public class TokenInterceptor implements Interceptor {
    public static final String REQUEST_AUTH_TOKEN = "x-auth-token";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String url = originalRequest.url().toString();
//        if (url.equals(phoneLoginUrl) || url.equals(adminloginUrl) || url.equals(emailLoginUrl)
//                || url.equals(phoneRegistUrl) || url.equals(emailRegistUrl)) {
//            authRequest = originalRequest;
//        } else {
//            //传入token
//            authRequest = originalRequest.newBuilder().addHeader(REQUEST_AUTH_TOKEN, originalToken).build();
//        }
//        Response originalResponse = chain.proceed(authRequest);
//        //保存token
//        if (url.equals(phoneLoginUrl) || url.equals(phoneRegistUrl) || url.equals(emailLoginUrl) || url.equals(emailRegistUrl)
//                || url.equals(thirdPlatformUrl) || url.equals(restpasswordUrl) || url.equals(adminloginUrl)) {
//            String authToken = originalResponse.header(REQUEST_AUTH_TOKEN);
//            appContext.setAuthToken(authToken);
//        }

        //传入token
        Request authRequest = originalRequest.newBuilder().addHeader(REQUEST_AUTH_TOKEN, getAuthToken()).build();
        //保存token
        Response originalResponse = chain.proceed(authRequest);
        String authToken = originalResponse.header(REQUEST_AUTH_TOKEN);
        setAuthToken(authToken);

        return originalResponse;
    }

    private String getAuthToken() {
        SPUtils spUtils = SPUtils.getInstance("http_request");
        String authToken = spUtils.getString("http_request_auth_token", "tokenid_undefined");
        return authToken;
    }

    private void setAuthToken(String authToken) {
        SPUtils spUtils = SPUtils.getInstance("http_request");
        spUtils.put("http_request_auth_token", authToken);
    }
}
