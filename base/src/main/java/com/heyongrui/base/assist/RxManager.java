package com.heyongrui.base.assist;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by hpw on 16/10/27.
 */

public class RxManager {

    private CompositeDisposable mCompositeDisposable;

    public RxManager() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void add(Disposable disposable) {
        if (mCompositeDisposable != null && disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public void clear() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}