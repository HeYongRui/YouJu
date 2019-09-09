package com.heyongrui.module.zhihu.presenter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.app.BaseApplication
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.glide.transformations.RoundedCornersTransformation
import com.heyongrui.base.utils.GlideUtil
import com.heyongrui.base.utils.TimeUtil
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration
import com.heyongrui.module.R
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.adapter.ModuleSectionEntity
import com.heyongrui.module.dagger.DaggerModuleComponent
import com.heyongrui.module.dagger.ModuleModule
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto
import com.heyongrui.module.data.service.DailyLifeService
import com.heyongrui.module.zhihu.contract.ZhiHuDailyNewsContract
import com.heyongrui.network.configure.ResponseDisposable
import com.ms.banner.Banner
import com.ms.banner.BannerConfig
import com.ms.banner.Transformer
import com.ms.banner.holder.BannerViewHolder
import com.scwang.smartrefresh.header.StoreHouseHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import javax.inject.Inject


class ZhiHuDailyNewsPresenter : ZhiHuDailyNewsContract.Presenter() {

    @Inject
    lateinit var mDailyLifeService: DailyLifeService

    init {
        DaggerModuleComponent.builder().appComponent(BaseApplication.getAppComponent()).moduleModule(ModuleModule()).build().inject(this)
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

    override fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): ModuleSectionAdapter {
        val data = ArrayList<ModuleSectionEntity>()
        val moduleSectionAdapter = ModuleSectionAdapter(android.R.layout.simple_list_item_1, data)
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

    override fun setBannerData(banner: Banner, bannersBeanList: List<*>?) {
        if (bannersBeanList == null || bannersBeanList.isEmpty()) return
        banner.setPages(bannersBeanList, object : BannerViewHolder<Any> {

            lateinit var photoIv: ImageView

            override fun onBind(context: Context?, position: Int, data: Any?) {
                if (data != null && data is ZhiHuDailyNewsDto.StoryBean) {
                    var photo_url = data.image
                    photo_url = if (TextUtils.isEmpty(photo_url)) "" else photo_url
                    GlideUtil.loadRound(context, photo_url, photoIv, 20, RoundedCornersTransformation.CornerType.ALL, R.drawable.placeholder)
                }
            }

            override fun createView(context: Context?): View {
                photoIv = ImageView(context)
                photoIv.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                photoIv.setScaleType(ImageView.ScaleType.CENTER_CROP)
                return photoIv
            }

        })
        banner.setBannerAnimation(Transformer.Scale);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        banner.setBannerAnimation(Transformer.Tablet)
//        banner.setIndicatorGravity(BannerConfig.CENTER)
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setOnBannerClickListener { datas, position ->
            var item = datas[position]
            if (item != null) {
                if (item is ZhiHuDailyNewsDto.StoryBean) {
                    ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", "http://daily.zhihu.com/story/${item.id}").navigation()
//                    getZhiHuNewsHtmlContent(item.id)
                }
            }
        }
        banner.start()
    }

    override fun getZhiHuNewsLatest() {
        mRxManager.add(mDailyLifeService.getZhiHuNewsLatest().subscribeWith(
                object : ResponseDisposable<ZhiHuDailyNewsDto>(mContext, true) {
                    override fun onSuccess(zhiHuDailyNewsDto: ZhiHuDailyNewsDto) {
                        mView?.getZhiHuNewsSuccess(zhiHuDailyNewsDto)
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        mView?.getZhiHuNewsFail(errorCode, errorMsg)
                    }
                }))
    }

    override fun getZhiHuPastNews(date: Date) {
        if (null == date) return
        try {
            var dateStr = TimeUtil.getDateString(date, "yyyyMMdd")
            mRxManager.add(mDailyLifeService.getZhiHuPastNews(dateStr).subscribeWith(
                    object : ResponseDisposable<ZhiHuDailyNewsDto>(mContext, true) {
                        override fun onSuccess(zhiHuDailyNewsDto: ZhiHuDailyNewsDto) {
                            mView?.getZhiHuNewsSuccess(zhiHuDailyNewsDto)
                        }

                        override fun onFailure(errorCode: Int, errorMsg: String) {
                            mView?.getZhiHuNewsFail(errorCode, errorMsg)
                        }
                    }))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getZhiHuNewsHtmlContent(id: Int) {
        mRxManager.add(mDailyLifeService.getZhiHuNewsHtmlContent(id).subscribeWith(
                object : ResponseDisposable<Any>(mContext, false) {
                    override fun onSuccess(any: Any) {

                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        ToastUtils.showShort(errorMsg)
                    }
                }))
    }
}