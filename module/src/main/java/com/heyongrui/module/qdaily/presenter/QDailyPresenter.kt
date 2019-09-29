package com.heyongrui.module.qdaily.presenter

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
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.app.BaseApplication
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.glide.transformations.RoundedCornersTransformation
import com.heyongrui.base.utils.GlideUtil
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration
import com.heyongrui.module.R
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.adapter.ModuleSectionEntity
import com.heyongrui.module.dagger.DaggerModuleComponent
import com.heyongrui.module.dagger.ModuleModule
import com.heyongrui.module.data.dto.QDailyLabsDto
import com.heyongrui.module.data.dto.QDailyNewsDto
import com.heyongrui.module.data.service.DailyLifeService
import com.heyongrui.module.qdaily.contract.QDailyContract
import com.heyongrui.network.configure.ResponseDisposable
import com.ms.banner.Banner
import com.ms.banner.BannerConfig
import com.ms.banner.Transformer
import com.ms.banner.holder.BannerViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import javax.inject.Inject


class QDailyPresenter : QDailyContract.Presenter() {

    @Inject
    lateinit var mDailyLifeService: DailyLifeService

    init {
        DaggerModuleComponent.builder().appComponent(BaseApplication.getAppComponent()).moduleModule(ModuleModule()).build().inject(this)
    }

    override fun initSwipeRefresh(smartRefreshLayout: SmartRefreshLayout, storeHouseHeader: RefreshHeader, onRefreshLoadMoreListener: OnRefreshLoadMoreListener) {
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
        smartRefreshLayout.setDisableContentWhenRefresh(true)//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true)//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true)
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true)
        smartRefreshLayout.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
    }

    override fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): ModuleSectionAdapter {
        val data = ArrayList<ModuleSectionEntity>()
        val moduleSectionAdapter = ModuleSectionAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        moduleSectionAdapter.bindToRecyclerView(recyclerView)
        val dp5 = ConvertUtils.dp2px(5f)
        val decorationColor = ContextCompat.getColor(mContext, R.color.window_background)
        recyclerView.addItemDecoration(RecycleViewItemDecoration(mContext, dp5, decorationColor))
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
                if (data != null && data is com.heyongrui.module.data.dto.Banner) {
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
            if (item != null && item is com.heyongrui.module.data.dto.Banner) {
                ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", item?.post?.appview).navigation()
            }
        }
        banner.start()
    }

    override fun getQDailyNews(pageKey: String?) {
        mRxManager.add(mDailyLifeService.getQDailyNews(pageKey).subscribeWith(
                object : ResponseDisposable<QDailyNewsDto>(mContext, true) {
                    override fun onSuccess(qDailyNewsDto: QDailyNewsDto) {
                        if (null != mView) {
                            mView.getQDailyNewsSuccess(qDailyNewsDto)
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        if (null != mView) {
                            mView.getQDailyNewsFail(errorCode, errorMsg)
                        }
                    }
                }))
    }

    override fun getQDailyLabs(pageKey: String?) {
        mRxManager.add(mDailyLifeService.getQDailyLabs(pageKey).subscribeWith(
                object : ResponseDisposable<QDailyLabsDto>(mContext, true) {
                    override fun onSuccess(qDailyLabsDto: QDailyLabsDto) {
                        if (null != mView) {
                            mView.getQDailyLabsSuccess(qDailyLabsDto)
                        }
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        if (null != mView) {
                            mView.getQDailyLabsFail(errorCode, errorMsg)
                        }
                    }
                }))
    }
}