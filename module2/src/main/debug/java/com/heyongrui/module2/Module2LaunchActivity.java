package com.heyongrui.module2;

import android.os.Bundle;

import com.heyongrui.base.base.BaseActivity;

public class Module2LaunchActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loadRootFragment(R.id.content_fragment, Module2Fragment.getInstance(), true, true);
    }
}
