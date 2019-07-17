package com.heyongrui.module2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.collisionball.PoolBallView;
import com.heyongrui.base.widget.firefly.FireflyView;
import com.heyongrui.module2.data.dto.GankMenuDto;

import java.util.ArrayList;
import java.util.List;

public class Module2Fragment extends BaseFragment {
    private FireflyView fireflyView;
    private PoolBallView poolBall;
    private Sensor mDefaultSensor;
    private SensorManager mSensorManager;

    private SensorEventListener listerner = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                int x = (int) event.values[0];
                int y = (int) (event.values[1] * 2.0f);
                if (lastX != x || lastY != y) {//防止频繁回调,画面抖动
                    poolBall.getBallView().rockBallByImpulse(-x * 6, y * 6);
                }
                lastX = x;
                lastY = y;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private int lastX;
    private int lastY;

    public static Module2Fragment getInstance() {
        Module2Fragment fragment = new Module2Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.icon_gank);
        bundle.putInt("tabTitleId", R.string.gank);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e(TAG, "onSupportVisible: ");
        if (poolBall != null) {
            mSensorManager.registerListener(listerner, mDefaultSensor, SensorManager.SENSOR_DELAY_UI);
//            poolBall.getBallView().onStart();
        }
        if (fireflyView != null) {
            fireflyView.setVisibility(View.VISIBLE);
            fireflyView.startAnimation();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.e(TAG, "onSupportInvisible: ");
        if (poolBall != null) {
//            poolBall.getBallView().onStop();
            mSensorManager.unregisterListener(listerner);
        }
        if (fireflyView != null) {
            fireflyView.stopAnimation();
//            fireflyView.setZOrderMediaOverlay(true);
            fireflyView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fireflyView = mView.findViewById(R.id.firefly);
        initFireFlyView(fireflyView);

        poolBall = mView.findViewById(R.id.pool_ball);
        initSensor();
        initBalls();
        poolBall.getBallView().onStart();
        mSensorManager.registerListener(listerner, mDefaultSensor, SensorManager.SENSOR_DELAY_UI);
        poolBall.setOnClickListener(view -> poolBall.getBallView().rockBallByImpulse());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void initSensor() {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mDefaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void initFireFlyView(FireflyView fireflyView) {
        int[] gradientColors = new int[]{Color.parseColor("#00D2DC"), Color.parseColor("#43DDB6")};
        GradientDrawable bgDrawable = new DrawableUtil.DrawableBuilder(mContext).setGradientColors(gradientColors).createGradientDrawable();
        fireflyView.setBackgroundDrawable(bgDrawable);
    }

    private void initBalls() {
        List<GankMenuDto> menuList = getMenuList();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ConvertUtils.dp2px(70), ConvertUtils.dp2px(70));
        layoutParams.gravity = Gravity.CENTER;
        for (GankMenuDto gankMenuDto : menuList) {
            FloatingActionButton floatingActionButton = new FloatingActionButton(mContext);
            floatingActionButton.setScaleType(ImageView.ScaleType.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                floatingActionButton.setElevation(ConvertUtils.dp2px(5));
            }
            floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorPrimary)));
            floatingActionButton.setCompatElevation(ConvertUtils.dp2px(5));
            floatingActionButton.setRippleColor(Color.parseColor("#a6a6a6"));
            floatingActionButton.setBackgroundColor(Color.WHITE);
            floatingActionButton.setImageResource(gankMenuDto.getIcon_res());
            floatingActionButton.setTag(R.id.circle_tag, true);
            floatingActionButton.setOnClickListener(view -> {
                switch (gankMenuDto.getType()) {
                    case 1://福利
                        ARouter.getInstance().build(ConfigConstants.PATH_WELFARE).navigation();
                        break;
                    case 9://关于
                        ARouter.getInstance().build(ConfigConstants.PATH_ABOUT).navigation();
                        break;
                }
            });
            poolBall.addView(floatingActionButton, layoutParams);
        }
    }

    private List<GankMenuDto> getMenuList() {
        List<GankMenuDto> gankMenuDtos = new ArrayList<>();
        gankMenuDtos.add(new GankMenuDto(getString(R.string.welfare), R.drawable.ic_welfare, 1));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.android), R.drawable.ic_android, 2));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.ios), R.drawable.ic_ios, 3));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.rest_video), R.drawable.ic_video, 4));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.front_end), R.drawable.ic_js, 5));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.expand), R.drawable.ic_expand, 6));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.app), R.drawable.ic_app, 7));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.blind_recommend), R.drawable.ic_recommend, 8));
        gankMenuDtos.add(new GankMenuDto(getString(R.string.about), R.drawable.ic_about, 9));
        return gankMenuDtos;
    }
}
