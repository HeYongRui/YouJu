package com.heyongrui.network.configure;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Brian Wu on 2016/12/15.
 */

public class TrustManager {

    public static SSLSocketFactory getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final X509TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                //检查客户端证书
                @Override
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                //检查服务器端证书
                @Override
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                //返回受信任的X509证书数组
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();


            return sslSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

