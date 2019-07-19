package com.heyongrui.module.textword.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_POETRY)
public class PoetryActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poetry;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.iv_search) {

            }
        }, R.id.iv_back, R.id.iv_search);
        //设置字体样式
        TextView tvTitle = findViewById(R.id.tv_title);
        UiUtil.setFontStyle(tvTitle, "fonts/font_hwzs.ttf");
        //图标着色
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);
        //初始化页面
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        initTabViewPager(tabLayout, viewPager);
    }

    private void initTabViewPager(TabLayout tabLayout, ViewPager viewPager) {
        List<BaseFragment> fragments = new ArrayList<>();
        String[] pageTitleArray = new String[]{getString(R.string.a), getString(R.string.poetry), getString(R.string.green_tea)};
        fragments.add(PoetryFragment.getInstance(1));
        fragments.add(PoetryFragment.getInstance(2));
        fragments.add(PoetryFragment.getInstance(3));
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return pageTitleArray[position];
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //遍历重置TabLayout的字体样式
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            LinearLayout linearLayout = tabAt.view;
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                View childAt = linearLayout.getChildAt(j);
                if (childAt instanceof TextView) {
                    UiUtil.setFontStyle((TextView) childAt, "fonts/font_hwzs.ttf");
                }
            }
        }
    }
}
