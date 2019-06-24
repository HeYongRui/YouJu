package com.heyongrui.main;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;

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
