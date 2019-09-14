package com.heyongrui.main.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.main.R;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.WeatherDto;

import java.util.List;


/**
 * lambert
 * 2019/6/25 18:17
 */
public class HomeSectionAdapter extends BaseSectionMultiItemQuickAdapter<HomeSectionEntity, BaseViewHolder> {

    public HomeSectionAdapter(List<HomeSectionEntity> data) {
        this(0, data);
    }

    public HomeSectionAdapter(int sectionHeadResId, List<HomeSectionEntity> data) {
        super(sectionHeadResId, data);
        addItemType(HomeSectionEntity.FLIGHT, R.layout.recycle_item_flight);
        addItemType(HomeSectionEntity.WEATHER, R.layout.recycle_item_weather);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, HomeSectionEntity item) {
//        switch (item.getItemType()) {
//            case HomeSectionEntity.FLIGHT:
//                TextView tvHeadSection = helper.getView(android.R.id.text1);
//                if (null != tvHeadSection) {
//                    tvHeadSection.setText(TextUtils.isEmpty(item.header) ? "" : item.header);
//                }
//                break;
//        }
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeSectionEntity item) {
        switch (helper.getItemViewType()) {
            case HomeSectionEntity.FLIGHT: {
                String flightCompany = "", flightNumber = "", flightRate = "", flightTime = "", fromAirport = "",
                        fromAirportCode = "", fromCity = "", fromCityCode = "", fromTerminal = "", planTime = "",
                        planArriveTime = "", toAirport = "", toAirportCode = "", toCity = "", toCityCode = "",
                        toTerminal = "", flightCycle = "";
                FlightDto flightDto = item.getFlightDto();
                if (null != flightDto) {
                    flightCompany = flightDto.getAirLines();
                    flightNumber = flightDto.getFlightNo();
                    flightRate = flightDto.getFlightRate();
                    flightTime = flightDto.getFlightTime();
                    fromAirport = flightDto.getFrom();
                    fromAirportCode = flightDto.getFromAirportCode();
                    fromCity = flightDto.getFromCityName();
                    fromCityCode = flightDto.getFromCityCode();
                    fromTerminal = flightDto.getFromTerminal();
                    planTime = flightDto.getPlanTime();
                    planArriveTime = flightDto.getPlanArriveTime();
                    toAirport = flightDto.getTo();
                    toAirportCode = flightDto.getToAirportCode();
                    toCity = flightDto.getToCityName();
                    toCityCode = flightDto.getToCityCode();
                    toTerminal = flightDto.getToTerminal();
                    flightCycle = flightDto.getWeek();
                }
                helper.setText(R.id.tv_company, TextUtils.isEmpty(flightCompany) ? "" : flightCompany);
                helper.setText(R.id.tv_flight_number, TextUtils.isEmpty(flightNumber) ? "" : flightNumber);
                helper.setText(R.id.tv_flight_rate, TextUtils.isEmpty(flightRate) ? "" : flightRate);
                helper.setText(R.id.tv_flight_time, TextUtils.isEmpty(flightTime) ? "" : flightTime);
                helper.setText(R.id.tv_from_airport, TextUtils.isEmpty(fromAirport) ? "" : fromAirport);
                helper.setText(R.id.tv_from_airport_code, TextUtils.isEmpty(fromAirportCode) ? "" : fromAirportCode);
                helper.setText(R.id.tv_from_city_name, TextUtils.isEmpty(fromCity) ? "" : fromCity);
                helper.setText(R.id.tv_from_city_code, TextUtils.isEmpty(fromCityCode) ? "" : fromCityCode);
                helper.setText(R.id.tv_from_terminal, TextUtils.isEmpty(fromTerminal) ? "" : fromTerminal);
                helper.setText(R.id.tv_plan_time, TextUtils.isEmpty(planTime) ? "" : planTime);
                helper.setText(R.id.tv_plan_arrive_time, TextUtils.isEmpty(planArriveTime) ? "" : planArriveTime);
                helper.setText(R.id.tv_to_airport, TextUtils.isEmpty(toAirport) ? "" : toAirport);
                helper.setText(R.id.tv_to_airport_code, TextUtils.isEmpty(toAirportCode) ? "" : toAirportCode);
                helper.setText(R.id.tv_to_city_name, TextUtils.isEmpty(toCity) ? "" : toCity);
                helper.setText(R.id.tv_to_city_code, TextUtils.isEmpty(toCityCode) ? "" : toCityCode);
                helper.setText(R.id.tv_to_terminal, TextUtils.isEmpty(toTerminal) ? "" : toTerminal);
                helper.setText(R.id.tv_flight_cycle, TextUtils.isEmpty(flightCycle) ? "" : flightCycle);
            }
            break;
            case HomeSectionEntity.WEATHER: {
                WeatherDto.FutureBean futureBean = item.getFutureBean();
                String temperature = "";
                StringBuilder date = new StringBuilder();
                StringBuilder weather = new StringBuilder();
                if (null != futureBean) {
                    date.append(TimeUtil.getDateString(futureBean.getDate(), "yyyy-MM-dd", "MM/dd"));
                    date.append("\t");
                    date.append(futureBean.getWeek());

                    weather.append(futureBean.getDayTime());
//                    weather.append("\t");
//                    weather.append(futureBean.getWind());

                    temperature = futureBean.getTemperature();
                }
                helper.setText(R.id.tv_date, TextUtils.isEmpty(date) ? "" : date);
                helper.setText(R.id.tv_weather, TextUtils.isEmpty(weather) ? "" : weather);
                helper.setText(R.id.tv_temperature, TextUtils.isEmpty(temperature) ? "" : temperature);
            }
            break;
        }
    }
}
