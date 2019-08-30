package com.heyongrui.module.dagger;


import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.module.data.service.DailyLifeService;
import com.heyongrui.module.data.service.DouBanService;
import com.heyongrui.module.data.service.MonoSerevice;
import com.heyongrui.module.data.service.TextService;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleModule {

    private Activity mActivity;
    private Fragment mFragment;

    public ModuleModule() {
    }

    public ModuleModule(Activity activity) {
        mActivity = activity;
    }

    public ModuleModule(Fragment fragment) {
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
    DailyLifeService provideDailyLifeService() {
        return new DailyLifeService();
    }

    @Provides
    @PerActivity
    DouBanService provideDouBanService() {
        return new DouBanService();
    }

    @Provides
    @PerActivity
    MonoSerevice provideMonoSerevice() {
        return new MonoSerevice();
    }

    @Provides
    @PerActivity
    TextService provideTextService() {
        return new TextService();
    }
}
