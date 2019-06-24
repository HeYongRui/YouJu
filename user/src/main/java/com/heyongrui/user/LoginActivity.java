package com.heyongrui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;

@Route(path = ConfigConstants.PATH_LOGIN)
public class LoginActivity extends BaseActivity {

    @Autowired(name = ConfigConstants.PATH_TARGET)
    String targetPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv_login) {
                    if (!TextUtils.isEmpty(targetPath)) {
                        ARouter.getInstance().build(targetPath).navigation();
                    }
                    finish();
                }
            }
        }, R.id.tv_login);
    }
}
