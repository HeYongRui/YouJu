package com.heyongrui.main.dagger;


import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.main.data.service.MobService;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private Activity mActivity;
    private Fragment mFragment;

    public HomeModule() {
    }

    public HomeModule(Activity activity) {
        mActivity = activity;
    }

    public HomeModule(Fragment fragment) {
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
    MobService provideMobService() {
        return new MobService();
    }
}
