package com.heyongrui.module2.gank.presenter

import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.tabs.TabLayout
import com.heyongrui.base.app.BaseApplication
import com.heyongrui.base.widget.ReSpinner
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration
import com.heyongrui.module2.R
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.adapter.Module2SectionEntity
import com.heyongrui.module2.dagger.DaggerModule2Component
import com.heyongrui.module2.dagger.Module2Module
import com.heyongrui.module2.data.dto.GankResponse
import com.heyongrui.module2.data.dto.LeisureReadCategoryDto
import com.heyongrui.module2.data.dto.LeisureReadDto
import com.heyongrui.module2.data.dto.LeisureReadSubCategoryDto
import com.heyongrui.module2.data.service.GankService
import com.heyongrui.module2.gank.contract.LeisureReadContract
import com.heyongrui.network.configure.ResponseDisposable
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import javax.inject.Inject

class LeisureReadPresenter : LeisureReadContract.Presenter() {

    @Inject
    lateinit var mGankService: GankService

    init {
        DaggerModule2Component.builder().appComponent(BaseApplication.getAppComponent()).module2Module(Module2Module()).build().inject(this)
    }

    override fun <T> initSpinner(spinner: Spinner, dataList: List<T>) {
        val stringList = ArrayList<String>()
        if (null != dataList) {
            for (t in dataList) {
                if (t is String) {
                    stringList.add(t as String)
                } else if (t is LeisureReadCategoryDto) {
                    t.name?.let { stringList.add(it) }
                }
            }
        }
        val adapter = ArrayAdapter(mContext, R.layout.layout_spinner_dropdown_item, stringList)
        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : ReSpinner.OnReItemSelectedListener(true) {
            override fun onReItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                var t: T? = null
                if (i >= 0) {
                    if (null != dataList) {
                        t = dataList[i]
                    }
                }
                if (null != mView) {
                    mView.onSpinnerItemSelected<T>(adapterView, t, i)
                }
            }
        }
        spinner.post {
            //            spinner.dropDownWidth = spinner.width;
            spinner.dropDownVerticalOffset = spinner.height
        }
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
        recyclerView.addItemDecoration(RecycleViewItemDecoration(mContext, dp1, Color.TRANSPARENT))
        moduleSectionAdapter.setSpanSizeLookup({ gridLayoutManager, position -> data[position].getSpanSize() })
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener)
        }
        return moduleSectionAdapter
    }

    override fun <T> setTagsData(tabLayout: TabLayout, tagsList: List<T>, tabSelectedListener: TabLayout.BaseOnTabSelectedListener<TabLayout.Tab>) {
        if (tagsList == null) return
        if (null != tabSelectedListener) {
            tabLayout.addOnTabSelectedListener(tabSelectedListener)
        }
        tabLayout.removeAllTabs()
        for (t in tagsList) {
            tabLayout.addTab(creatTab(tabLayout, t))
        }
    }

    private fun creatTab(tabLayout: TabLayout, tag: Any?): TabLayout.Tab {
        val tagTab = tabLayout.newTab()
        var title: String = ""
        if (tag != null && tag is LeisureReadSubCategoryDto) {
            title = tag?.title.toString()
        } else if (tag != null && tag is String) {
            title = tag
        }
        tagTab.text = title
        tagTab.tag = tag
        return tagTab
    }

    override fun getLeisureReadCategory() {
        mRxManager.add(mGankService.getLeisureReadCategory().subscribeWith(
                object : ResponseDisposable<GankResponse<List<LeisureReadCategoryDto>>>(mContext, true) {
                    override fun onSuccess(leisureReadList: GankResponse<List<LeisureReadCategoryDto>>) {
                        if (mView != null) {
                            leisureReadList.results?.let { mView.getLeisureReadCategorySuccess(it) }
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        ToastUtils.showShort(errorMsg)
                    }
                }))
    }

    override fun getLeisureReadSubCategory(subCategory: String) {
        mRxManager.add(mGankService.getLeisureReadSubCategory(subCategory).subscribeWith(
                object : ResponseDisposable<GankResponse<List<LeisureReadSubCategoryDto>>>(mContext, true) {
                    override fun onSuccess(leisureReadSubCategoryList: GankResponse<List<LeisureReadSubCategoryDto>>) {
                        if (mView != null) {
                            leisureReadSubCategoryList.results?.let { mView.getLeisureReadSubCategorySuccess(it) }
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        ToastUtils.showShort(errorMsg)
                    }
                }))
    }

    override fun getLeisureRead(id: String, perPage: Int, page: Int) {
        mRxManager.add(mGankService.getLeisureRead(id, perPage, page).subscribeWith(
                object : ResponseDisposable<GankResponse<List<LeisureReadDto>>>(mContext, true) {
                    override fun onSuccess(leisureReadList: GankResponse<List<LeisureReadDto>>) {
                        if (mView != null) {
                            leisureReadList.results?.let { mView.getLeisureReadSuccess(it) }
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        if (mView != null) {
                            mView.getLeisureReadFail(errorCode, errorMsg)
                        }
                    }
                }))
    }
}