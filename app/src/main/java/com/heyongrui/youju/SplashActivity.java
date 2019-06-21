package com.heyongrui.youju;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv) {
                    ARouter.getInstance().build("/main/activity").navigation();
                }
            }
        }, R.id.tv);
    }
}
