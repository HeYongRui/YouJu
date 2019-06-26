package com.heyongrui.module.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;
import com.heyongrui.module.ModuleFragment;

@Route(path = ConfigConstants.PATH_MODULE_PROVIDER)
public class IModuleFragmentProvider implements IFragmentProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment newInstance(Object... args) {
        return ModuleFragment.getInstance();
    }
}
