package com.heyongrui.main.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.WeatherDto;

import java.util.List;

/**
 * lambert
 * 2019/6/25 18:13
 */
public class HomeSectionEntity extends SectionMultiEntity implements MultiItemEntity {

    public static final int FLIGHT = 100;
    public static final int WEATHER = 101;

    private int itemType;
    private int spanSize;

    private Object object;
    private FlightDto flightDto;
    private WeatherDto.FutureBean futureBean;

    public HomeSectionEntity(boolean isHeader, String header, boolean isShow) {
        super(isHeader, header);
    }

    public HomeSectionEntity(int itemType, Object object) {
        this(itemType, 1, object);
    }

    public HomeSectionEntity(int itemType, int spanSize, Object object) {
        super(object);
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.object = object;
        if (object != null) {
            if (object instanceof List && ((List) object).size() > 0) {
                Object o = ((List) object).get(0);
//                if (o instanceof BannerDto) {
//                    this.bannerDtoList = (List<BannerDto>) object;
//                }
            } else if (object instanceof FlightDto) {
                this.flightDto = (FlightDto) object;
            } else if (object instanceof WeatherDto.FutureBean) {
                this.futureBean = (WeatherDto.FutureBean) object;
            }
        }
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public void setFlightDto(FlightDto flightDto) {
        this.flightDto = flightDto;
    }

    public WeatherDto.FutureBean getFutureBean() {
        return futureBean;
    }

    public void setFutureBean(WeatherDto.FutureBean futureBean) {
        this.futureBean = futureBean;
    }
}
