package com.heyongrui.base.widget.multiplestatuslayout;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lambert on 2018/10/17.
 */

public class MultipleStatusLayout extends FrameLayout {
    private View mLoadingView;
    private View mRetryView;
    private View mErrorView;
    private View mNoNetworkView;
    private View mContentView;
    private View mEmptyView;
    private LayoutInflater mInflater;


    private static final String TAG = "MultipleStatusLayout";

    public MultipleStatusLayout(Context context) {
        this(context, null);
    }

    public MultipleStatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MultipleStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void showLoading() {
//        if (isMainThread()) {
//            showView(mLoadingView);
//        } else {
//            post(() -> showView(mLoadingView));
//        }
        post(() -> showView(mLoadingView));
    }

    public void showError() {
//        if (isMainThread()) {
//            showView(mErrorView);
//        } else {
//            post(() -> showView(mErrorView));
//        }
        post(() -> showView(mErrorView));
    }

    public void showNoNetworkView() {
//        if (isMainThread()) {
//            showView(mNoNetworkView);
//        } else {
//            post(() -> showView(mNoNetworkView));
//        }
        post(() -> showView(mNoNetworkView));
    }

    public void showRetry() {
//        if (isMainThread()) {
//            showView(mRetryView);
//        } else {
//            post(() -> showView(mRetryView));
//        }
        post(() -> showView(mRetryView));
    }

    public void showContent() {
//        if (isMainThread()) {
//            showView(mContentView);
//        } else {
//            post(() -> showView(mContentView));
//        }
        post(() -> showView(mContentView));
    }

    public void showEmpty() {
//        if (isMainThread()) {
//            showView(mEmptyView);
//        } else {
//            post(() -> showView(mEmptyView));
//        }
        post(() -> showView(mEmptyView));
    }

    private void showView(View view) {
        if (view == null) return;
        if (view == mLoadingView) {
            mLoadingView.setVisibility(View.VISIBLE);
            if (mErrorView != null)
                mErrorView.setVisibility(View.GONE);
            if (mNoNetworkView != null)
                mNoNetworkView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mErrorView) {
            mErrorView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mNoNetworkView != null)
                mNoNetworkView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mNoNetworkView) {
            mNoNetworkView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mErrorView != null)
                mErrorView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mRetryView) {
            mRetryView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mErrorView != null)
                mErrorView.setVisibility(View.GONE);
            if (mNoNetworkView != null)
                mNoNetworkView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mContentView) {
            mContentView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mErrorView != null)
                mErrorView.setVisibility(View.GONE);
            if (mNoNetworkView != null)
                mNoNetworkView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mEmptyView) {
            mEmptyView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mErrorView != null)
                mErrorView.setVisibility(View.GONE);
            if (mNoNetworkView != null)
                mNoNetworkView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
        }
    }

    public View setLoadingView(int layoutId) {
        return setLoadingView(mInflater.inflate(layoutId, this, false));
    }

    public View setErrorView(int layoutId) {
        return setErrorView(mInflater.inflate(layoutId, this, false));
    }

    public View setNoNetworkView(int layoutId) {
        return setNoNetworkView(mInflater.inflate(layoutId, this, false));
    }

    public View setEmptyView(int layoutId) {
        return setEmptyView(mInflater.inflate(layoutId, this, false));
    }

    public View setRetryView(int layoutId) {
        return setRetryView(mInflater.inflate(layoutId, this, false));
    }

    public View setContentView(int layoutId) {
        return setContentView(mInflater.inflate(layoutId, this, false));
    }

    public View setLoadingView(View view) {
        View loadingView = mLoadingView;
        if (loadingView != null) {
            Log.w(TAG, "you have already set a loading view and would be instead of this new one.");
        }
        removeView(loadingView);
        addView(view);
        mLoadingView = view;
        return mLoadingView;
    }

    public View setErrorView(View view) {
        View errorView = mErrorView;
        if (errorView != null) {
            Log.w(TAG, "you have already set a error view and would be instead of this new one.");
        }
        removeView(errorView);
        addView(view);
        mErrorView = view;
        return mErrorView;
    }

    public View setNoNetworkView(View view) {
        View networkView = mNoNetworkView;
        if (networkView != null) {
            Log.w(TAG, "you have already set a nonetwork view and would be instead of this new one.");
        }
        removeView(networkView);
        addView(view);
        mNoNetworkView = view;
        return mNoNetworkView;
    }

    public View setEmptyView(View view) {
        View emptyView = mEmptyView;
        if (emptyView != null) {
            Log.w(TAG, "you have already set a empty view and would be instead of this new one.");
        }
        removeView(emptyView);
        addView(view);
        mEmptyView = view;
        return mEmptyView;
    }

    public View setRetryView(View view) {
        View retryView = mRetryView;
        if (retryView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.");
        }
        removeView(retryView);
        addView(view);
        mRetryView = view;
        return mRetryView;

    }

    public View setContentView(View view) {
        View contentView = mContentView;
        if (contentView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.");
        }
        removeView(contentView);
        addView(view);
        mContentView = view;
        return mContentView;
    }

    public View getRetryView() {
        return mRetryView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getNoNetworkView() {
        return mNoNetworkView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }
}