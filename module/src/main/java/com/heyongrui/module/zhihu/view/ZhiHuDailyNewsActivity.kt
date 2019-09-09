package com.heyongrui.module.zhihu.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.utils.DrawableUtil
import com.heyongrui.base.utils.TimeUtil
import com.heyongrui.module.R
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.adapter.ModuleSectionEntity
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto
import com.heyongrui.module.zhihu.contract.ZhiHuDailyNewsContract
import com.heyongrui.module.zhihu.presenter.ZhiHuDailyNewsPresenter
import com.ms.banner.Banner
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_zhihu_daily_news.*
import java.util.*

@Route(path = ConfigConstants.PATH_ZHIHU_DAILY_NEWS)
class ZhiHuDailyNewsActivity : BaseActivity<ZhiHuDailyNewsContract.Presenter>(),
        ZhiHuDailyNewsContract.View, View.OnClickListener, OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    var mCalendar = Calendar.getInstance()
    lateinit var mZhiHuAdapter: ModuleSectionAdapter
    lateinit var mBanner: Banner

    fun isInitBanner(): Boolean = ::mBanner.isInitialized

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0 == iv_back) {
                finish()
            }
        }
    }

    override fun setPresenter(): ZhiHuDailyNewsContract.Presenter {
        return ZhiHuDailyNewsPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_zhihu_daily_news
    }

    override fun init(savedInstanceState: Bundle?) {
        val tintDrawable = DrawableUtil.tintDrawable(this@ZhiHuDailyNewsActivity, R.drawable.ic_back,
                ContextCompat.getColor(this@ZhiHuDailyNewsActivity, R.color.background))
        iv_back.setImageDrawable(tintDrawable)

        addOnClickListeners(this@ZhiHuDailyNewsActivity, iv_back)

        mPresenter.initSwipeRefresh(refresh_layout, store_house_header, this@ZhiHuDailyNewsActivity)
        mZhiHuAdapter = mPresenter.initRecyclerView(rlv_zhihu, this@ZhiHuDailyNewsActivity)

        refresh_layout.autoRefresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mCalendar.add(Calendar.DAY_OF_MONTH, -1)
        getZhiHuNews()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mCalendar = Calendar.getInstance()
        getZhiHuNews()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var storyBean = mZhiHuAdapter.data.get(position).storyBean
        if (storyBean != null) {
            ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", "http://daily.zhihu.com/story/${storyBean.id}").navigation()
//            mPresenter?.getZhiHuNewsHtmlContent(storyBean.id)
        }
    }

    override fun getZhiHuNewsSuccess(zhiHuDailyNewsDto: ZhiHuDailyNewsDto?) {
        resetRefreshLayout(true, true, false)
        var addDataList = ArrayList<ModuleSectionEntity>()
        var moduleSectionEntity = ModuleSectionEntity(true, TimeUtil.getDateString(mCalendar.time, TimeUtil.DAY_ONE), true)
        moduleSectionEntity.itemType = ModuleSectionEntity.ZHIHU_NEWS
        addDataList.add(moduleSectionEntity)
        var storyBeanList = zhiHuDailyNewsDto?.stories
        if (storyBeanList != null) {
            for (storyBean in storyBeanList) {
                addDataList.add(ModuleSectionEntity(ModuleSectionEntity.ZHIHU_NEWS, storyBean))
            }
        }
        if (TimeUtil.isSameDay(Date(), mCalendar.time)) {
            mZhiHuAdapter.replaceData(addDataList)
            if (!isInitBanner()) {
                val headerView = LayoutInflater.from(this@ZhiHuDailyNewsActivity).inflate(R.layout.layout_banner, null)
                mBanner = headerView.findViewById(R.id.banner)
                mZhiHuAdapter.setHeaderView(headerView)
            }
            mPresenter?.setBannerData(mBanner, zhiHuDailyNewsDto?.top_stories)
        } else {
            mZhiHuAdapter.addData(addDataList)
        }
    }

    override fun getZhiHuNewsFail(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        resetRefreshLayout(true, true, false)
    }

    private fun getZhiHuNews() {
        if (TimeUtil.isSameDay(Date(), mCalendar.time)) {//如果是今天，获取最新
            mPresenter?.getZhiHuNewsLatest()
        } else {//不是今天，获取往期新闻
            mPresenter?.getZhiHuPastNews(mCalendar.time)
        }
    }

    private fun resetRefreshLayout(isFinishLoadMore: Boolean, isFinishRefresh: Boolean, noMoreData: Boolean) {
        if (isFinishLoadMore) {
            refresh_layout.finishLoadMore()
        }
        if (isFinishRefresh) {
            refresh_layout.finishRefresh()
        }
        refresh_layout.setNoMoreData(noMoreData)
    }

    override fun onStart() {
        super.onStart()
        if (isInitBanner()) {
            if (!mBanner.isStart() && mBanner.isPrepare()) {
                mBanner.startAutoPlay();
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (isInitBanner()) {
            if (mBanner.isStart() && mBanner.isPrepare()) {
                mBanner.stopAutoPlay();
            }
        }
    }
}