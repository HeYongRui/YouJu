package com.heyongrui.main.mob.presenter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heyongrui.base.app.BaseApplication;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.adapter.HomeSectionEntity;
import com.heyongrui.main.dagger.DaggerHomeComponent;
import com.heyongrui.main.dagger.HomeModule;
import com.heyongrui.main.data.dto.GeocoderDto;
import com.heyongrui.main.data.dto.MobResponse;
import com.heyongrui.main.data.dto.WeatherDto;
import com.heyongrui.main.data.service.MobService;
import com.heyongrui.main.mob.contract.MobWeatherContract;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class MobWeatherPresenter extends MobWeatherContract.Presenter {

    @Inject
    RxManager mRxManager;
    @Inject
    MobService mMobService;

    public MobWeatherPresenter() {
        DaggerHomeComponent
                .builder()
                .appComponent(BaseApplication.getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    public HomeSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener) {
        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(mContext)
                .setColor(Color.parseColor("#4F747474")).setGradientRoundRadius(20)
                .createGradientDrawable();
        ViewCompat.setBackground(recyclerView, gradientDrawable);
        List<HomeSectionEntity> data = new ArrayList<>();
        HomeSectionAdapter moduleSectionAdapter = new HomeSectionAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        moduleSectionAdapter.bindToRecyclerView(recyclerView);
        int dp1 = ConvertUtils.dp2px(0.5f);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(mContext, dp1, Color.WHITE));
//        moduleSectionAdapter.setSpanSizeLookup((gridLayoutManager, position) -> data.get(position).getSpanSize());
        if (null != listener) {
            moduleSectionAdapter.setOnItemClickListener(listener);
        }
        return moduleSectionAdapter;
    }

    @Override
    public void weatherQuery(String cityName) {
        mRxManager.add(mMobService.weatherQueryByCity(cityName)
                .subscribeWith(new ResponseDisposable<MobResponse<List<WeatherDto>>>(mContext, false) {
                    @Override
                    protected void onSuccess(MobResponse<List<WeatherDto>> listMobResponse) {
                        if (null != listMobResponse) {
                            if (200 == listMobResponse.getRetCode()) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR_OF_DAY, 6);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);

                                long start = calendar.getTimeInMillis();

                                calendar.set(Calendar.HOUR_OF_DAY, 18);
                                long end = calendar.getTimeInMillis();

                                long current = Calendar.getInstance().getTimeInMillis();

                                float molecule = current - start;
                                molecule = molecule <= 0 ? 0 : molecule;
                                float persent = (float) (molecule) / (end - start);
                                persent = persent >= 1 ? 1 : persent;
                                if (null != mView) {
                                    mView.playSunAnim(persent);
                                    mView.weatherQuerySuccess(listMobResponse.getResult());
                                }
                            } else {
                                ToastUtils.showShort(listMobResponse.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    public void getAddressByLocation(double lat, double lon) {
        mRxManager.add(mMobService.getAddressByLocation(lat, lon)
                .subscribeWith(new ResponseDisposable<GeocoderDto>(mContext, false) {
                    @Override
                    protected void onSuccess(GeocoderDto geocoderDto) {
                        if (null != geocoderDto) {
                            GeocoderDto.ResultBean result = geocoderDto.getResult();
                            if (null != result) {
                                GeocoderDto.ResultBean.AddressComponentBean addressComponent = result.getAddressComponent();
                                if (null != addressComponent) {
                                    String city = addressComponent.getCity();
                                    if (!TextUtils.isEmpty(city)) {
                                        try {
                                            city = city.replace("市", "");
                                            weatherQuery(city);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }
}
