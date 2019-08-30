package com.heyongrui.module.douban.contract;

import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.data.dto.DouBanDto;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public interface DouBanContract {
    interface View extends BaseView {
        void getIndexTagsSuccess(List<String> tagsList);

        void getSubjectsDataSuccess(DouBanDto douBanDto);

        void getSubjectsDataFail(int errorCode, String errorMsg);

        <T> void onSpinnerItemSelected(AdapterView<?> adapterView, T t, int position);
    }

    abstract class Presenter extends BasePresenter<DouBanContract.View> {
        public abstract void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader, OnRefreshLoadMoreListener onRefreshLoadMoreListener);

        public abstract ModuleSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener);

        public abstract <T> void initSpinner(Spinner spinner, List<T> dataList);

        public abstract void getIndexTags(String type, String source);

        public abstract void getSubjectsData(String type, String tag, String sort, int pageSize, int pageStart);

        public abstract void setTagsData(TabLayout tabLayout, List<String> tagsList, TabLayout.BaseOnTabSelectedListener tabSelectedListener);
    }
}
