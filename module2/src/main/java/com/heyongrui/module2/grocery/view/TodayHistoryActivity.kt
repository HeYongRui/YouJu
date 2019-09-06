package com.heyongrui.module2.grocery.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.heyongrui.base.app.BaseApplication
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.assist.RxManager
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.utils.DrawableUtil
import com.heyongrui.base.utils.TimeUtil
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration
import com.heyongrui.module2.R
import com.heyongrui.module2.adapter.Module2SectionAdapter
import com.heyongrui.module2.adapter.Module2SectionEntity
import com.heyongrui.module2.dagger.DaggerModule2Component
import com.heyongrui.module2.dagger.Module2Module
import com.heyongrui.module2.data.dto.HistoryTodayDto
import com.heyongrui.module2.data.service.ModuleService
import com.heyongrui.network.configure.ResponseDisposable
import kotlinx.android.synthetic.main.activity_today_history.*
import java.util.*
import javax.inject.Inject

@Route(path = ConfigConstants.PATH_TODAY_HISTORY)
class TodayHistoryActivity : BaseActivity<BasePresenter<*>>(),
        View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener {

    @Inject
    lateinit var mModuleService: ModuleService
    @Inject
    lateinit var mRxManager: RxManager

    lateinit var mTodayHistoryAdapter: Module2SectionAdapter

    fun adapterIsInit() = ::mTodayHistoryAdapter.isInitialized

    override fun initializeInjector() {
        DaggerModule2Component.builder().appComponent(BaseApplication.getAppComponent())
                .module2Module(Module2Module(this@TodayHistoryActivity)).build().inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_today_history
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0 == iv_back) {
                finish()
            }
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        addOnClickListeners(this@TodayHistoryActivity, iv_back)

        val tintDrawable = DrawableUtil.tintDrawable(this@TodayHistoryActivity,
                R.drawable.ic_back, ContextCompat.getColor(this@TodayHistoryActivity, R.color.background))
        iv_back.setImageDrawable(tintDrawable)

        mTodayHistoryAdapter = initRecyclerView(rlv_today_history, this)

        val day = TimeUtil.getDateString(Date(), "MMdd")
        getTodayHistory(day)
    }

    fun initRecyclerView(recyclerView: RecyclerView, listener: BaseQuickAdapter.OnItemClickListener): Module2SectionAdapter {
        val data = ArrayList<Module2SectionEntity>()
        val moduleSectionAdapter = Module2SectionAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(this@TodayHistoryActivity)
        moduleSectionAdapter.bindToRecyclerView(recyclerView)
        val dp1 = ConvertUtils.dp2px(1f)
        recyclerView.addItemDecoration(RecycleViewItemDecoration(this@TodayHistoryActivity, dp1, Color.TRANSPARENT))
        moduleSectionAdapter.setSpanSizeLookup({ gridLayoutManager, position -> data[position].getSpanSize() })
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener)
        }
        return moduleSectionAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    fun getTodayHistory(day: String) {
        mRxManager.add(mModuleService.getTodayHistory(day).subscribeWith(
                object : ResponseDisposable<HistoryTodayDto>(this@TodayHistoryActivity, true) {
                    override fun onSuccess(historyTodayDto: HistoryTodayDto) {
                        setData(historyTodayDto)
                    }

                    override fun onFailure(errorCode: Int, errorMsg: String) {
                        ToastUtils.showShort(errorMsg)
                    }
                }))
    }

    fun setData(historyTodayDto: HistoryTodayDto) {
        if (!adapterIsInit()) return
    }
}