package com.heyongrui.base.widget.itemdecoration;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.heyongrui.base.R;

/**
 * Created by lambert on 2018/3/7.
 */

public class RecycleViewItemDecoration extends BaseItemDecoration {

    private int mWidthPx;
    private int mColor;

    public RecycleViewItemDecoration(Context context) {
        this(context, ConvertUtils.dp2px(10));
    }

    public RecycleViewItemDecoration(Context context, int widthPx) {
        this(context, widthPx, ContextCompat.getColor(context, R.color.window_background));
    }

    public RecycleViewItemDecoration(Context context, int widthPx, @ColorInt int color) {
        super(context);
        mWidthPx = widthPx;
        mColor = color;
    }

    @Override
    public Divider getDivider(int itemPosition) {
        DividerBuilder dividerBuilder = new DividerBuilder();
        dividerBuilder.setBottomSideLine(true, mColor, mWidthPx, 0, 0);
        Divider divider = dividerBuilder.create();
        return divider;
    }
}