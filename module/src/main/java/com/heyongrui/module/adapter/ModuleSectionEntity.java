package com.heyongrui.module.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import com.heyongrui.module.data.dto.GarbageCardBean;
import com.heyongrui.module.data.dto.KaiYanItemBean;
import com.heyongrui.module.data.dto.MenuCardDto;
import com.heyongrui.module.data.dto.MonoCategoryDto;
import com.heyongrui.module.data.dto.MonoHistoryTeaDateDto;
import com.heyongrui.module.data.dto.MonoTeaDto;

import java.util.List;

/**
 * lambert
 * 2019/6/25 18:13
 */
public class ModuleSectionEntity extends SectionMultiEntity implements MultiItemEntity {

    public static final int KAIYAN_ONE = 100;
    public static final int KAIYAN_TWO = 101;
    public static final int GARBAGE = 103;
    public static final int GARBAGE_CARD = 104;
    public static final int MENU_CARD = 105;
    public static final int TEA_NORMAL = 106;
    public static final int TEA_NINE_GRID = 107;
    public static final int MONO_HISTORY_DATE = 108;
    public static final int MONO_CATEGORY = 109;

    private int itemType;
    private int spanSize;

    private Object object;
    private KaiYanItemBean kaiYanItemBean;
    private GarbageCardBean garbageCardBean;
    private MenuCardDto menuCardDto;
    private MonoTeaDto.EntityListBean entityListBean;
    private MonoHistoryTeaDateDto.RecentTeaBean recentTeaBean;
    private MonoCategoryDto.MeowsBean meowsBean;

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
        this.object = object;
        if (object != null) {
            if (object instanceof List && ((List) object).size() > 0) {
                Object o = ((List) object).get(0);
//                if (o instanceof BannerDto) {
//                    this.bannerDtoList = (List<BannerDto>) object;
//                }
            } else if (object instanceof KaiYanItemBean) {
                this.kaiYanItemBean = (KaiYanItemBean) object;
            } else if (object instanceof GarbageCardBean) {
                this.garbageCardBean = (GarbageCardBean) object;
            } else if (object instanceof MenuCardDto) {
                this.menuCardDto = (MenuCardDto) object;
            } else if (object instanceof MonoTeaDto.EntityListBean) {
                this.entityListBean = (MonoTeaDto.EntityListBean) object;
            } else if (object instanceof MonoHistoryTeaDateDto.RecentTeaBean) {
                this.recentTeaBean = (MonoHistoryTeaDateDto.RecentTeaBean) object;
            } else if (object instanceof MonoCategoryDto.MeowsBean) {
                this.meowsBean = (MonoCategoryDto.MeowsBean) object;
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

    public KaiYanItemBean getKaiYanItemBean() {
        return kaiYanItemBean;
    }

    public void setKaiYanItemBean(KaiYanItemBean kaiYanItemBean) {
        this.kaiYanItemBean = kaiYanItemBean;
    }

    public GarbageCardBean getGarbageCardBean() {
        return garbageCardBean;
    }

    public void setGarbageCardBean(GarbageCardBean garbageCardBean) {
        this.garbageCardBean = garbageCardBean;
    }

    public MenuCardDto getMenuCardDto() {
        return menuCardDto;
    }

    public void setMenuCardDto(MenuCardDto menuCardDto) {
        this.menuCardDto = menuCardDto;
    }

    public MonoTeaDto.EntityListBean getEntityListBean() {
        return entityListBean;
    }

    public void setEntityListBean(MonoTeaDto.EntityListBean entityListBean) {
        this.entityListBean = entityListBean;
    }

    public MonoHistoryTeaDateDto.RecentTeaBean getRecentTeaBean() {
        return recentTeaBean;
    }

    public void setRecentTeaBean(MonoHistoryTeaDateDto.RecentTeaBean recentTeaBean) {
        this.recentTeaBean = recentTeaBean;
    }

    public MonoCategoryDto.MeowsBean getMeowsBean() {
        return meowsBean;
    }

    public void setMeowsBean(MonoCategoryDto.MeowsBean meowsBean) {
        this.meowsBean = meowsBean;
    }
}
