package com.heyongrui.module2.gank.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.tabs.TabLayout
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.utils.DrawableUtil
import com.heyongrui.module2.R
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.adapter.Module2SectionEntity
import com.heyongrui.module2.data.dto.LeisureReadCategoryDto
import com.heyongrui.module2.data.dto.LeisureReadDto
import com.heyongrui.module2.data.dto.LeisureReadSubCategoryDto
import com.heyongrui.module2.gank.contract.LeisureReadContract
import com.heyongrui.module2.gank.presenter.LeisureReadPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_leisure_read.*
import java.util.*

@Route(path = ConfigConstants.PATH_LEISURE_READ)
class LeisureReadActivity : BaseActivity<LeisureReadContract.Presenter>(),
        LeisureReadContract.View,
        View.OnClickListener,
        OnRefreshLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {

    private val mPerPage = 10
    private var mPage = 1
    private lateinit var mId: String
    private var mIsLastPage: Boolean = false
    lateinit var mLeisureReadAdapter: Module2SectionAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_leisure_read
    }

    override fun setPresenter(): LeisureReadContract.Presenter {
        return LeisureReadPresenter()
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0 == iv_back) {
                finish()
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        addOnClickListeners(this@LeisureReadActivity, iv_back)

        val tintDrawable = DrawableUtil.tintDrawable(this@LeisureReadActivity, R.drawable.ic_back, ContextCompat.getColor(this@LeisureReadActivity, R.color.background))
        iv_back.setImageDrawable(tintDrawable)

        mPresenter?.initSwipeRefresh(refresh_layout, store_house_header, this@LeisureReadActivity)
        mLeisureReadAdapter = mPresenter.initRecyclerView(rlv_leisure_read, this@LeisureReadActivity)

        mPresenter?.getLeisureReadCategory()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (mIsLastPage) {
            resetRefreshLayout(true, true, true)
        } else {
            loadData(false)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        loadData(true)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val leisureReadDto = mLeisureReadAdapter.data.get(position).leisureReadDto
        ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", leisureReadDto.url).navigation()
    }

    override fun <T> onSpinnerItemSelected(adapterView: AdapterView<*>, t: T?, position: Int) {
        val id = adapterView.id
        if (id == R.id.spinner_category) {
            if (null != t && t is LeisureReadCategoryDto) {
                t.en_name?.let { mPresenter?.getLeisureReadSubCategory(it) }
            }
        }
    }

    override fun getLeisureReadCategorySuccess(leisureReadCategoryList: List<LeisureReadCategoryDto>) {
        mPresenter?.initSpinner(spinner_category, leisureReadCategoryList)
    }

    override fun getLeisureReadSubCategorySuccess(leisureReadSubCategoryList: List<LeisureReadSubCategoryDto>) {
        mPresenter?.setTagsData(tab_layout, leisureReadSubCategoryList, object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val tag = p0?.tag
                if (tag is LeisureReadSubCategoryDto) {
                    mId = tag.id.toString()
                    loadData(true)
                }
            }
        })
    }

    override fun getLeisureReadSuccess(leisureReadList: List<LeisureReadDto>) {
        resetRefreshLayout(true, true, false)
        val addDataList = ArrayList<Module2SectionEntity>()
        mIsLastPage = leisureReadList.size < mPerPage
        for (leisureReadDto in leisureReadList) {
            addDataList.add(Module2SectionEntity(Module2SectionEntity.LEISURE_READ, leisureReadDto))
        }
        if (1 == mPage) {
            rlv_leisure_read.smoothScrollToPosition(0)
            mLeisureReadAdapter.replaceData(addDataList)
        } else {
            mLeisureReadAdapter.addData(addDataList)
        }
    }

    override fun getLeisureReadFail(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        resetRefreshLayout(true, true, false)
    }

    private fun loadData(isClearData: Boolean) {
        if (isClearData) {
            mPage = 1
        } else {
            mPage += 1
        }
        mPresenter?.getLeisureRead(mId, mPerPage, mPage)
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