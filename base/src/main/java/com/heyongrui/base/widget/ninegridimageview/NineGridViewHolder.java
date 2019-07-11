package com.heyongrui.base.widget.ninegridimageview;

import android.content.Context;

/**
 * Created by lambert on 2018/11/6.
 */

public interface NineGridViewHolder<T> {
    /**
     * 是否自定义单张图片时的view参数
     *
     * @return true-按照自定义宽高显示  false-按照九宫格默认大小显示
     */
    boolean isCustomSingleParam(Context context, RatioImageView ratioImageView, T t, int parentWidth);

    void onBind(Context context, int position, RatioImageView ratioImageView, T t);

    void onItemClick(Context context, int position, RatioImageView ratioImageView, T t);
}
