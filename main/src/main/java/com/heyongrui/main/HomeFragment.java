package com.heyongrui.main;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.icon_mono);
        bundle.putInt("tabTitleId", R.string.gank);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.tv_home) {
                ARouter.getInstance().build(ConfigConstants.PATH_USER).withBoolean(ConfigConstants.IS_NEED_INTERCEPT, true).navigation();
            }
        }, R.id.tv_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
