package com.heyongrui.user

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.heyongrui.base.assist.ConfigConstants
import com.heyongrui.base.base.BaseActivity
import com.heyongrui.base.base.BasePresenter
import com.heyongrui.base.base.BaseView
import com.heyongrui.base.glide.GlideApp
import kotlinx.android.synthetic.main.activity_kotlin.*

@Route(path = ConfigConstants.PATH_KOTLIN)
class KotlinActivity<T : BasePresenter<out BaseView<*>>?> : BaseActivity<T>(), View.OnClickListener {

    var currentState = -1;

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin;
    }

    override fun init(savedInstanceState: Bundle?) {
        bg.setOnClickListener(this);
        iv.loadCircle("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2551393832.jpg")
        iv2.loadCircle("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2563780504.jpg")
        iv3.loadCircle(ColorDrawable(Color.BLACK))
        iv4.loadCircle(ColorDrawable(Color.GRAY))
    }

    fun ImageView.loadCircle(resource: Any) {
        val options = RequestOptions()
                .dontAnimate()
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        GlideApp.with(this).load(resource).apply(options).into(this)
    }

    override fun onClick(view: View?) {
        val id = view?.id;
        if (id == R.id.bg) {
            if (-1 == currentState) {
                motionLayout.transitionToEnd();
                motionLayout2.transitionToEnd();
                currentState = 1;
            } else if (1 == currentState) {
                motionLayout.transitionToStart();
                motionLayout2.transitionToStart();
                currentState = -1;
            }
        }
    }
}