package com.heyongrui.user;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;

@Route(path = ConfigConstants.PATH_USER_PROVIDER)
public class IUserFragmentProvider implements IFragmentProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment newInstance(Object... args) {
        return UserFragment.getInstance();
    }
}
