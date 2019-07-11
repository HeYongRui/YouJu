package com.heyongrui.module2.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;
import com.heyongrui.module2.Module2Fragment;

@Route(path = ConfigConstants.PATH_MODULE2_PROVIDER)
public class IModule2FragmentProvider implements IFragmentProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment newInstance(Object... args) {
        return Module2Fragment.getInstance();
    }
}
