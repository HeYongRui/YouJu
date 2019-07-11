package com.heyongrui.module2.gank.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.imagewatcher.ImageWatcherHelper;
import com.heyongrui.module2.R;
import com.heyongrui.module2.adapter.Module2SectionAdapter;
import com.heyongrui.module2.adapter.Module2SectionEntity;
import com.heyongrui.module2.data.dto.WelfareDto;
import com.heyongrui.module2.gank.contract.WelfareContract;
import com.heyongrui.module2.gank.presenter.WelfarePresenter;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.He on 2019/3/10.
 */

@Route(path = ConfigConstants.PATH_WELFARE)
public class WelfareActivity extends BaseActivity<WelfareContract.Presenter> implements WelfareContract.View {

    SmartRefreshLayout refreshLayout;

    private int mPerPage = 10, mPage = 1;
    private boolean mIsLastPage;
    private Module2SectionAdapter mGankAdapter;
    private ImageWatcherHelper mIwHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected WelfareContract.Presenter setPresenter() {
        return new WelfarePresenter();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        mIwHelper = mPresenter.getImageWatcher();
    }

    private void initView() {
        ImageView backIv = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, Color.WHITE);
        backIv.setImageDrawable(drawable);
        refreshLayout = findViewById(R.id.refreshLayout);
        StoreHouseHeader header = findViewById(R.id.header);
        initSwipeRefresh(refreshLayout, header);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                onBackPressed();
            }
        }, R.id.iv_back);
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
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    refreshLayout.setNoMoreData(false);
                } else {
                    getWelfare(false);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getWelfare(true);
            }
        });
        smartRefreshLayout.autoRefresh();
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<Module2SectionEntity> data = new ArrayList<>();
        mGankAdapter = new Module2SectionAdapter(data);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mGankAdapter.bindToRecyclerView(recyclerView);
        mGankAdapter.setSpanSizeLookup((gridLayoutManager, position) -> data.get(position).getSpanSize());
        mGankAdapter.setOnItemClickListener((adapter, view, position) -> {//点击预览大图
            ImageView coverIv = view.findViewById(R.id.iv);
            if (coverIv == null) return;
            SparseArray<ImageView> imageViewSparseArray = new SparseArray<>();
            imageViewSparseArray.put(0, coverIv);
            List<Module2SectionEntity> gankMultipleItemList = new ArrayList<>();
            gankMultipleItemList.add(mGankAdapter.getData().get(position));
            mIwHelper.show(coverIv, imageViewSparseArray, gankMultipleItemList);
        });
    }

    private void getWelfare(boolean isClearData) {
        if (isClearData) {
            mPage = 1;
            mIsLastPage = false;
            mGankAdapter.setNewData(null);
        } else {
            mPage += 1;
        }
        if (mPresenter != null) {
            mPresenter.getWelfare(mPerPage, mPage);
        }
    }

    @Override
    public void getWelfareSuccess(WelfareDto welfareDto) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(true);
        List<Module2SectionEntity> addDataList = new ArrayList<>();
        if (welfareDto != null) {
            if (!welfareDto.isError()) {
                List<WelfareDto.WelfareBean> welfareBeanList = welfareDto.getResults();
                if (welfareBeanList != null && !welfareBeanList.isEmpty()) {
                    if (welfareBeanList.size() < mPerPage) {
                        mIsLastPage = true;
                    }
                    for (WelfareDto.WelfareBean welfareBean : welfareBeanList) {
                        addDataList.add(new Module2SectionEntity(Module2SectionEntity.WELFARE, welfareBean));
                    }
                } else {
                    mIsLastPage = true;
                }
            }
        }
        mGankAdapter.addData(addDataList);
    }

    @Override
    public void getWelfareFail(int errorCode, String errorMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(true);
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void onBackPressedSupport() {
        if (mIwHelper == null) {
            super.onBackPressedSupport();
        } else {
            if (!mIwHelper.handleBackPressed()) {
                super.onBackPressedSupport();
            }
        }
    }
}
