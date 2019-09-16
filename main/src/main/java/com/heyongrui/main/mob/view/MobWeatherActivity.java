package com.heyongrui.main.mob.view;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.FloatRange;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DialogUtil;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.LocationUtil;
import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.base.widget.numberruntextview.NumberRunningTextView;
import com.heyongrui.main.R;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.adapter.HomeSectionEntity;
import com.heyongrui.main.data.dto.WeatherDto;
import com.heyongrui.main.mob.BezierEvaluator;
import com.heyongrui.main.mob.contract.MobWeatherContract;
import com.heyongrui.main.mob.presenter.MobWeatherPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Route(path = ConfigConstants.PATH_WEATHER)
public class MobWeatherActivity extends BaseActivity<MobWeatherContract.Presenter> implements
        MobWeatherContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private ImageView ivSun;
    private ImageView ivMask;

    private HomeSectionAdapter mWeatherAdapter;

    private LocationUtil mLocationUtil;
    private Location mLocation;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (null == mLocation) {
                if (null != location) {
                    mLocation = location;
                    if (null != mPresenter) {
                        mPresenter.getAddressByLocation(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private LottieAnimationView lottieView;
    private NumberRunningTextView numberRunTv;
    private TextView tvWeather;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected MobWeatherContract.Presenter setPresenter() {
        return new MobWeatherPresenter();
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
        ImageFilterView ifvBg = findViewById(R.id.ifv_bg);
        int[] resIds = new int[]{R.drawable.bg_weather_one, R.drawable.bg_weather_two, R.drawable.bg_weather_three};
        ifvBg.setImageResource(resIds[new Random().nextInt(3)]);
        ivSun = findViewById(R.id.iv_sun);
        ivMask = findViewById(R.id.iv_mask);
        lottieView = findViewById(R.id.lottie_view);
        numberRunTv = findViewById(R.id.number_run_tv);
        tvWeather = findViewById(R.id.tv_weather);
        RecyclerView rlvWeather = findViewById(R.id.rlv_weather);

        ivMask.getBackground().mutate().setAlpha(0);
        mWeatherAdapter = mPresenter.initRecyclerView(rlvWeather, this);

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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void weatherQuerySuccess(List<WeatherDto> weatherDtoList) {
        if (null != weatherDtoList) {
            WeatherDto weatherDto = weatherDtoList.get(0);
            String temperature = weatherDto.getTemperature();
            temperature = temperature.replace("℃", "");
            numberRunTv.setContent(temperature);

            StringBuilder weatherBuilder = new StringBuilder();
            String weather = weatherDto.getWeather();
            String week = weatherDto.getWeek();
            String wind = weatherDto.getWind();
            String date = weatherDto.getDate();
            date = TimeUtil.getDateString(date, "yyyy-MM-dd", "yyyy/MM/dd");
            weatherBuilder.append(date);
            weatherBuilder.append("\t");
            weatherBuilder.append(week);
            weatherBuilder.append("\n");
            weatherBuilder.append(weather);
            weatherBuilder.append("\t");
            weatherBuilder.append(wind);
            tvWeather.setText(weatherBuilder);

            lottieView.setAnimation(mPresenter.getAnimJsonFromWeather(weather));
            lottieView.setRepeatCount(ValueAnimator.INFINITE);
            lottieView.playAnimation();

            List<HomeSectionEntity> dataList = new ArrayList<>();
            List<WeatherDto.FutureBean> futureBeanList = weatherDto.getFuture();
            if (null != futureBeanList) {
                for (WeatherDto.FutureBean futureBean : futureBeanList) {
                    dataList.add(new HomeSectionEntity(HomeSectionEntity.WEATHER, futureBean));
                }
            }
            mWeatherAdapter.replaceData(dataList);
        }
    }

    @Override
    public void playSunAnim(@FloatRange(from = 0.0, to = 1.0) float stopPercent) {
        int screenWidth = ScreenUtils.getScreenWidth();
        int screenHeight = ScreenUtils.getScreenHeight();

        float startX = 0;
        float startY = screenHeight / 4;

        float endX = screenWidth;
        float endY = startY;

        float controlY = -200;
        float controlX = screenWidth / 2;
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
        ivSun.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .rationale(shouldRequest -> shouldRequest.again(true))
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        mLocationUtil = null != mLocationUtil ? mLocationUtil : new LocationUtil(MobWeatherActivity.this);
                        mLocationUtil.addLocationListener(mLocationListener);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        DialogUtil.showPermissionDialog(MobWeatherActivity.this, getString(R.string.location_permisson));
                    }
                }).request();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mLocationUtil) {
            mLocationUtil.removeLocationUpdatesListener();
        }
    }
}
