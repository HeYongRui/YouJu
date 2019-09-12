package com.heyongrui.main.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lambert on 2018/11/13.
 * 拖拽、滑动适配器
 */
public class DragAndSwipeAdapter extends BaseItemDraggableAdapter<Object, BaseViewHolder> {
    private int mItemType;

    public DragAndSwipeAdapter(List<Object> data, int itemType) {
        super(data);
        mItemType = itemType;
    }

    public DragAndSwipeAdapter(int layoutResId, int itemType, List<Object> data) {
        super(layoutResId, data);
        mItemType = itemType;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        switch (mItemType) {

        }
    }
}
