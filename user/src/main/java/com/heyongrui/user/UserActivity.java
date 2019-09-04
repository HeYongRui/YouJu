package com.heyongrui.user;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;

@Route(path = ConfigConstants.PATH_USER)
public class UserActivity extends BaseActivity {

    @Autowired
    Bundle bundle;
    @Autowired(name = "key3")
    String key;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.e(TAG, "key: " + key);
        if (bundle != null) {
            String test = bundle.getString("test");
            Log.e(TAG, "test: " + test);
        }
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.tv) {
            }
        }, R.id.tv);
    }
}
