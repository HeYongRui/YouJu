package com.heyongrui.base.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.NetworkUtils;
import com.heyongrui.base.app.BaseApplication;
import com.heyongrui.base.assist.NetStateChangeObserver;
import com.heyongrui.base.assist.NetStateChangeReceiver;
import com.heyongrui.base.dagger.AppComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * lambert
 * 2019/6/20 17:11
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements NetStateChangeObserver {

    protected String TAG = "BaseActivity";
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            Log.i("onCreate", "onCreate fixOrientation when Oreo, result = " + result);
        }
        super.onCreate(savedInstanceState);
        initializeInjector();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (getLayoutId() != 0) {
            // 通过注解绑定控件
            setContentView(getLayoutId());
        }
        ARouter.getInstance().inject(this);
        mPresenter = setPresenter();
        if (null != mPresenter && this instanceof BaseView) {
            mPresenter.attchView(this, (BaseView) this);
        }
        init(savedInstanceState);
        if (!NetworkUtils.isConnected()) {
            onNetDisconnected();
        }
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            Log.i("setRequestedOrientation", "avoid calling setRequestedOrientation when Oreo.");
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.registerObserver(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    protected void addOnClickListeners(View.OnClickListener onClickListener, View... views) {
        if (views != null) {
            for (View view : views) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    protected void addOnClickListeners(View.OnClickListener onClickListener, @IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnClickListener(onClickListener);
            }
        }
    }

    protected void addOnLongClickListeners(View.OnLongClickListener onClickListener, @IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnLongClickListener(onClickListener);
            }
        }
    }

    protected AppComponent getAppComponent() {
        return BaseApplication.getAppComponent();
    }

    protected void initializeInjector() {
    }

    protected boolean isImmersionBarEnabled() {//是否启用沉浸式
        return true;
    }

    protected void initImmersionBar() {
    }

    protected abstract int getLayoutId();

    protected T setPresenter() {
        return null;
    }

    protected abstract void init(Bundle savedInstanceState);


    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true.默认返回false
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return false;
    }

    @Override
    public void onNetDisconnected() {//网络断开
    }

    @Override
    public void onNetConnected(NetworkUtils.NetworkType networkType) {//网络连接
    }
}
