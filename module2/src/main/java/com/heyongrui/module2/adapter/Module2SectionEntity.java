package com.heyongrui.module2.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.heyongrui.module2.data.dto.WelfareDto;

import java.util.List;

/**
 * lambert
 * 2019/6/25 18:13
 */
public class Module2SectionEntity extends SectionMultiEntity implements MultiItemEntity {

    public static final int WELFARE = 100;

    private int itemType;
    private int spanSize;

    private Object object;
    private WelfareDto.WelfareBean welfareBean;

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
            } else if (object instanceof WelfareDto.WelfareBean) {
                this.welfareBean = (WelfareDto.WelfareBean) object;
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

    public WelfareDto.WelfareBean getWelfareBean() {
        return welfareBean;
    }

    public void setWelfareBean(WelfareDto.WelfareBean welfareBean) {
        this.welfareBean = welfareBean;
    }
}
