package com.heyongrui.module.h5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.StringUtils;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.base.widget.x5webview.X5WebChromeClient;
import com.heyongrui.base.widget.x5webview.X5WebView;
import com.heyongrui.base.widget.x5webview.X5WebViewClient;
import com.heyongrui.module.R;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;


/**
 * Created by lambert on 2018/6/13.
 */

public class X5WebViewFragment extends BaseFragment {

    public static final String H5_URL_KEY = "h5Url";
    public static final String H5_CONTENT_KEY = "htmlContent";
    public static final String H5_TITLE_KEY = "title";
    public static final String H5_IS_SHOW_BAR_KEY = "isShowToolBar";
    public static final String H5_PARCELABLE_KEY = "parcelable";

    ConstraintLayout toolBar;
    TextView tvTitle;
    ProgressBar progressBar;
    X5WebView x5Webview;

    private String mH5Url, mHtmlContent, mTitle;

    public static X5WebViewFragment newInstance() {
        X5WebViewFragment fragment = new X5WebViewFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_x5webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolBar = mView.findViewById(R.id.tool_bar);
        tvTitle = mView.findViewById(R.id.tv_title);
        progressBar = mView.findViewById(R.id.progressbar);
        x5Webview = mView.findViewById(R.id.x5_webview);
        initWebView(x5Webview);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                backLogic();
            } else if (id == R.id.iv_close) {
                mActivity.finish();
            }
        }, R.id.iv_back, R.id.iv_close);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments == null) return;
        mH5Url = arguments.getString(H5_URL_KEY, "");
        mHtmlContent = arguments.getString(H5_CONTENT_KEY, "");
        mTitle = arguments.getString(H5_TITLE_KEY, "");
        if (!StringUtils.isTrimEmpty(mTitle)) {
            tvTitle.setText(mTitle);
        }
        Parcelable parcelable = arguments.getParcelable(H5_PARCELABLE_KEY);
        boolean isShowToolBar = arguments.getBoolean(H5_IS_SHOW_BAR_KEY, true);
        toolBar.setVisibility(isShowToolBar ? View.VISIBLE : View.GONE);
        //优先使用Url，其次使用Html
        if (!TextUtils.isEmpty(mH5Url)) {
            x5Webview.loadUrl(mH5Url);
        } else if (!TextUtils.isEmpty(mHtmlContent)) {
            x5Webview.loadDataWithBaseURL(null, mHtmlContent, "text/html", "utf-8", null);
        }
    }

    private void initWebView(X5WebView x5WebView) {
        //隐藏滚动块
        IX5WebViewExtension ex = x5WebView.getX5WebViewExtension();
        if (ex != null) {//这里要判空，否则如果是调用手机浏览器内核的话会报空
            ex.setScrollBarFadingEnabled(false);
        }
        x5WebView.setWebViewClient(new X5WebViewClient() {

            @Override
            public void onPageFinished(WebView webView, String s) {//页面加载完成回调
                super.onPageFinished(webView, s);
                if (StringUtils.isTrimEmpty(mTitle)) {//没有固定title才显示网页的title
                    tvTitle.setText(webView.getTitle());
                }
                CookieSyncManager.getInstance().sync();
            }
        });
        X5WebChromeClient webChromeClient = new X5WebChromeClient(mActivity) {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (tvTitle == null) return;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (StringUtils.isTrimEmpty(mTitle)) {//没有固定title才显示网页的title
                    if (newProgress != 100) {//页面没加载完成标题设为空，否则将显示网站链接
                        if (!StringUtils.isTrimEmpty(tvTitle.getText().toString())) {
                            tvTitle.setText("");
                        }
                    } else {//加载完成后设置H5标题
                        tvTitle.setText(view.getTitle());
                    }
                }
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                    if (progressBar != null && newProgress != 100) {
                        UiUtil.setVisibility(progressBar, View.VISIBLE);
                    } else if (progressBar != null) {
                        UiUtil.setVisibility(progressBar, View.GONE);
                    }
                }
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage, long l1, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(requiredStorage * 2);
            }
        };
        x5WebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        x5WebView.setWebChromeClient(webChromeClient);
    }

    @Override
    public void onResume() {
        super.onResume();
        x5Webview.onResume();
        x5Webview.resumeTimers();
        CookieSyncManager.createInstance(mContext);
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    public void onPause() {
        super.onPause();
        x5Webview.onPause();
        x5Webview.pauseTimers();
        CookieSyncManager.getInstance().stopSync();
    }

    private boolean backLogic() {
        if (x5Webview.canGoBack()) {
            x5Webview.goBack();
            return true;
        } else {
            mActivity.finish();
            return false;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        return backLogic();
    }

    @Override
    public void onDestroy() {
        if (x5Webview != null) {
            x5Webview.removeAllViews();
            x5Webview.destroy();
        }
        super.onDestroy();
    }
}
