package com.heyongrui.module.hitokoto.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.module.R;
import com.heyongrui.module.data.dto.DuJiTangDto;
import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.hitokoto.contract.HitokotoContract;
import com.heyongrui.module.hitokoto.presenter.HitokotoPresenter;

import java.util.Random;

@Route(path = ConfigConstants.PATH_HITOKOTO)
public class HitokotoActivity extends BaseActivity<HitokotoContract.Presenter> implements HitokotoContract.View {

    private TextView tvAuthor;
    private TextView tvContent;
    private FloatingActionButton fabRefresh;

    private boolean mIsLoadDone = true;
    //一言类型
    private final int ALL = 0;//全部
    private final int SOULPOSION = 1;//毒鸡汤
    private final int ANIM = 2;//动画
    private final int COMIC = 3;//漫画
    private final int GAME = 4;//游戏
    private final int NOVEL = 5;//小说
    private final int MYSELF = 6;//原创
    private final int INTERNET = 7;//网络
    private final int OTHER = 8;//其他

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hitokoto;
    }

    @Override
    protected HitokotoPresenter setPresenter() {
        return new HitokotoPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        initTabLayout(tabLayout);
        tvContent = findViewById(R.id.tv_content);
        tvAuthor = findViewById(R.id.tv_author);
        fabRefresh = findViewById(R.id.fab_refresh);

        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.fab_refresh) {
                tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).select();
                startRotateAnim(fabRefresh);
            }
        }, R.id.iv_back, R.id.fab_refresh);
    }

    private void initTabLayout(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabSelected(tab);
            }
        });
        tabLayout.removeAllTabs();
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.all), ALL), true);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.soulposion), SOULPOSION), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.anim), ANIM), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.comic), COMIC), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.game), GAME), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.novel), NOVEL), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.myself), MYSELF), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.internet), INTERNET), false);
        tabLayout.addTab(creatTab(tabLayout, getString(R.string.other), OTHER), false);
    }

    private void tabSelected(TabLayout.Tab tab) {
        if (tab == null) return;
        Object tag = tab.getTag();
        if (tag != null && tag instanceof Integer) {
            switch ((int) tag) {
                case ALL:
                    if (new Random().nextInt(10) % 2 == 0) {
                        getDuJiTang();
                    } else {
                        getHitokoto(null);
                    }
                    break;
                case SOULPOSION:
                    getDuJiTang();
                    break;
                case ANIM:
                    getHitokoto("a");
                    break;
                case COMIC:
                    getHitokoto("b");
                    break;
                case GAME:
                    getHitokoto("c");
                    break;
                case NOVEL:
                    getHitokoto("d");
                    break;
                case MYSELF:
                    getHitokoto("e");
                    break;
                case INTERNET:
                    getHitokoto("f");
                    break;
                case OTHER:
                    getHitokoto("g");
                    break;
                default:
                    break;
            }
        }
    }

    private TabLayout.Tab creatTab(TabLayout tabLayout, String tabText, Object tag) {
        TabLayout.Tab tagTab = tabLayout.newTab();
        tagTab.setText(tabText);
        tagTab.setTag(tag);
        return tagTab;
    }

    private void startRotateAnim(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                if (mIsLoadDone) {
                    animation.cancel();
                }
            }
        });
        objectAnimator.start();
        mIsLoadDone = false;
    }

    private void getHitokoto(String c) {
        if (mPresenter != null) {
            mPresenter.getHitokoto(c);
        }
    }

    private void getDuJiTang() {
        if (mPresenter != null) {
            mPresenter.getDuJiTang();
        }
    }

    @Override
    public void getDuJiTangSuccess(DuJiTangDto duJiTangDto) {
        mIsLoadDone = true;
        if (duJiTangDto == null) return;
        String content = duJiTangDto.getContent();
        String author = duJiTangDto.getAuthor();
        tvContent.setText(TextUtils.isEmpty(content) ? "" : content);
        tvAuthor.setText((TextUtils.isEmpty(author) ? "" : "─" + author));
    }

    @Override
    public void getHitokotoSuccess(HitokotoDto hitokotoDto) {
        mIsLoadDone = true;
        if (hitokotoDto == null) return;
        String content = hitokotoDto.getHitokoto();
        String author = hitokotoDto.getFrom();
        tvContent.setText("『" + (TextUtils.isEmpty(content) ? "" : content) + "』");
        tvAuthor.setText((TextUtils.isEmpty(author) ? "" : "─「" + author + "」"));
    }
}
