package com.heyongrui.component;

import android.util.Log;

import com.heyongrui.base.app.BaseApplication;

public class ComponentLaunchApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("", "onCreate: ");
    }
}
