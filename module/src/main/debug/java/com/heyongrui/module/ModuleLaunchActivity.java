package com.heyongrui.module;

import android.os.Bundle;

import com.heyongrui.base.base.BaseActivity;

public class ModuleLaunchActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loadRootFragment(R.id.content_fragment, ModuleFragment.getInstance(), true, true);
    }
}
