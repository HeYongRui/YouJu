package com.heyongrui.module.textword.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.PoemGroupDetailDto;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.network.configure.ResponseDisposable;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Route(path = ConfigConstants.PATH_POEMGROUP_DETAIL)
public class PoemGroupDetailActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;

    @Autowired(name = "groupId")
    String mGroupId;
    @Autowired(name = "title")
    String mTitle;

    private TextService mTextService;
    private RxManager mRxManager;
    private int mPageNo = 1, mPageSize = 10;
    private boolean mIsLastPage = false;
    private ModuleSectionAdapter mPoemGroupAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poem_group_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }, R.id.iv_back);

        //设置字体样式
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(TextUtils.isEmpty(mTitle) ? "" : mTitle);
        UiUtil.setFontStyle(tvTitle, "fonts/font_hwzs.ttf");

        //图标着色
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);

        //随机设置背景
        ImageView ivBg = findViewById(R.id.iv_bg);
        int[] bgResIds = new int[]{R.drawable.bg_poem_1, R.drawable.bg_poem_2, R.drawable.bg_poem_3, R.drawable.bg_poem_4, R.drawable.bg_poem_5, R.drawable.bg_poem_6, R.drawable.bg_poem_7};
        GlideUtil.loadImage(this, bgResIds[new Random().nextInt(100) % 7], ivBg, null);

        StoreHouseHeader storeHouseHeader = findViewById(R.id.store_house_header);
        refreshLayout = findViewById(R.id.refresh_layout);
        initSwipeRefresh(refreshLayout, storeHouseHeader);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);

        //初始化数据
        mTextService = new TextService();
        mRxManager = new RxManager();
        getgroupDetail(mGroupId, mPageNo, mPageSize);
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
                    refreshLayout.setNoMoreData(true);
                } else {
                    mPageNo++;
                    getgroupDetail(mGroupId, mPageNo, mPageSize);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNo = 1;
                getgroupDetail(mGroupId, mPageNo, mPageSize);
            }
        });
        smartRefreshLayout.autoRefresh();
    }

    private void resetRefreshLayout(boolean noMoreData, String errorMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(noMoreData);
        if (!TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showShort(errorMsg);
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mPoemGroupAdapter = new ModuleSectionAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPoemGroupAdapter.bindToRecyclerView(recyclerView);
    }

    public void getgroupDetail(String groupId, int pageNo, int pageSize) {
        mRxManager.add(mTextService.groupDetail(groupId, pageNo, pageSize)
                .subscribeWith(new ResponseDisposable<PoemGroupDetailDto>(this, true) {
                    @Override
                    protected void onSuccess(PoemGroupDetailDto poemGroupDetailDto) {
                        resetRefreshLayout(false, null);
                        mIsLastPage = true;
                        List<ModuleSectionEntity> newDataList = new ArrayList<>();
                        if (poemGroupDetailDto != null) {
                            List<PoemGroupDetailDto.DataBean> dataBeanList = poemGroupDetailDto.getData();
                            if (dataBeanList != null) {
                                mIsLastPage = dataBeanList.size() < mPageSize ? true : false;
                                for (PoemGroupDetailDto.DataBean dataBean : dataBeanList) {
                                    newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.POEM_GROUP, dataBean));
                                }
                            }
                        }
                        if (mPageNo == 1) {
                            mPoemGroupAdapter.replaceData(newDataList);
                        } else {
                            mPoemGroupAdapter.addData(newDataList);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        resetRefreshLayout(false, errorMsg);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (mRxManager != null) {
            mRxManager.clear();
        }
        mTextService = null;
        super.onDestroy();
    }
}
