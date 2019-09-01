package com.heyongrui.module2.gank.presenter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.app.BaseApplication
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration
import com.heyongrui.module2.R
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.adapter.Module2SectionEntity
import com.heyongrui.module2.dagger.DaggerModule2Component
import com.heyongrui.module2.dagger.Module2Module
import com.heyongrui.module2.data.dto.GankDto
import com.heyongrui.module2.data.service.GankService
import com.heyongrui.module2.gank.contract.GankContract
import com.heyongrui.network.configure.ResponseDisposable
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import javax.inject.Inject

class GankPresenter : GankContract.Presenter() {

    @Inject
    lateinit var mGankService: GankService

    init {
        DaggerModule2Component.builder().appComponent(BaseApplication.getAppComponent()).module2Module(Module2Module()).build().inject(this)
    }

    override fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: StoreHouseHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(mContext, R.color.white))
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
        smartRefreshLayout.setDisableContentWhenRefresh(true)//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true)//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true)
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true)
        smartRefreshLayout.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
    }

    override fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): Module2SectionAdapter {
        val data = ArrayList<Module2SectionEntity>()
        val moduleSectionAdapter = Module2SectionAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        moduleSectionAdapter.bindToRecyclerView(recyclerView)
        val dp1 = ConvertUtils.dp2px(1f)
        recyclerView.addItemDecoration(RecycleViewItemDecoration(mContext, dp1))
        moduleSectionAdapter.setSpanSizeLookup({ gridLayoutManager, position -> data[position].getSpanSize() })
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener)
        }
        return moduleSectionAdapter
    }

    override fun getGankCategory(category: String, perPage: Int, page: Int) {
        mRxManager.add(mGankService.getGankCategory(category, perPage, page).subscribeWith(
                object : ResponseDisposable<GankDto>(mContext, true) {
                    override fun onSuccess(gankDto: GankDto) {
                        if (mView != null) {
                            mView.getGankCategorySuccess(gankDto)
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        if (mView != null) {
                            mView.getGankCategoryFail(errorCode, errorMsg)
                        }
                    }
                }))
    }
}