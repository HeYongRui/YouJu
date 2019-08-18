package com.heyongrui.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.NetworkUtils;
import com.heyongrui.base.assist.NetStateChangeObserver;
import com.heyongrui.base.assist.NetStateChangeReceiver;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * lambert
 * 2019/6/20 17:11
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements NetStateChangeObserver {

    protected String TAG = "BaseFragment";
    protected FragmentActivity mActivity;
    protected Context mContext;
    protected View mView;
    protected T mPresenter;
    protected LayoutInflater mInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mActivity = getActivity();
        mContext = getContext();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    public void initImmersionBar() {
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mPresenter = setPresenter();
        if (mPresenter != null) {
            if (this instanceof BaseView) mPresenter.attchView(mContext, (BaseView) this);
        }
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
            initView(savedInstanceState);
        } else {
            if (mView.getParent() != null) {
                ViewGroup vg = (ViewGroup) mView.getParent();
                if (vg != null) {
                    vg.removeView(mView);
                }
            }
        }
        if (!NetworkUtils.isConnected()) {
            onNetDisconnected();
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onStart() {
        TAG = this.getClass().getSimpleName();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.registerObserver(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

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

    protected void addOnClickListeners(View.OnClickListener onClickListener, View... views) {
        if (views != null) {
            for (View view : views) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    protected void addOnClickListeners(View.OnClickListener onClickListener, @IdRes int... ids) {
        if (mView == null) return;
        if (ids != null) {
            for (@IdRes int id : ids) {
                mView.findViewById(id).setOnClickListener(onClickListener);
            }
        }
    }

    protected void addOnLongClickListeners(View.OnLongClickListener onClickListener, @IdRes int... ids) {
        if (mView == null) return;
        if (ids != null) {
            for (@IdRes int id : ids) {
                mView.findViewById(id).setOnLongClickListener(onClickListener);
            }
        }
    }


    protected abstract int getLayoutId();

    protected T setPresenter() {
        return null;
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);
}
