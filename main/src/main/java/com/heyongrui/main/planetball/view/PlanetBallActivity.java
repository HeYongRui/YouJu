package com.heyongrui.main.planetball.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.widget.firefly.FireflyView;
import com.heyongrui.base.widget.planetball.adapter.PlanetAdapter;
import com.heyongrui.base.widget.planetball.view.PlanetBallView;
import com.heyongrui.base.widget.planetball.view.PlanetView;
import com.heyongrui.main.R;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Route(path = ConfigConstants.PATH_PLANET_BALL)
public class PlanetBallActivity extends BaseActivity {

    private FireflyView fireflyView;
    private Handler mHandler = new Handler();

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_planet_ball;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        PlanetBallView planetBall = findViewById(R.id.planet_ball);
        initPlanetBallView(planetBall);

        fireflyView = findViewById(R.id.firefly);
    }

    private void initPlanetBallView(PlanetBallView planetBallView) {
        planetBallView.setAdapter(new PlanetAdapter() {
            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public View getView(Context context, int position, ViewGroup parent) {
                int starColor = position % 2 == 0 ? PlanetView.COLOR_FEMALE : PlanetView.COLOR_MALE;
                boolean isShining = false;

                String str = "";
                if (position % 12 == 0) {
                    str = "最活跃";
                    starColor = PlanetView.COLOR_MOST_ACTIVE;
                } else if (position % 20 == 0) {
                    str = "最匹配";
                    starColor = PlanetView.COLOR_BEST_MATCH;
                } else if (position % 33 == 0) {
                    str = "最新人";
                    starColor = PlanetView.COLOR_MOST_NEW;
                } else if (position % 18 == 0) {
                    isShining = true;
                    str = "最闪耀";
                } else {
                    str = "描述";
                }

                PlanetView planetView = new PlanetView(context);
                int[] textColor = new int[]{0x33333333, getRandomColor(), getRandomColor(), 0x33333333};
                float[] colorPosition = new float[]{0.0f, 0.15f, 0.85f, 1.0f};
                planetView.setTextColor(textColor, colorPosition);
                planetView.setStarColor(starColor);
                String nickName = getRandomNick();
                planetView.setSign(nickName);
                planetView.setHasShadow(isShining);
                planetView.setMatch(position * 2 + "%", str);
                if (isShining) {
                    planetView.setMatchColor(starColor);
                } else {
                    planetView.setMatchColor(PlanetView.COLOR_MOST_ACTIVE);
                }
                int starWidth = ConvertUtils.dp2px(50.0f);
                int starHeight = ConvertUtils.dp2px(85.0f);
                int starPaddingTop = ConvertUtils.dp2px(20.0f);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(starWidth, starHeight);
                planetView.setPadding(0, starPaddingTop, 0, 0);
                planetView.setLayoutParams(layoutParams);
                planetView.setOnClickListener(view -> ToastUtils.showShort(nickName + "\n" + position * 2 + "%"));
                return planetView;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public int getPopularity(int position) {
                return position % 10;
            }

            @Override
            public void onThemeColorChanged(View view, int themeColor) {

            }
        });
    }

    /**
     * 获取随机颜色
     */
    private int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.argb(255, r, g, b);
    }

    /**
     * 获取随机昵称
     */
    private String getRandomNick() {
        Random random = new Random();
        int len = random.nextInt(12) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(getRandomSingleCharacter());
        }
        return builder.toString();
    }

    /**
     * 获取随机单个汉字
     */
    private String getRandomSingleCharacter() {
        String str = "";
        int heightPos;
        int lowPos;
        Random rd = new Random();
        heightPos = 176 + Math.abs(rd.nextInt(39));
        lowPos = 161 + Math.abs(rd.nextInt(93));
        byte[] bt = new byte[2];
        bt[0] = Integer.valueOf(heightPos).byteValue();
        bt[1] = Integer.valueOf(lowPos).byteValue();
        try {
            str = new String(bt, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 控制萤火虫粒子动画是否播放
     */
    private void toggleAnimationEffect(boolean isStart) {
        if (isStart) {
            if (fireflyView != null && !fireflyView.isPlaying()) {
                fireflyView.setVisibility(View.VISIBLE);
                fireflyView.startAnimation();
            }
        } else {
            if (fireflyView != null && fireflyView.isPlaying()) {
                fireflyView.stopAnimation();
//                    fireflyView.setZOrderMediaOverlay(true);
                fireflyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(() -> toggleAnimationEffect(true), 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(() -> toggleAnimationEffect(false), 1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }
}
