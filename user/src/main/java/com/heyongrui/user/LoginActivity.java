package com.heyongrui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;

@Route(path = ConfigConstants.PATH_LOGIN)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Autowired(name = ConfigConstants.PATH_TARGET)
    String mTargetPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_login) {
            if (!TextUtils.isEmpty(mTargetPath)) {
                ARouter.getInstance().build(mTargetPath).with(getIntent().getExtras())
                        .withBoolean(ConfigConstants.IS_NEED_INTERCEPT, false)
                        .navigation(this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                finish();
                            }
                        });
            } else {
                ARouter.getInstance().build(ConfigConstants.PATH_USER).greenChannel()
                        .navigation(this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                finish();
                            }
                        });
            }
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(this, R.id.tv_login);
    }
}
