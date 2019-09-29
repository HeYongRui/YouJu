package com.heyongrui.module.qdaily.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.base.BaseFragment
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.utils.DrawableUtil
import com.heyongrui.module.R
import kotlinx.android.synthetic.main.activity_q_daily.*
import java.util.*

@Route(path = ConfigConstants.PATH_Q_DAILY)
class QDailyActivity : BaseActivity<BasePresenter<*>>(), View.OnClickListener {

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0 == iv_back) {
                finish()
            } else if (p0 == fab_q) {
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_q_daily;
    }

    override fun init(savedInstanceState: Bundle?) {
        val tintDrawable = DrawableUtil.tintDrawable(this@QDailyActivity, R.drawable.ic_back,
                ContextCompat.getColor(this@QDailyActivity, R.color.background))
        iv_back.setImageDrawable(tintDrawable)

        addOnClickListeners(this@QDailyActivity, iv_back, fab_q)

        initTabViewPager(tab_layout, view_pager)
    }

    private fun initTabViewPager(tabLayout: TabLayout, viewPager: ViewPager) {
        val fragments = ArrayList<BaseFragment<*>>()
        val pageTitleArray = arrayOf(getString(R.string.qdaily_tab1), getString(R.string.qdaily_tab2))
        var fabListener = object : QDailyFragment.FabDisplayListener {
            override fun onShow() {
                showFABAnimation(fab_q)
            }

            override fun onDismiss() {
                hideFABAnimation(fab_q)
            }

        }
        fragments.add(QDailyFragment.getInstance(1, fabListener))
        fragments.add(QDailyFragment.getInstance(2, fabListener))
        val fragmentPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return pageTitleArray[position]
            }
        }
        viewPager.offscreenPageLimit = fragments.size
        viewPager.adapter = fragmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun showFABAnimation(view: View) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 1f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }

    fun hideFABAnimation(view: View) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 0f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }
}