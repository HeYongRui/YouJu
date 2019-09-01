package com.heyongrui.module2.gank.contract

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.base.BaseView
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.data.dto.GankDto
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

interface GankContract {
    interface View : BaseView<Any> {
        fun getGankCategorySuccess(gankDtoList: List<GankDto>)

        fun getGankCategoryFail(errorCode: Int, errorMsg: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: StoreHouseHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener)

        abstract fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): Module2SectionAdapter

        abstract fun getGankCategory(category: String, perPage: Int, page: Int)
    }
}