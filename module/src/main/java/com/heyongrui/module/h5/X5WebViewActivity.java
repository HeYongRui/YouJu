package com.heyongrui.module.h5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.module.R;

@Route(path = ConfigConstants.PATH_H5)
public class X5WebViewActivity extends BaseActivity {

    public BaseFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_x5webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        fragment = X5WebViewFragment.newInstance();
        fragment.setArguments(bundle);
        loadRootFragment(R.id.x5webview_content_fragment, fragment, true, true);
    }

    public static class X5Builder {//H5页面构造者模式，灵活设置所需参数
        private Context context;
        private String h5Url;
        private String htmlContent;
        private String title;
        private boolean isShowToolBar;
        private Parcelable parcelable;

        public X5Builder(Context context) {
            this.context = context;
        }

        public X5Builder setH5Url(String h5Url) {
            this.h5Url = h5Url;
            return this;
        }

        public X5Builder setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
            return this;
        }

        public X5Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public X5Builder setIsShowToolBar(boolean isShowToolBar) {
            this.isShowToolBar = isShowToolBar;
            return this;
        }

        public X5Builder setParcelable(Parcelable parcelable) {
            this.parcelable = parcelable;
            return this;
        }

        public void launchActivity() {
            Bundle bundle = new Bundle();
            bundle.putString(X5WebViewFragment.H5_URL_KEY, h5Url);
            bundle.putString(X5WebViewFragment.H5_CONTENT_KEY, htmlContent);
            bundle.putString(X5WebViewFragment.H5_TITLE_KEY, title);
            bundle.putBoolean(X5WebViewFragment.H5_IS_SHOW_BAR_KEY, isShowToolBar);
            bundle.putParcelable(X5WebViewFragment.H5_PARCELABLE_KEY, parcelable);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(context, X5WebViewActivity.class);
            context.startActivity(intent);
        }
    }
}
