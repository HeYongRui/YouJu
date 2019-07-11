package com.heyongrui.module.mono.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.itemdecoration.Divider;
import com.heyongrui.base.widget.itemdecoration.DividerBuilder;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.MonoHistoryTeaDateDto;
import com.heyongrui.module.data.service.MonoSerevice;
import com.heyongrui.network.configure.ResponseDisposable;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_MONO_TEA_HISTORY_DATE)
public class MonoTeaHistoryDateActivity extends BaseActivity {

    SmartRefreshLayout refreshLayout;

    RxManager mRxManager;
    MonoSerevice mMonoSerevice;

    private ModuleSectionAdapter mMonoAdapter;
    private int mStart;
    private boolean mIsLastPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mono_tea_history_date;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRxManager = new RxManager();
        mMonoSerevice = new MonoSerevice();
        initView();
    }

    private void initView() {
        ImageView backIv = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        backIv.setImageDrawable(drawable);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }, R.id.iv_back);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
        refreshLayout = findViewById(R.id.refresh_layout);
        StoreHouseHeader storeHouseHeader = findViewById(R.id.store_house_header);
        initSwipeRefresh(refreshLayout, storeHouseHeader);
        refreshLayout.autoRefresh();
    }

    private void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(this, R.color.white));
//        storeHouseHeader.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mIsLastPage) {
                    refreshLayout.setNoMoreData(false);
                    refreshLayout.finishLoadMore();
                } else {
                    getTeaHistoryDate(false);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(true);
                getTeaHistoryDate(true);
            }
        });
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        mMonoAdapter = new ModuleSectionAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //防止Recyclerview局部刷新item闪烁
        ((SimpleItemAnimator) recyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);
        mMonoAdapter.bindToRecyclerView(recyclerView);
        int color = Color.parseColor("#dadada");
        int padding = ConvertUtils.dp2px(0.2f);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(this) {
            @Override
            public Divider getDivider(int itemPosition) {
                DividerBuilder dividerBuilder = new DividerBuilder();
                dividerBuilder.setLeftSideLine(true, color, padding, 0, 0);
                dividerBuilder.setTopSideLine(true, color, padding, 0, 0);
                dividerBuilder.setRightSideLine(true, color, padding, 0, 0);
                dividerBuilder.setBottomSideLine(true, color, padding, 0, 0);
                Divider divider = dividerBuilder.create();
                return divider;
            }
        });
        mMonoAdapter.setOnItemClickListener((adapter, view, position) -> {
            MonoHistoryTeaDateDto.RecentTeaBean recentTeaBean = mMonoAdapter.getData().get(position).getRecentTeaBean();
            if (recentTeaBean == null) return;
            int id = recentTeaBean.getId();
            int kind = recentTeaBean.getKind();
            String release_date = recentTeaBean.getRelease_date();
            ARouter.getInstance().build(ConfigConstants.PATH_MONO_TEA)
                    .withInt("source_type", 1)
                    .withInt("id", id)
                    .withInt("kind", kind)
                    .withString("release_date", release_date)
                    .navigation();
        });
    }

    private void getTeaHistoryDate(boolean isClearData) {
        mStart = isClearData ? 0 : mStart + 30;
        mRxManager.add(mMonoSerevice.getTeaHistoryDate(mStart)
                .subscribeWith(new ResponseDisposable<MonoHistoryTeaDateDto>(this, true) {
                    @Override
                    protected void onSuccess(MonoHistoryTeaDateDto monoHistoryTeaDateDto) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        if (isClearData) {
                            mMonoAdapter.setNewData(null);
                        }
                        if (monoHistoryTeaDateDto == null) return;
                        mIsLastPage = monoHistoryTeaDateDto.isIs_last_page();
                        List<MonoHistoryTeaDateDto.RecentTeaBean> recent_tea = monoHistoryTeaDateDto.getRecent_tea();
                        if (recent_tea == null || recent_tea.isEmpty()) return;
                        for (MonoHistoryTeaDateDto.RecentTeaBean recentTeaBean : recent_tea) {
                            mMonoAdapter.addData(new ModuleSectionEntity(ModuleSectionEntity.MONO_HISTORY_DATE, 1, recentTeaBean));
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        mRxManager.clear();
        super.onDestroy();
    }
}
