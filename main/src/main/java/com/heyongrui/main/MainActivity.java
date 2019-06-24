package com.heyongrui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;

import java.util.LinkedHashMap;

@Route(path = ConfigConstants.PATH_MAIN)
public class MainActivity extends BaseActivity {

    private final String[] TAB_PATH_ARRAY = {ConfigConstants.PATH_HOME_PROVIDER, ConfigConstants.PATH_USER_PROVIDER};
    private LinkedHashMap<String, BaseFragment> mRootFragmentMap = new LinkedHashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        for (String tabPath : TAB_PATH_ARRAY) {
            addFragment(tabPath);
        }

        loadMultipleRootFragment(R.id.home_content_fragment, 0, mRootFragmentMap.values().toArray(new BaseFragment[mRootFragmentMap.size()]));

        addOnClickListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.btn1) {
                    selectedTab(0);
                } else if (id == R.id.btn2) {
                    selectedTab(1);
                }
            }
        }, R.id.btn1, R.id.btn2);
    }

    private void addFragment(String providerPath) {
        BaseFragment baseFragment = mRootFragmentMap.get(providerPath);
        if (baseFragment == null) {
            IFragmentProvider homeFragmentProvider = (IFragmentProvider) ARouter.getInstance().build(providerPath).navigation();
            mRootFragmentMap.put(providerPath, homeFragmentProvider.newInstance());
        }
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
