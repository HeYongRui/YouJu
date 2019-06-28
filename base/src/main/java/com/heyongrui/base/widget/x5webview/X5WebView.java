package com.heyongrui.base.widget.x5webview;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;


public class X5WebView extends WebView {

    private Context mContext;
    private int overScrollMode;

    public X5WebView(Context context) {
        super(context);
        mContext = context;
        initWebViewSettings();
    }

    public X5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initWebViewSettings();
    }

    public X5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initWebViewSettings();
    }


    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
//        webSetting.setDefaultFontSize(20);
//        webSetting.setTextSize(WebSettings.TextSize.LARGEST);
//        webSetting.setTextZoom(22);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        //设置UserAgent，用以H5判断是哪种移动设备
        webSetting.setUserAgentString(webSetting.getUserAgentString() + "AndroidAPP");
        webSetting.setUseWideViewPort(true); // 推荐使用的窗口，使html界面自适应屏幕
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);// 支持javascript
        webSetting.setLoadsImagesAutomatically(true); // 支持自动加载图片
        webSetting.setGeolocationEnabled(true);
        if (mContext != null && mContext.getDir("appcache", 0) != null) {
            webSetting.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        }
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        webSetting.setAppCacheEnabled(true);
        if (mContext != null && mContext.getDir("databases", 0) != null) {
            webSetting.setDatabasePath(mContext.getDir("databases", 0).getPath());
        }
        webSetting.setDatabaseEnabled(true);
        if (mContext != null && mContext.getDir("geolocation", 0) != null) {
            webSetting.setGeolocationDatabasePath(mContext.getDir("geolocation", 0)
                    .getPath());
        }
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        if (Build.VERSION.SDK_INT > 8) {
            webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        }
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        //        getWindow().setFormat(PixelFormat.TRANSLUCENT);//用以Activity设置webview全屏的配置
        overScrollMode = getView().getOverScrollMode();
    }

    public void enableX5FullscreenFunc() {//开启X5全屏播放模式
        if (getX5WebViewExtension() != null) {
            getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    public void disableX5FullscreenFunc() {//恢复webkit初始状态
        if (getX5WebViewExtension() != null) {
            getView().setOverScrollMode(overScrollMode);
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    public void enableLiteWndFunc() {//开启小窗模式
        if (getX5WebViewExtension() != null) {
            getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    public void enablePageVideoFunc() {//页面内全屏播放模式
        if (getX5WebViewExtension() != null) {
            getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
        //绘制设备信息在控件左上角
//        boolean ret = super.drawChild(canvas, child, drawingTime);
//        canvas.save();
//        Paint paint = new Paint();
//        paint.setColor(0x7fff0000);
//        paint.setTextSize(24.f);
//        paint.setAntiAlias(true);
//        if (getX5WebViewExtension() != null) {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText(
//                    "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//                    100, paint);
//        } else {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText("Sys Core", 10, 100, paint);
//        }
//        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//        canvas.drawText(Build.MODEL, 10, 200, paint);
//        canvas.restore();
//        return ret;
    }
}
