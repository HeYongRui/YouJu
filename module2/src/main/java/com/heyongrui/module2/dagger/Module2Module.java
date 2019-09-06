package com.heyongrui.module2.dagger;


import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.module2.data.service.GankService;
import com.heyongrui.module2.data.service.ModuleService;

import dagger.Module;
import dagger.Provides;

@Module
public class Module2Module {

    private Activity mActivity;
    private Fragment mFragment;

    public Module2Module() {
    }

    public Module2Module(Activity activity) {
        mActivity = activity;
    }

    public Module2Module(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    /*
     * @ActivityContext别名，通过在@Inject时加入这个自定义注解达到返回相同provider类型但是数据不同(eg:User() User(int age))
     */
    @Provides
    @ActivityContext
    Context providesContext() {
        Context context = null;
        if (null != mActivity) {
            context = mActivity;
        } else if (null != mFragment) {
            context = mFragment.getContext();
        }
        return context;
    }

    @Provides
    @PerActivity
    GankService provideGankService() {
        return new GankService();
    }

    @Provides
    @PerActivity
    ModuleService provideModuleService() {
        return new ModuleService();
    }

    @Provides
    RxManager provideRxManager() {
        return new RxManager();
    }
}
