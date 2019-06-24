package com.heyongrui.youju;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
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
                    ARouter.getInstance().build(ConfigConstants.PATH_MAIN).navigation(SplashActivity.this, new NavigationCallback() {
                        @Override
                        public void onFound(Postcard postcard) {
                        }

                        @Override
                        public void onLost(Postcard postcard) {
                            ARouter.getInstance().build(ConfigConstants.PATH_USER).navigation();
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                        }
                    });
                }
            }
        }, R.id.tv);
    }
}
