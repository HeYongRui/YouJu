package com.heyongrui.main.mob.contract;

import androidx.annotation.FloatRange;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.data.dto.WeatherDto;

import java.util.List;

public interface MobWeatherContract {
    interface View extends BaseView {
        void weatherQuerySuccess(List<WeatherDto> weatherDtoList);

        void playSunAnim(@FloatRange(from = 0.0, to = 1.0) float persent);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract HomeSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener);

        public abstract void weatherQuery(String cityName);

        public abstract String getAnimJsonFromWeather(String weather);

        public abstract void getAddressByLocation(double lat, double lon);
    }
}
