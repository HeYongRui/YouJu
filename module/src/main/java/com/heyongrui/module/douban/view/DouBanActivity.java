package com.heyongrui.module.douban.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.itemdecoration.Divider;
import com.heyongrui.base.widget.itemdecoration.DividerBuilder;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.DouBanDto;
import com.heyongrui.module.douban.contract.DouBanContract;
import com.heyongrui.module.douban.presenter.DouBanPresenter;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_DOUBAN)
public class DouBanActivity extends BaseActivity<DouBanContract.Presenter> implements DouBanContract.View {

    private TabLayout tabLayout;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private ModuleSectionAdapter mGankAdapter;
    private int mPageStart = 0, mPageSize = 10;
    private String mType, mSort;
    private boolean mIsLastPage = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban;
    }

    @Override
    protected DouBanContract.Presenter setPresenter() {
        return new DouBanPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }, R.id.iv_back);

        //图标着色
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);

        tabLayout = findViewById(R.id.tab_layout);

        //初始化刷新控件
        StoreHouseHeader storeHouseHeader = findViewById(R.id.store_house_header);
        refreshLayout = findViewById(R.id.refresh_layout);
        initSwipeRefresh(refreshLayout, storeHouseHeader);

        //初始化列表
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);

        //初始化类型下拉选择器
        Spinner spinnerType = findViewById(R.id.spinner_type);
        initTypeSpinner(spinnerType);
    }

    private void initTypeSpinner(Spinner spinnerType) {
        String[] itemArrays = new String[]{getString(R.string.movie), getString(R.string.tv)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemArrays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(getString(R.string.movie), itemArrays[i])) {
                    mType = "movie";
                } else if (TextUtils.equals(getString(R.string.tv), itemArrays[i])) {
                    mType = "tv";
                }
                if (mPresenter != null) {
                    mPresenter.getIndexTags(mType, "index");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new Handler().postDelayed(() -> spinnerType.setDropDownVerticalOffset(spinnerType.getHeight()), 1000);
    }

    private void initSortSpinner(Spinner spinnerSort) {
        if (spinnerSort.getAdapter() != null) return;
        String[] itemArrays = new String[]{getString(R.string.heat), getString(R.string.time), getString(R.string.evaluate)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemArrays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(adapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(getString(R.string.heat), itemArrays[i])) {
                    mSort = "recommend";
                } else if (TextUtils.equals(getString(R.string.time), itemArrays[i])) {
                    mSort = "time";
                } else if (TextUtils.equals(getString(R.string.evaluate), itemArrays[i])) {
                    mSort = "rank";
                }
                if (mPresenter != null) {
                    mPageStart = 0;
                    String tag = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
                    mPresenter.getSubjectsData(mType, tag, mSort, mPageSize, mPageStart);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new Handler().postDelayed(() -> spinnerSort.setDropDownVerticalOffset(spinnerSort.getHeight()), 1000);
    }

    private void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(this, R.color.white));
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mIsLastPage) {
                    resetRefreshLayout(true, true, true);
                } else {
                    mPageStart = mGankAdapter.getData().size();
                    if (mPresenter != null) {
                        String tag = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
                        mPresenter.getSubjectsData(mType, tag, mSort, mPageSize, mPageStart);
                    }
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageStart = 0;
                if (mPresenter != null) {
                    String tag = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
                    mPresenter.getSubjectsData(mType, tag, mSort, mPageSize, mPageStart);
                }
            }
        });
    }

    private void resetRefreshLayout(boolean isFinishLoadMore, boolean isFinishRefresh, boolean noMoreData) {
        if (isFinishLoadMore) {
            refreshLayout.finishLoadMore();
        }
        if (isFinishRefresh) {
            refreshLayout.finishRefresh();
        }
        refreshLayout.setNoMoreData(noMoreData);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mGankAdapter = new ModuleSectionAdapter(data);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mGankAdapter.bindToRecyclerView(recyclerView);
        int dp10 = ConvertUtils.dp2px(10);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(this) {
            @Override
            public Divider getDivider(int itemPosition) {
                DividerBuilder dividerBuilder = new DividerBuilder();
                dividerBuilder.setBottomSideLine(true, Color.TRANSPARENT, dp10, 0, 0);
                dividerBuilder.setTopSideLine(true, Color.TRANSPARENT, dp10, 0, 0);
                dividerBuilder.setLeftSideLine(true, Color.TRANSPARENT, dp10, 0, 0);
                dividerBuilder.setRightSideLine(true, Color.TRANSPARENT, dp10, 0, 0);
                Divider divider = dividerBuilder.create();
                return divider;
            }
        });
        mGankAdapter.setSpanSizeLookup((gridLayoutManager, position) -> data.get(position).getSpanSize());
        mGankAdapter.setOnItemClickListener((adapter, view, position) -> {
            DouBanDto.SubjectsBean subjectsBean = mGankAdapter.getData().get(position).getSubjectsBean();
            if (subjectsBean != null) {
                ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", subjectsBean.getUrl()).navigation();
            }
        });
    }

    @Override
    public void getIndexTagsSuccess(List<String> tagsList) {
        setTagsData(tabLayout, tagsList);
        initSortSpinner(findViewById(R.id.spinner_sort));
    }

    @Override
    public void getSubjectsDataSuccess(DouBanDto douBanDto) {
        resetRefreshLayout(true, true, false);
        List<ModuleSectionEntity> newDataList = new ArrayList<>();
        if (douBanDto != null) {
            List<DouBanDto.SubjectsBean> subjectsBeanList = douBanDto.getSubjects();
            if (subjectsBeanList != null) {
                for (DouBanDto.SubjectsBean subjectsBean : subjectsBeanList) {
                    newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.DOUBAN_MOVIE, 1, subjectsBean));
                }
            }
        }
        if (mPageStart == 0) {
            mGankAdapter.replaceData(newDataList);
            recyclerView.scrollToPosition(0);
        } else {
            mGankAdapter.addData(newDataList);
        }
    }

    @Override
    public void getSubjectsDataFail(int errorCode, String errorMsg) {
        resetRefreshLayout(true, true, false);
    }

    private void setTagsData(TabLayout tabLayout, List<String> tagsList) {
        if (tagsList == null) return;
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mPresenter != null) {
                    mPageStart = 0;
                    mPresenter.getSubjectsData(mType, tab.getText().toString(), mSort, mPageSize, mPageStart);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.removeAllTabs();
        for (String tag : tagsList) {
            tabLayout.addTab(creatTab(tabLayout, tag, tag));
        }
    }

    private TabLayout.Tab creatTab(TabLayout tabLayout, String tabText, Object tag) {
        TabLayout.Tab tagTab = tabLayout.newTab();
        tagTab.setText(tabText);
        tagTab.setTag(tag);
        return tagTab;
    }
}
