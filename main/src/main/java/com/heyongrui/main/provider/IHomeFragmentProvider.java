package com.heyongrui.main.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;
import com.heyongrui.main.HomeFragment;

@Route(path = ConfigConstants.PATH_HOME_PROVIDER)
public class IHomeFragmentProvider implements IFragmentProvider {
    @Override
    public BaseFragment newInstance(Object... args) {
        return HomeFragment.getInstance();
    }

    @Override
    public void init(Context context) {

    }
}
