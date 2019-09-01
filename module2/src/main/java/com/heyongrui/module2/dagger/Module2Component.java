package com.heyongrui.module2.dagger;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.heyongrui.base.dagger.AppComponent;
import com.heyongrui.base.dagger.PerActivity;
import com.heyongrui.module2.gank.presenter.GankPresenter;
import com.heyongrui.module2.gank.presenter.LeisureReadPresenter;

import dagger.Component;

/**
 * 2019/8/26
 * lambert
 * 此Component依赖AppComponent，由于AppComponent使用了@Singleton，此处只能使用自定义@Scope(@PerActivity)
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {Module2Module.class})
public interface Module2Component {
    Activity getActivity();

    Fragment getFragment();

    void inject(GankPresenter gankPresenter);

    void inject(LeisureReadPresenter leisureReadPresenter);
}
