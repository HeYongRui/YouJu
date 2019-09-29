package com.heyongrui.module.qdaily.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseFragment
import com.heyongrui.module.R
import com.heyongrui.module.adapter.ModuleSectionAdapter
import com.heyongrui.module.adapter.ModuleSectionEntity
import com.heyongrui.module.data.dto.QDailyLabsDto
import com.heyongrui.module.data.dto.QDailyNewsDto
import com.heyongrui.module.qdaily.contract.QDailyContract
import com.heyongrui.module.qdaily.presenter.QDailyPresenter
import com.ms.banner.Banner
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_q_daily.*
import java.util.*
import kotlin.properties.Delegates

class QDailyFragment : BaseFragment<QDailyContract.Presenter>(),
        QDailyContract.View, OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    lateinit var mBanner: Banner
    //列表适配器
    lateinit var mQDailyAdapter: ModuleSectionAdapter
    //来源类型
    var mType by Delegates.notNull<Int>()//1-NEWS 2-LABS
    //页面是否首次可见（用以首次自动刷新）
    var mIsFirstVisable = true
    //分页参数
    var mIsLastPage = true
    var mPageKey: String = ""
    //交互按钮显示辅助参数
    var mDistance: Int = 0
    var mVisible: Boolean = true
    var mFabDisplayListener: FabDisplayListener? = null

    fun isInitBanner(): Boolean = ::mBanner.isInitialized//判断Banner是否已经初始化

    companion object {
        fun getInstance(type: Int, fabDisplayListener: FabDisplayListener): QDailyFragment {
            val fragment = QDailyFragment()
            val bundle = Bundle()
            fragment.mFabDisplayListener = fabDisplayListener
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_q_daily;
    }

    override fun setPresenter(): QDailyContract.Presenter {
        return QDailyPresenter()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (mIsFirstVisable) {
            refresh_layout.autoRefresh()
            mIsFirstVisable = false
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
        val arguments = arguments
        mType = if (arguments != null) arguments.getInt("type") else 0

        mPresenter.initSwipeRefresh(refresh_layout, store_house_header, this@QDailyFragment)
        mQDailyAdapter = mPresenter.initRecyclerView(rlv_qdaily, this@QDailyFragment)

        rlv_qdaily.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mFabDisplayListener == null) return
                if (mDistance < -ViewConfiguration.getTouchSlop() && !mVisible) {
                    mFabDisplayListener?.onShow()
                    mDistance = 0
                    mVisible = true
                } else if (mDistance > ViewConfiguration.getTouchSlop() && mVisible) {
                    mDistance = 0
                    mFabDisplayListener?.onDismiss()
                    mVisible = false
                }
                if ((dy > 0 && mVisible) || (dy < 0 && !mVisible))//向下滑并且可见  或者  向上滑并且不可见
                    mDistance += dy
            }
        })
    }

    override fun getQDailyNewsSuccess(qDailyNewsDto: QDailyNewsDto) {
        resetRefreshLayout(true, true, false)
        var addDataList = ArrayList<ModuleSectionEntity>()
        var isLastPage = false
        var pageKey = ""
        if (qDailyNewsDto != null) {
            isLastPage = !qDailyNewsDto.has_more;
            pageKey = qDailyNewsDto.last_key
            //banner数据
            if (!isInitBanner()) {
                val headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_banner, null)
                mBanner = headerView.findViewById(R.id.banner)
                mQDailyAdapter.setHeaderView(headerView)
            }
            mPresenter?.setBannerData(mBanner, qDailyNewsDto.banners)
            //列表数据
            val feedList = qDailyNewsDto.feeds
            for (feed in feedList) {
                val type = feed.type
                var itemType: Int = ModuleSectionEntity.Q_DAILY_ONE
                if (type == 0) {
                    itemType = ModuleSectionEntity.Q_DAILY_ONE
                } else if (type == 1) {
                    itemType = ModuleSectionEntity.Q_DAILY_TWO
                }
                addDataList.add(ModuleSectionEntity(itemType, feed.post))
            }
        }
        //把数据更新到UI
        if (TextUtils.isEmpty(mPageKey)) {
            mQDailyAdapter.replaceData(addDataList)
        } else {
            mQDailyAdapter.addData(addDataList)
        }
        //更新分页标识参数
        mIsLastPage = isLastPage;
        mPageKey = pageKey
    }

    override fun getQDailyNewsFail(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        resetRefreshLayout(true, true, false)
    }

    override fun getQDailyLabsSuccess(qDailyLabsDto: QDailyLabsDto) {
        resetRefreshLayout(true, true, false)
        var addDataList = ArrayList<ModuleSectionEntity>()
        var isLastPage = false
        var pageKey = ""
        if (qDailyLabsDto != null) {
            isLastPage = !qDailyLabsDto.has_more;
            pageKey = qDailyLabsDto.last_key
            //列表数据
            val feedList = qDailyLabsDto.feeds
            for (feed in feedList) {
                val type = feed.type
                var itemType: Int = ModuleSectionEntity.Q_DAILY_ONE
                if (type == 0) {
                    itemType = ModuleSectionEntity.Q_DAILY_ONE
                } else if (type == 1) {
                    itemType = ModuleSectionEntity.Q_DAILY_TWO
                }
                addDataList.add(ModuleSectionEntity(itemType, feed.post))
            }
        }
        //把数据更新到UI
        mQDailyAdapter.removeAllHeaderView()
        if (TextUtils.isEmpty(mPageKey)) {
            mQDailyAdapter.replaceData(addDataList)
        } else {
            mQDailyAdapter.addData(addDataList)
        }
        //更新分页标识参数
        mIsLastPage = isLastPage;
        mPageKey = pageKey
    }

    override fun getQDailyLabsFail(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        resetRefreshLayout(true, true, false)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (mIsLastPage) {
            resetRefreshLayout(true, true, true)
        } else {
            if (mPresenter != null) {
                if (mType == 1) {
                    mPresenter.getQDailyNews(mPageKey)
                } else if (mType == 2) {
                    mPresenter.getQDailyLabs(mPageKey)
                }
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        if (mPresenter != null) {
            mPageKey = ""
            if (mType == 1) {
                mPresenter.getQDailyNews(mPageKey)
            } else if (mType == 2) {
                mPresenter.getQDailyLabs(mPageKey)
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (mQDailyAdapter != null) {
            val post = mQDailyAdapter.data[position].post
            val dataType = post?.datatype
            if (TextUtils.equals("paper", dataType)) {
                //去另一个页面投票类型
            } else if (TextUtils.equals("article", dataType)) {
                ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", post?.appview).navigation()
            }
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

    interface FabDisplayListener {
        fun onShow()

        fun onDismiss()
    }
}