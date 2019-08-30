package com.heyongrui.module.douban.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.assist.AppData;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.dagger.DaggerModuleComponent;
import com.heyongrui.module.dagger.ModuleModule;
import com.heyongrui.module.data.dto.DouBanDto;
import com.heyongrui.module.douban.contract.DouBanContract;
import com.heyongrui.module.douban.presenter.DouBanPresenter;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

@Route(path = ConfigConstants.PATH_DOUBAN)
public class DouBanActivity extends BaseActivity<DouBanContract.Presenter> implements DouBanContract.View, View.OnClickListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private TabLayout tabLayout;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private ModuleSectionAdapter mGankAdapter;
    private int mPageStart = 0, mPageSize = 10;
    private String mType, mSort;
    private boolean mIsLastPage = false;

    @Inject
    Lazy<AppData> mAppData;

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerModuleComponent
                .builder()
                .appComponent(getAppComponent())
                .moduleModule(new ModuleModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban;
    }

    @Override
    protected DouBanContract.Presenter setPresenter() {
        return new DouBanPresenter();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        mAppData.get().getBaseUrl();
        addOnClickListeners(this, R.id.iv_back);

        //图标着色
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);

        tabLayout = findViewById(R.id.tab_layout);

        //初始化刷新控件
        StoreHouseHeader storeHouseHeader = findViewById(R.id.store_house_header);
        refreshLayout = findViewById(R.id.refresh_layout);
        mPresenter.initSwipeRefresh(refreshLayout, storeHouseHeader, this);

        //初始化列表
        recyclerView = findViewById(R.id.recycler_view);
        mGankAdapter = mPresenter.initRecyclerView(recyclerView, this);

        //初始化类型下拉选择器
        mPresenter.initSpinner(findViewById(R.id.spinner_type), Arrays.asList(new String[]{getString(R.string.movie), getString(R.string.tv)}));
    }

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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DouBanDto.SubjectsBean subjectsBean = mGankAdapter.getData().get(position).getSubjectsBean();
        if (subjectsBean != null) {
            ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", subjectsBean.getUrl()).navigation();
        }
    }

    @Override
    public <T> void onSpinnerItemSelected(AdapterView<?> adapterView, T t, int position) {
        int id = adapterView.getId();
        if (id == R.id.spinner_sort) {//影视排序
            if (null != t && t instanceof String) {
                if (TextUtils.equals(getString(R.string.heat), (String) t)) {
                    mSort = "recommend";
                } else if (TextUtils.equals(getString(R.string.time), (String) t)) {
                    mSort = "time";
                } else if (TextUtils.equals(getString(R.string.evaluate), (String) t)) {
                    mSort = "rank";
                }
            }
            if (null != mPresenter) {
                mPageStart = 0;
                String tag = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString();
                mPresenter.getSubjectsData(mType, tag, mSort, mPageSize, mPageStart);
            }
        } else if (id == R.id.spinner_type) {//影视类型
            if (null != t && t instanceof String) {
                if (TextUtils.equals(getString(R.string.movie), (String) t)) {
                    mType = "movie";
                } else if (TextUtils.equals(getString(R.string.tv), (String) t)) {
                    mType = "tv";
                }
                if (mPresenter != null) {
                    mPresenter.getIndexTags(mType, "index");
                }
            }
        }
    }

    @Override
    public void getIndexTagsSuccess(List<String> tagsList) {
        if (null != mPresenter) {
            mPresenter.setTagsData(tabLayout, tagsList, new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPageStart = 0;
                    mPresenter.getSubjectsData(mType, tab.getText().toString(), mSort, mPageSize, mPageStart);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            mPresenter.initSpinner(findViewById(R.id.spinner_sort), Arrays.asList(new String[]{getString(R.string.heat), getString(R.string.time), getString(R.string.evaluate)}));
        }
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

    private void resetRefreshLayout(boolean isFinishLoadMore, boolean isFinishRefresh, boolean noMoreData) {
        if (isFinishLoadMore) {
            refreshLayout.finishLoadMore();
        }
        if (isFinishRefresh) {
            refreshLayout.finishRefresh();
        }
        refreshLayout.setNoMoreData(noMoreData);
    }
}
