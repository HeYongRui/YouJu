package com.heyongrui.main;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.tv_home) {
                    ARouter.getInstance().build(ConfigConstants.PATH_USER).withBoolean(ConfigConstants.IS_NEED_INTERCEPT, true).navigation();
                }
            }
        }, R.id.tv_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
