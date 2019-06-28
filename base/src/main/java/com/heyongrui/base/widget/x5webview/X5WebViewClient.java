package com.heyongrui.base.widget.x5webview;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebSettings;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebViewClient extends WebViewClient {

    public X5WebViewClient() {
    }

    //重写这个方法，忽视证书有问题的SSL网页
    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        // 接受所有网站的证书，忽略SSL错误，执行访问网页
        sslErrorHandler.proceed();//直接调用，谷歌审核不通过
        //推荐处理
//        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage(R.string.notification_error_ssl_cert_invalid);
//        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sslErrorHandler.proceed();
//            }
//        });
//        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sslErrorHandler.cancel();
//            }
//        });
//        final AlertDialog dialog = builder.create();
//        dialog.show();
    }

    /**
     * 拦截url
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
        //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
        //返回true，说明你自己想根据url，做新的跳转
        view.loadUrl(url);
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
//        return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        String url = webResourceRequest.getUrl().toString();
        webView.loadUrl(url);
        return true;
    }

    /**
     * 低版本错误处理方法
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //6.0以下执行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    /**
     * 高版本错误处理方法
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        //6.0以上执行
        super.onReceivedError(webView, webResourceRequest, webResourceError);
    }
}
