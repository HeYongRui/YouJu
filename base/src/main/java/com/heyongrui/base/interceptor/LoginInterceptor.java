package com.heyongrui.base.interceptor;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;

import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@Interceptor(priority = 5, name = "login")
public class LoginInterceptor implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Bundle extras = postcard.getExtras();
        boolean isNeedIntercept = false;
        if (extras != null) {
            isNeedIntercept = extras.getBoolean(ConfigConstants.IS_NEED_INTERCEPT, false);
        }
        if (isNeedIntercept) {//需要执行登录拦截逻辑
            boolean isNeedLogin = new Random().nextInt(10) % 2 == 0;
            if (isNeedLogin) { //需要登录
                //主线程跳转登录页面（走绿色通道，不走拦截器）
                Single.just(postcard)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Postcard>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onSuccess(Postcard postcard) {
                                Bundle bundle = null;
                                if (null != postcard) {
                                    String targetPath = postcard.getPath();
                                    bundle = postcard.getExtras();
                                    if (null != bundle && !TextUtils.isEmpty(targetPath) && !TextUtils.equals(ConfigConstants.PATH_LOGIN, targetPath)) {
                                        bundle.putString(ConfigConstants.PATH_TARGET, targetPath);
                                    }
                                }
                                ARouter.getInstance().build(ConfigConstants.PATH_LOGIN)
                                        .with(bundle)
                                        .greenChannel()
                                        .navigation();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
                callback.onInterrupt(null);
            } else {//不需要登录
                callback.onContinue(postcard);
            }
        } else {//不需要拦截
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        mContext = context;
    }
}
