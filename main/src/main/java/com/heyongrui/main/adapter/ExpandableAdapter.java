package com.heyongrui.main.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.main.R;

import java.util.List;

/**
 * Created by lambert on 2018/8/29.
 * 可折叠分组适配器
 */
public class ExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int HOTEL_ROOM_TYPE = 10004;

    public ExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
//        addItemType(HOTEL_ROOM_TYPE, R.layout.adapter_item_hotel_room_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
        switch (helper.getItemViewType()) {
        }
    }
}
