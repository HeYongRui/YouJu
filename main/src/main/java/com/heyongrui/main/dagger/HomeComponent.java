package com.heyongrui.main.dagger;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.dagger.AppComponent;
import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.main.mob.presenter.MobPresenter;
import com.heyongrui.main.mob.presenter.MobWeatherPresenter;

import dagger.Component;

/**
 * 2019/8/26
 * lambert
 * 此Component依赖AppComponent，由于AppComponent使用了@Singleton，此处只能使用自定义@Scope(@PerActivity)
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {HomeModule.class})
public interface HomeComponent {
    Activity getActivity();

    Fragment getFragment();

    void inject(MobPresenter mobPresenter);

    void inject(MobWeatherPresenter mobWeatherPresenter);
}
