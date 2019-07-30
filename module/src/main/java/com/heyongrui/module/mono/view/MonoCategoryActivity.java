package com.heyongrui.module.mono.view;

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
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.MonoCategoryDto;
import com.heyongrui.module.data.service.MonoSerevice;
import com.heyongrui.network.configure.ResponseDisposable;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by lambert on 2018/11/21.
 */

@Route(path = ConfigConstants.PATH_MONO_CATEGORY)
public class MonoCategoryActivity extends BaseActivity {

    private TextView titleTv;
    private SmartRefreshLayout refreshLayout;

    private Integer mStart;
    private boolean mIsLastPage;
    private ModuleSectionAdapter monoAdapter;

    private RxManager mRxManager;
    private MonoSerevice monoSerevice;

    @Autowired(name = "category_id")
    int mCategoryId = -1;
    @Autowired(name = "category_name")
    String mCategoryName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mono_category;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        ImageView backIv = findViewById(R.id.iv_back);
        titleTv = findViewById(R.id.tv_title);
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
                    refreshLayout.setNoMoreData(true);
                    refreshLayout.finishLoadMore();
                } else {
                    getCategory(false);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                getCategory(true);
            }
        });
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        monoAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        monoAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(this, ConvertUtils.dp2px(1)));
        monoAdapter.setOnItemClickListener((adapter, view, position) -> {
            MonoCategoryDto.MeowsBean meowsBean = monoAdapter.getData().get(position).getMeowsBean();
            if (meowsBean == null) return;
            MonoCategoryDto.MeowBean meow = meowsBean.getMeow();
            if (meow == null) return;
            getMeowDetail(meow.getId());
        });
    }

    private void getMeowDetail(int meow_id) {
        mRxManager.add(monoSerevice.getMeowDetail(meow_id)
                .subscribeWith(new ResponseDisposable<ResponseBody>(this, true) {
                    @Override
                    protected void onSuccess(ResponseBody responseBody) {
                        try {
                            String xmlContent = responseBody.string();
                            ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("htmlContent", xmlContent).navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    private void initData() {
        mRxManager = new RxManager();
        monoSerevice = new MonoSerevice();
        titleTv.setText(TextUtils.isEmpty(mCategoryName) ? "" : mCategoryName);
        if (mCategoryId == -1) return;
        getCategory(true);
    }

    private void getCategory(boolean is_clear_data) {
        mStart = is_clear_data ? 0 : mStart + 1;
        mRxManager.add(monoSerevice.getCategory(mCategoryId, mStart)
                .subscribeWith(new ResponseDisposable<MonoCategoryDto>(this, true) {
                    @Override
                    protected void onSuccess(MonoCategoryDto monoCategoryDto) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                        if (monoCategoryDto == null) return;
                        mIsLastPage = monoCategoryDto.isIs_last_page();
                        List<MonoCategoryDto.MeowsBean> meows = monoCategoryDto.getMeows();
                        if (meows == null || meows.isEmpty()) return;
                        if (is_clear_data) {
                            monoAdapter.getData().clear();
                        }
                        for (MonoCategoryDto.MeowsBean meowsBean : meows) {
                            monoAdapter.addData(new ModuleSectionEntity(ModuleSectionEntity.MONO_CATEGORY, meowsBean));
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        refreshLayout.finishLoadMore();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
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
