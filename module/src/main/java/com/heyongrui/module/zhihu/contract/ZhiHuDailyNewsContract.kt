package com.heyongrui.module.zhihu.contract

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.base.BaseView
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto
import com.ms.banner.Banner
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*

interface ZhiHuDailyNewsContract {
    interface View : BaseView<Any> {
        fun getZhiHuNewsSuccess(zhiHuDailyNewsDto: ZhiHuDailyNewsDto?)

        fun getZhiHuNewsFail(errorCode: Int, errorMsg: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: StoreHouseHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener)

        abstract fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): ModuleSectionAdapter

        abstract fun setBannerData(banner: Banner, bannersBeanList: List<*>?)

        abstract fun getZhiHuNewsLatest()

        abstract fun getZhiHuPastNews(date: Date)

        abstract fun getZhiHuNewsHtmlContent(id: Int)
    }
}