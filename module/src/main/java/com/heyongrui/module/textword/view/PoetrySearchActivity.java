package com.heyongrui.module.textword.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_POETRY_SEARCH)
public class PoetrySearchActivity extends BaseActivity {

    private ViewPager viewPager;
    private EditText etSearch;
    private float scrollPercent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poetry_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back || id == R.id.iv_back2) {
                finish();
            } else if (id == R.id.tv_search) {
                doSearch(etSearch.getText().toString());
            }
        }, R.id.iv_back, R.id.iv_back2, R.id.tv_search);
        //初始化view
        etSearch = findViewById(R.id.et_search);
        TextView tvSearch = findViewById(R.id.tv_search);
        UiUtil.setFontStyle(etSearch, "fonts/font_hwzs.ttf");
        UiUtil.setFontStyle(tvSearch, "fonts/font_hwzs.ttf");
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_search, ContextCompat.getColor(this, R.color.text_color));
        drawable.setBounds(0, 0, 60, 60);
        etSearch.setCompoundDrawables(drawable, null, null, null);

        View cvSearchBar = findViewById(R.id.cv_search_bar);
        ImageView ivBack2 = findViewById(R.id.iv_back2);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        int dp20 = ConvertUtils.dp2px(20);
        int dp30 = ConvertUtils.dp2px(30);
        appBarLayout.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout1, i) -> {
            float percent = (float) Math.abs(i) / Math.abs(appBarLayout1.getTotalScrollRange());
            if (scrollPercent == percent) return;
            ViewGroup.LayoutParams layoutParams = cvSearchBar.getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                if (percent == 0) {
                    ivBack2.setVisibility(View.GONE);
                    ((ConstraintLayout.LayoutParams) layoutParams).leftMargin = dp20;
                } else if (percent == 1) {
                    ((ConstraintLayout.LayoutParams) layoutParams).leftMargin = dp20 + dp30;
                    ivBack2.setVisibility(View.VISIBLE);
                } else {
                    ivBack2.setVisibility(View.GONE);
                    ((ConstraintLayout.LayoutParams) layoutParams).leftMargin = (int) (dp20 + percent * dp30);
                }
                cvSearchBar.setLayoutParams(layoutParams);
            }
            scrollPercent = percent;
        });
        //初始化页面
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        initTabViewPager(tabLayout, viewPager);
        //设置监听器
        etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String keywords = etSearch.getText().toString();
                if (TextUtils.isEmpty(keywords)) {
                    return false;
                } else {
                    KeyboardUtils.hideSoftInput(etSearch);
                    doSearch(keywords);
                    return true;
                }
            }
            return false;
        });
    }

    private void doSearch(String keywords) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null && adapter instanceof FragmentPagerAdapter) {
            for (int i = 0; i < adapter.getCount(); i++) {
                Fragment item = ((FragmentPagerAdapter) adapter).getItem(i);
                if (item != null && item instanceof PoetrySearchFragment) {
                    ((PoetrySearchFragment) item).searchPoem(keywords, i == viewPager.getCurrentItem());
                }
            }
        }
    }

    private void initTabViewPager(TabLayout tabLayout, ViewPager viewPager) {
        List<BaseFragment> fragments = new ArrayList<>();
        //1-原文 2-标题 4-作者
        String[] pageTitleArray = new String[]{getString(R.string.original), getString(R.string.title), getString(R.string.author)};
        fragments.add(PoetrySearchFragment.getInstance(1));
        fragments.add(PoetrySearchFragment.getInstance(2));
        fragments.add(PoetrySearchFragment.getInstance(4));
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
        viewPager.setOffscreenPageLimit(fragments.size());
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
