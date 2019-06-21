package com.heyongrui.component;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.base.BaseActivity;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/test/activity")
public class ComponentActivity extends BaseActivity {

    @Autowired
    Bundle bundle;
    @Autowired(name = "key3")
    String key;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_component;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        Log.e(TAG, "key: " + key);
        if (bundle != null) {
            String test = bundle.getString("test");
            Log.e(TAG, "test: " + test);
        }
    }
}
