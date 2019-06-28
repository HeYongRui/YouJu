package com.heyongrui.module;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;

public class ModuleFragment extends BaseFragment {

    public static ModuleFragment getInstance() {
        return new ModuleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.tv_title) {
                ARouter.getInstance().build(ConfigConstants.PATH_KAIYAN_LIST).withInt("type", 1).navigation();
            }
        }, R.id.tv_title);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
