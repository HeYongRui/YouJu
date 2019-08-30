package com.heyongrui.module.douban.presenter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heyongrui.base.app.BaseApplication;
import com.heyongrui.base.assist.AppData;
import com.heyongrui.base.widget.ReSpinner;
import com.heyongrui.base.widget.itemdecoration.Divider;
import com.heyongrui.base.widget.itemdecoration.DividerBuilder;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.dagger.DaggerModuleComponent;
import com.heyongrui.module.dagger.ModuleModule;
import com.heyongrui.module.data.dto.DouBanDto;
import com.heyongrui.module.data.service.DouBanService;
import com.heyongrui.module.douban.contract.DouBanContract;
import com.heyongrui.network.configure.ResponseDisposable;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

public class DouBanPresenter extends DouBanContract.Presenter {

    @Inject
    DouBanService mDouBanService;
    @Inject
    Lazy<AppData> mAppData;

    public DouBanPresenter() {
        DaggerModuleComponent
                .builder()
                .appComponent(BaseApplication.getAppComponent())
                .moduleModule(new ModuleModule())
                .build()
                .inject(this);
    }

    @Override
    public void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader, OnRefreshLoadMoreListener onRefreshLoadMoreListener) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        if (null != onRefreshLoadMoreListener) {
            smartRefreshLayout.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener);
        }
    }

    @Override
    public ModuleSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        ModuleSectionAdapter moduleSectionAdapter = new ModuleSectionAdapter(data);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        moduleSectionAdapter.bindToRecyclerView(recyclerView);
        int dp10 = ConvertUtils.dp2px(10);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(mContext) {
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
        moduleSectionAdapter.setSpanSizeLookup((gridLayoutManager, position) -> data.get(position).getSpanSize());
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener);
        }
        return moduleSectionAdapter;
    }

    @Override
    public <T> void initSpinner(Spinner spinner, List<T> dataList) {
        List<String> stringList = new ArrayList<>();
        if (null != dataList) {
            for (T t : dataList) {
                if (t instanceof String) {
                    stringList.add((String) t);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.layout_spinner_dropdown_item, stringList);
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new ReSpinner.OnReItemSelectedListener(true) {
            @Override
            public void onReItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                T t = null;
                if (i >= 0) {
                    if (null != dataList) {
                        t = dataList.get(i);
                    }
                }
                if (null != mView) {
                    mView.onSpinnerItemSelected(adapterView, t, i);
                }
            }
        });
        spinner.post(() -> {
//            spinner.setDropDownWidth(spinner.getWidth());
            spinner.setDropDownVerticalOffset(spinner.getHeight());
        });
    }

    @Override
    public void getIndexTags(String type, String source) {
        mRxManager.add(mDouBanService.getIndexTags(type, source)
                .subscribeWith(new ResponseDisposable<Object>(mContext, true) {
                    @Override
                    protected void onSuccess(Object object) {
                        String resultString = new Gson().toJson(object);
                        if (!TextUtils.isEmpty(resultString)) {
                            JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                            if (jsonObject != null) {
                                boolean isHasTags = jsonObject.has("tags");
                                if (isHasTags) {
                                    JsonElement tags = jsonObject.get("tags");
                                    List<String> tagsList = new ArrayList<>();
                                    if (tags != null && tags.isJsonArray()) {
                                        JsonArray jsonArray = tags.getAsJsonArray();
                                        for (JsonElement jsonElement : jsonArray) {
                                            tagsList.add(jsonElement.getAsString());
                                        }
                                        if (mView != null) {
                                            mView.getIndexTagsSuccess(tagsList);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void getSubjectsData(String type, String tag, String sort, int pageSize, int pageStart) {
        mRxManager.add(mDouBanService.getSubjectsData(type, tag, sort, pageSize, pageStart)
                .subscribeWith(new ResponseDisposable<DouBanDto>(mContext, true) {
                    @Override
                    protected void onSuccess(DouBanDto douBanDto) {
                        if (mView != null) {
                            mView.getSubjectsDataSuccess(douBanDto);
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        if (mView != null) {
                            mView.getSubjectsDataFail(errorCode, errorMsg);
                        }
                    }
                }));
    }

    @Override
    public void setTagsData(TabLayout tabLayout, List<String> tagsList, TabLayout.BaseOnTabSelectedListener tabSelectedListener) {
        if (tagsList == null) return;
        if (null != tabSelectedListener) {
            tabLayout.addOnTabSelectedListener(tabSelectedListener);
        }
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
