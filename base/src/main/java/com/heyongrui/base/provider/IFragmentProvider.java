package com.heyongrui.base.provider;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.heyongrui.base.base.BaseFragment;

public interface IFragmentProvider extends IProvider {
    BaseFragment newInstance(Object... args);
}
