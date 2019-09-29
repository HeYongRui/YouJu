package com.heyongrui.module.qdaily.contract

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.base.BaseView
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.data.dto.QDailyLabsDto
import com.heyongrui.module.data.dto.QDailyNewsDto
import com.ms.banner.Banner
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

interface QDailyContract {
    interface View : BaseView<Any> {
        fun getQDailyNewsSuccess(qDailyNewsDto: QDailyNewsDto)

        fun getQDailyNewsFail(errorCode: Int, errorMsg: String)

        fun getQDailyLabsSuccess(qDailyLabsDto: QDailyLabsDto)

        fun getQDailyLabsFail(errorCode: Int, errorMsg: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: RefreshHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener)

        abstract fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): ModuleSectionAdapter

        abstract fun setBannerData(banner: Banner, bannersBeanList: List<*>?)

        abstract fun getQDailyNews(pageKey: String?)

        abstract fun getQDailyLabs(pageKey: String?)
    }
}