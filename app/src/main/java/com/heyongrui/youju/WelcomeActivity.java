package com.heyongrui.youju;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.FileUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WelcomeActivity extends BaseActivity {

    private ImageView ivWelcome;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //初始化View
        ivWelcome = findViewById(R.id.iv_welcome);
        View bg = findViewById(R.id.bg);
        ImageView ivLogo = findViewById(R.id.iv_logo);
        TextView tvSlogan = findViewById(R.id.tv_slogan);
        //加载本地缓存封面图
        loadLocalCover();
        //同时开始下载新的封面图
        startDownloadCover();
        //设置logo
        Drawable logoDrawable = DrawableUtil.tintDrawable(this, R.drawable.app_icon, Color.WHITE);
        ivLogo.setImageDrawable(logoDrawable);
        //设置slogan
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/HYQinChuanFeiYingW.ttf");
        tvSlogan.setTypeface(typeFace);
        //设置背景透明黑渐变
        int[] colors = new int[]{R.color.transparent, R.color.black};
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(this).setGradientColors(colors).createGradientDrawable();
        bg.setBackgroundDrawable(gradientDrawable);
        //开始动画、跳转逻辑
        startMainActivity();
    }

    private void loadLocalCover() {//加载本地封面图
        File cacheDirectory = FileUtil.getCacheDirectory(this, null);
        if (cacheDirectory != null && cacheDirectory.exists()) {
            File file = new File(cacheDirectory.getPath(), "splash.jpg");
            if (file != null && file.exists()) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.welcomimg);
                requestOptions.skipMemoryCache(true);
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                Glide.with(this).load(file.getAbsolutePath()).apply(requestOptions).into(ivWelcome);
            }
        }
    }

    private void startMainActivity() {//开始缩放动画
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> startAnim());
    }

    private void startDownloadCover() {//随机下载封面图
        Intent intent = new Intent(this, DownloadAdService.class);
        intent.putExtra("download_url", "https://source.unsplash.com/random/1080x1920");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//兼容android8.0
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void startAnim() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivWelcome, "scaleX", 1f, 1.3F);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivWelcome, "scaleY", 1f, 1.3F);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(3000).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ARouter.getInstance().build(ConfigConstants.PATH_MAIN).navigation(WelcomeActivity.this);
                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//屏蔽物理返回按钮
        }
        return super.onKeyDown(keyCode, event);
    }
}
