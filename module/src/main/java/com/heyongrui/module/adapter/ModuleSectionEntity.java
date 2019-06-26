package com.heyongrui.module.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.heyongrui.module.data.dto.KaiYanItemBean;

import java.util.List;

/**
 * lambert
 * 2019/6/25 18:13
 */
public class ModuleSectionEntity extends SectionMultiEntity implements MultiItemEntity {

    public static final int KAIYAN = 100;

    private int itemType;
    private int spanSize;

    private KaiYanItemBean kaiYanItemBean;

    public ModuleSectionEntity(boolean isHeader, String header, boolean isShow) {
        super(isHeader, header);
    }

    public ModuleSectionEntity(int itemType, Object object) {
        this(itemType, 1, object);
    }

    public ModuleSectionEntity(int itemType, int spanSize, Object object) {
        super(object);
        this.itemType = itemType;
        this.spanSize = spanSize;
        if (object != null) {
            if (object instanceof List && ((List) object).size() > 0) {
                Object o = ((List) object).get(0);
//                if (o instanceof BannerDto) {
//                    this.bannerDtoList = (List<BannerDto>) object;
//                }
            } else if (object instanceof KaiYanItemBean) {
                this.kaiYanItemBean = (KaiYanItemBean) object;
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

    public KaiYanItemBean getKaiYanItemBean() {
        return kaiYanItemBean;
    }

    public void setKaiYanItemBean(KaiYanItemBean kaiYanItemBean) {
        this.kaiYanItemBean = kaiYanItemBean;
    }
}
