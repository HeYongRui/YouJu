package com.heyongrui.module2.gank.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.utils.DrawableUtil
import com.heyongrui.module2.R
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.adapter.Module2SectionEntity
import com.heyongrui.module2.data.dto.WelfareDto
import com.heyongrui.module2.gank.contract.GankContract
import com.heyongrui.module2.gank.presenter.GankPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_gank.*
import java.util.*

@Route(path = ConfigConstants.PATH_GANK)
class GankActivity : BaseActivity<GankContract.Presenter>(), GankContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, OnRefreshLoadMoreListener {

    @JvmField
    @Autowired(name = "type")
    var mType: Int = 0

    private val mPerPage = 10
    private var mPage = 1
    private var mIsLastPage: Boolean = false
    lateinit var mGankAdapter: Module2SectionAdapter

    override fun initImmersionBar() {
        super.initImmersionBar()
//        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()
    }

    override fun setPresenter(): GankContract.Presenter {
        return GankPresenter()
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0 == iv_back) {
                finish()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_gank;
    }

    override fun init(savedInstanceState: Bundle?) {
        addOnClickListeners(this@GankActivity, iv_back)

        val tintDrawable = DrawableUtil.tintDrawable(this@GankActivity, R.drawable.ic_back, ContextCompat.getColor(this@GankActivity, R.color.background))
        iv_back.setImageDrawable(tintDrawable)

        mPresenter.initSwipeRefresh(refresh_layout, store_house_header, this@GankActivity)
        mGankAdapter = mPresenter.initRecyclerView(rlv_gank, this@GankActivity)

        refresh_layout.autoRefresh()
    }

    private fun loadData() {
        mPresenter.getAndroid(mPerPage, mPage)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        loadData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    override fun getAndroidSuccess(welfareDto: WelfareDto) {
        resetRefreshLayout(true, true, false)
        val addDataList = ArrayList<Module2SectionEntity>()
        if (welfareDto != null) {
            if (!welfareDto.isError) {
                val welfareBeanList = welfareDto.results
                if (welfareBeanList != null && !welfareBeanList.isEmpty()) {
                    if (welfareBeanList.size < mPerPage) {
                        mIsLastPage = true
                    }
                    for (welfareBean in welfareBeanList) {
                        addDataList.add(Module2SectionEntity(Module2SectionEntity.WELFARE, welfareBean))
                    }
                } else {
                    mIsLastPage = true
                }
            }
        }
        mGankAdapter.replaceData(addDataList)
    }

    override fun getAndroidFail(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        resetRefreshLayout(true, true, false)
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
}