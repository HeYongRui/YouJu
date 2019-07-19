package com.heyongrui.module.textword.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.PoetryDto;
import com.heyongrui.module.data.dto.TodayRecommendPoemDto;
import com.heyongrui.module.textword.contract.PoetryContract;
import com.heyongrui.module.textword.presenter.PoetryPresenter;
import com.heyongrui.network.configure.ResponseDisposable;
import com.heyongrui.network.configure.RxHelper;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class PoetryFragment extends BaseFragment<PoetryContract.Presenter> implements PoetryContract.View {

    private SmartRefreshLayout refreshLayout;
    private ModuleSectionAdapter mPoetryAdapter;
    private int mType;

    public static PoetryFragment getInstance(int type) {
        PoetryFragment fragment = new PoetryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected PoetryContract.Presenter setPresenter() {
        return new PoetryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_poetry;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        refreshLayout = mView.findViewById(R.id.refresh_layout);
        StoreHouseHeader storeHouseHeader = mView.findViewById(R.id.store_house_header);
        initSwipeRefresh(refreshLayout, storeHouseHeader);
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
    }

    private void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//        storeHouseHeader.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                switch (mType) {
                    case 1:
                        refreshLayout.finishLoadMore();
                        refreshLayout.setNoMoreData(false);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                switch (mType) {
                    case 1:
                        refreshLayout.setNoMoreData(false);
                        mPresenter.getTodayRecommendPoem();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mPoetryAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        mPoetryAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(mContext, ConvertUtils.dp2px(1)));
        mPoetryAdapter.setOnItemClickListener((adapter, view, position) -> {
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt("type");
        }
        switch (mType) {
            case 1:
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_today_poem, null);
                mPoetryAdapter.setHeaderView(headerView);
                break;
        }
        refreshLayout.autoRefresh();
    }

    @Override
    public void getPoetrySuccess(PoetryDto poetryDto) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(true);
    }

    @Override
    public void getPoetryFail(int errorCode, String errorMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(true);
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void getTodayRecommendPoemSuccess(TodayRecommendPoemDto todayRecommendPoemDto) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(false);
        List<ModuleSectionEntity> newDataList = new ArrayList<>();
        if (todayRecommendPoemDto != null) {
            Observable.just(todayRecommendPoemDto.getCover()).map(url -> GlideUtil.getBitmap(mContext, url, false, null, null))
                    .compose(RxHelper.rxSchedulerHelper()).subscribe(new ResponseDisposable<Bitmap>(mContext) {
                @Override
                protected void onSuccess(Bitmap bitmap) {

                }

                @Override
                protected void onFailure(int errorCode, String errorMsg) {

                }
            });
            List<TodayRecommendPoemDto.DataBean> dataBeanList = todayRecommendPoemDto.getData();
            if (dataBeanList != null) {
                for (TodayRecommendPoemDto.DataBean dataBean : dataBeanList) {
                    newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.TODAY_POEM, dataBean));
                }
            }
        }
        mPoetryAdapter.replaceData(newDataList);
    }

    @Override
    public void getTodayRecommendPoemFail(int errorCode, String errorMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(false);
        ToastUtils.showShort(errorMsg);
    }
}
