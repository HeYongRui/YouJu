package com.heyongrui.module2.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.heyongrui.module2.data.dto.GankDto;
import com.heyongrui.module2.data.dto.HistoryTodayDto;
import com.heyongrui.module2.data.dto.LeisureReadDto;

import java.util.List;

/**
 * lambert
 * 2019/6/25 18:13
 */
public class Module2SectionEntity extends SectionMultiEntity implements MultiItemEntity {

    public static final int WELFARE = 100;
    public static final int GANK = 101;
    public static final int LEISURE_READ = 102;
    public static final int TODAY_HISTORY = 103;

    private int itemType;
    private int spanSize;

    private Object object;
    private GankDto gankDto;
    private LeisureReadDto leisureReadDto;
    private HistoryTodayDto.HistoryTodayBean historyTodayBean;

    public Module2SectionEntity(boolean isHeader, String header, boolean isShow) {
        super(isHeader, header);
    }

    public Module2SectionEntity(int itemType, Object object) {
        this(itemType, 1, object);
    }

    public Module2SectionEntity(int itemType, int spanSize, Object object) {
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
            } else if (object instanceof GankDto) {
                this.gankDto = (GankDto) object;
            } else if (object instanceof LeisureReadDto) {
                this.leisureReadDto = (LeisureReadDto) object;
            } else if (object instanceof HistoryTodayDto.HistoryTodayBean) {
                this.historyTodayBean = (HistoryTodayDto.HistoryTodayBean) object;
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

    public GankDto getGankDto() {
        return gankDto;
    }

    public void setGankDto(GankDto gankDto) {
        this.gankDto = gankDto;
    }

    public LeisureReadDto getLeisureReadDto() {
        return leisureReadDto;
    }

    public void setLeisureReadDto(LeisureReadDto leisureReadDto) {
        this.leisureReadDto = leisureReadDto;
    }

    public HistoryTodayDto.HistoryTodayBean getHistoryTodayBean() {
        return historyTodayBean;
    }

    public void setHistoryTodayBean(HistoryTodayDto.HistoryTodayBean historyTodayBean) {
        this.historyTodayBean = historyTodayBean;
    }
}
