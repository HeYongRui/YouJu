package com.heyongrui.network.configure;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.heyongrui.base.widget.catloadingview.CatLoadingView;
import com.heyongrui.network.core.CoreApiException;

import java.lang.ref.SoftReference;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by lambert on 2018/10/23.
 */

public abstract class ResponseDisposable<T> extends DisposableObserver<T> {

    private SoftReference<Context> mContext;
    private Dialog mLoadingDialog;

    //调用此无参构造函数无网络加载框
    public ResponseDisposable(Context context) {
        this(context, false);
    }

    public ResponseDisposable(Context context, boolean isShowLoading) {
        this(context, isShowLoading, null);
    }

    public ResponseDisposable(Context context, boolean isShowLoading, String loadingContent) {
        if (context != null) {
            mContext = new SoftReference(context);
            if (isShowLoading) {
                loadingContent = TextUtils.isEmpty(loadingContent) ? "" : loadingContent;
                showDialog(loadingContent);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        dismissDialog();
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        unsubscribe();
        dismissDialog();
        //统一处理错误异常
        CoreApiException coreApiException = FactoryException.analysisExcetpion(e);
        //用户登录失效，清除本地用户信息
        int code = coreApiException.getCode();
        if (code == 1105) {
            SPUtils spUtils = SPUtils.getInstance("http_request");
            spUtils.put("http_request_auth_token", "");
        }
        //返回错误信息
        onFailure(coreApiException.getCode(), coreApiException.getDisplayMessage());
    }

    @Override
    public void onComplete() {
        unsubscribe();
        dismissDialog();
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(int errorCode, String errorMsg);

    private void showDialog(String loadingContent) {
        if (mContext == null) return;
        Context context = mContext.get();
        if (context == null) return;
        if (mLoadingDialog == null) {
            mLoadingDialog = new CatLoadingView(context);
            ((CatLoadingView) mLoadingDialog).setGraduallyText(loadingContent);
        }
        mLoadingDialog.setOnCancelListener(dialog -> unsubscribe());
        mLoadingDialog.setOnDismissListener(dialogInterface -> unsubscribe());
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public void unsubscribe() {
        if (isDisposed()) {
            dispose();
        }
    }
}
