package com.heyongrui.module2.gank.contract

import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.tabs.TabLayout
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.base.BaseView
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.data.dto.LeisureReadCategoryDto
import com.heyongrui.module2.data.dto.LeisureReadDto
import com.heyongrui.module2.data.dto.LeisureReadSubCategoryDto
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

interface LeisureReadContract {
    interface View : BaseView<Any> {
        fun <T> onSpinnerItemSelected(adapterView: AdapterView<*>, t: T?, position: Int)

        fun getLeisureReadCategorySuccess(leisureReadCategoryList: List<LeisureReadCategoryDto>)

        fun getLeisureReadSubCategorySuccess(leisureReadSubCategoryList: List<LeisureReadSubCategoryDto>)

        fun getLeisureReadSuccess(leisureReadList: List<LeisureReadDto>)

        fun getLeisureReadFail(errorCode: Int, errorMsg: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun <T> initSpinner(spinner: Spinner, dataList: List<T>)

        abstract fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: StoreHouseHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener)

        abstract fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): Module2SectionAdapter

        abstract fun <T> setTagsData(tabLayout: TabLayout, tagsList: List<T>, tabSelectedListener: TabLayout.BaseOnTabSelectedListener<TabLayout.Tab>)

        abstract fun getLeisureReadCategory()

        abstract fun getLeisureReadSubCategory(subCategory: String)

        abstract fun getLeisureRead(id: String, perPage: Int, page: Int)
    }
}