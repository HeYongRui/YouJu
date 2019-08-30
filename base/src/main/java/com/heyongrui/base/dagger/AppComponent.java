package com.heyongrui.base.dagger;


import com.heyongrui.base.assist.AppData;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 2019/8/26
 * lambert
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    /*
     * 暴露给依赖者Component(依赖者若使用@Component，此处必须暴露，否则依赖者无法@Inject AppData；依赖者若使用@Subcomponent，此处可以不暴露依赖者也可@Inject AppData)
     */
    AppData appData();
}
