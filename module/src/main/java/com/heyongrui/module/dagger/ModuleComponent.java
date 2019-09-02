package com.heyongrui.module.dagger;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.dagger.AppComponent;
import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.module.ModuleFragment;
import com.heyongrui.module.douban.presenter.DouBanPresenter;
import com.heyongrui.module.douban.view.DouBanActivity;
import com.heyongrui.module.textword.view.SmartRobotActivity;

import dagger.Component;

/**
 * 2019/8/26
 * lambert
 * 此Component依赖AppComponent，由于AppComponent使用了@Singleton，此处只能使用自定义@Scope(@PerActivity)
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ModuleModule.class})
public interface ModuleComponent {
    Activity getActivity();

    Fragment getFragment();

    void inject(DouBanActivity douBanActivity);

    void inject(DouBanPresenter douBanPresenter);

    void inject(ModuleFragment moduleFragment);

    void inject(SmartRobotActivity smartRobotActivity);
}
