package com.heyongrui.base.dagger;


import com.heyongrui.base.app.BaseApplication;
import com.heyongrui.base.assist.AppData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 2019/8/26
 * lambert
 */

@Module
public class AppModule {
    private BaseApplication baseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Provides
    @Singleton
    BaseApplication providesApplication() {
        return baseApplication;
    }


    /*
     * @Inject AppData使用单例，由于AppModule在BaseApplication中初始，所以@Inject AppData是全局单例(而且AppComponent也必须加上@Singleton)
     */
    @Provides
    @Singleton
    AppData provideAppData() {
        return new AppData();
    }
}
