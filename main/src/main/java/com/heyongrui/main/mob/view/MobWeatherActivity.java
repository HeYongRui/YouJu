package com.heyongrui.main.mob.view;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ScreenUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.main.R;

@Route(path = ConfigConstants.PATH_WEATHER)
public class MobWeatherActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivSun;
    private ImageView ivMask;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_SHOW_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mob_weather;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //初始化View
        ImageView ivBack = findViewById(R.id.iv_back);
        ivSun = findViewById(R.id.iv_sun);
        ivMask = findViewById(R.id.iv_mask);
        ivMask.getBackground().mutate().setAlpha(0);

        int statusBarHeight = ImmersionBar.getStatusBarHeight(this);
        ViewGroup.LayoutParams layoutParams = ivBack.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = statusBarHeight;
            ivBack.setLayoutParams(layoutParams);
        }

        //图标着色
        Drawable drawable = DrawableUtil.tintDrawable(this,
                R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);

        //设置点击监听器
        addOnClickListeners(this, ivBack);
    }

    private void playSunAnim(float stopPercent) {
        float startX = 0;
        float startY = ivSun.getY();

        float endX = ScreenUtils.getScreenWidth();
        float endY = startY;

        float controlY = 0;
        float controlX = ScreenUtils.getScreenWidth() / 2;
        //贝塞尔二阶曲线控制点
        PointF controlP = new PointF(controlX, controlY);
        //构造贝塞尔估值器
        BezierEvaluator evaluator = new BezierEvaluator(controlP);
        //贝塞尔动画
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(startX, startY), new PointF(endX, endY));
//            animator.setInterpolator(new AnticipateInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            //设置目标位置
            if (ivSun.getVisibility() != View.VISIBLE) {
                ivSun.setVisibility(View.VISIBLE);
            }
            ivSun.setX(pointF.x);
            ivSun.setY(pointF.y);
            //设置透明度
            float percent = valueAnimator.getAnimatedFraction();//动画进度百分比
            //需要在哪里停止动画
            if (percent > (stopPercent - 0.1) && percent < (stopPercent + 0.1)) {
                animator.cancel();
            }
            ivMask.getBackground().mutate().setAlpha((int) (255 * percent));
            if (percent <= 0.5f) {//代表早晨到中午时间段，太阳慢慢升起，渐显渐大
                if (ivSun.getDrawable().getCurrent().getConstantState() != getResources().getDrawable(R.drawable.ic_sun).getConstantState()) {
                    ivSun.setImageResource(R.drawable.ic_sun);
                }
                float alpha = (float) (percent / 0.5);
                alpha = alpha <= 0.3 ? (float) 0.3 : alpha;
                ivSun.setAlpha(alpha);
                ivSun.setScaleX((float) (0.3 + alpha));
                ivSun.setScaleY((float) (0.3 + alpha));
            } else if (percent <= 0.7f) {//代表中午到下午时间段，太阳慢慢降落，渐隐渐小
                if (ivSun.getDrawable().getCurrent().getConstantState() != getResources().getDrawable(R.drawable.ic_sun).getConstantState()) {
                    ivSun.setImageResource(R.drawable.ic_sun);
                }
                float alpha = (float) (1 - ((percent - 0.5) / 0.2));
                alpha = alpha <= 0.3 ? (float) 0.3 : alpha;
                ivSun.setAlpha(alpha);
                ivSun.setScaleX((float) (0.3 + alpha));
                ivSun.setScaleY((float) (0.3 + alpha));
            } else {//代表下午到晚上时间段，太阳消失，月亮慢慢出现，渐显渐大
                if (ivSun.getDrawable().getCurrent().getConstantState() != getResources().getDrawable(R.drawable.ic_moon).getConstantState()) {
                    ivSun.setImageResource(R.drawable.ic_moon);
                }
                float alpha = (float) ((percent - 0.7) / 0.3);
                alpha = alpha <= 0.3 ? (float) 0.3 : alpha;
                ivSun.setAlpha(alpha);
                ivSun.setScaleX((float) (0.3 + alpha));
                ivSun.setScaleY((float) (0.3 + alpha));
            }
        });
        animator.setTarget(ivSun);
        animator.setDuration(4000);
        animator.start();
    }
}
