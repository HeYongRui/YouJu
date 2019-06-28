package com.heyongrui.main;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;

import java.util.LinkedHashMap;

@Route(path = ConfigConstants.PATH_MAIN)
public class MainActivity extends BaseActivity {

    private final String[] TAB_PATH_ARRAY = {ConfigConstants.PATH_HOME_PROVIDER, ConfigConstants.PATH_MODULE_PROVIDER, ConfigConstants.PATH_USER_PROVIDER};
    private LinkedHashMap<String, BaseFragment> mRootFragmentMap = new LinkedHashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initFragment();
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.btn1) {
                selectedTab(0);
            } else if (id == R.id.btn2) {
                selectedTab(1);
            } else if (id == R.id.btn3) {
                selectedTab(2);
            }
        }, R.id.btn1, R.id.btn2, R.id.btn3);
    }

    private void initFragment() {
        for (String tabPath : TAB_PATH_ARRAY) {
            BaseFragment baseFragment = mRootFragmentMap.get(tabPath);
            if (baseFragment == null) {
                IFragmentProvider homeFragmentProvider = (IFragmentProvider) ARouter.getInstance().build(tabPath).navigation();
                if (homeFragmentProvider != null) {
                    mRootFragmentMap.put(tabPath, homeFragmentProvider.newInstance());
                }
            }
        }
        loadMultipleRootFragment(R.id.home_content_fragment, 0, mRootFragmentMap.values().toArray(new BaseFragment[mRootFragmentMap.size()]));
    }

    public void selectedTab(int position) {
        if (position < 0 || position >= TAB_PATH_ARRAY.length) return;
        String tabPath = TAB_PATH_ARRAY[position];
        if (TextUtils.isEmpty(tabPath)) return;
        BaseFragment baseFragment = mRootFragmentMap.get(tabPath);
        if (baseFragment == null) return;
        showHideFragment(baseFragment);
    }
}
