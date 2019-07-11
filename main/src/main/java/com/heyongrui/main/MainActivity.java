package com.heyongrui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.AppManager;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.provider.IFragmentProvider;

import java.util.LinkedHashMap;

@Route(path = ConfigConstants.PATH_MAIN)
public class MainActivity extends BaseActivity {

    private final String[] TAB_PATH_ARRAY = {ConfigConstants.PATH_HOME_PROVIDER, ConfigConstants.PATH_MODULE_PROVIDER, ConfigConstants.PATH_MODULE2_PROVIDER, ConfigConstants.PATH_USER_PROVIDER};
    private LinkedHashMap<String, BaseFragment> mRootFragmentMap = new LinkedHashMap<>();
    private long exitTime = 0;


    private AHBottomNavigation bottomNavigation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        initFragment();
    }

    private void initFragment() {
        for (String tabPath : TAB_PATH_ARRAY) {
            //初始化Fragment
            BaseFragment baseFragment = mRootFragmentMap.get(tabPath);
            if (baseFragment == null) {
                IFragmentProvider homeFragmentProvider = (IFragmentProvider) ARouter.getInstance().build(tabPath).navigation();
                if (homeFragmentProvider != null) {
                    baseFragment = homeFragmentProvider.newInstance();
                    mRootFragmentMap.put(tabPath, baseFragment);
                }
            }
            //初始化关联BottomTabBar
            int tabTitleId = 0, tabIconId = 0;
            Bundle arguments = baseFragment.getArguments();
            if (arguments != null) {
                tabTitleId = arguments.getInt("tabTitleId");
                tabIconId = arguments.getInt("tabIconId");
            }
            tabTitleId = tabTitleId == 0 ? R.string.app_name : tabTitleId;
            tabIconId = tabIconId == 0 ? R.drawable.placeholder : tabIconId;
            AHBottomNavigationItem item = new AHBottomNavigationItem(tabTitleId, tabIconId, R.color.gray);
            bottomNavigation.addItem(item);
        }

        loadMultipleRootFragment(R.id.content_fragment, 0, mRootFragmentMap.values().toArray(new BaseFragment[mRootFragmentMap.size()]));

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorAccent));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            selectedTab(position);
            return true;
        });
    }

    public void selectedTab(int position) {
        if (position < 0 || position >= TAB_PATH_ARRAY.length) return;
        String tabPath = TAB_PATH_ARRAY[position];
        if (TextUtils.isEmpty(tabPath)) return;
        BaseFragment baseFragment = mRootFragmentMap.get(tabPath);
        if (baseFragment == null) return;
        showHideFragment(baseFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("home_tab_position", bottomNavigation.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int homeTabPosition = savedInstanceState.getInt("home_tab_position", 0);
        selectedTab(homeTabPosition);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //双击退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort(getResources().getString(R.string.tip_click_again_exit));
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().exitApp();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
