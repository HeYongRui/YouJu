package com.heyongrui.base.widget.itemdecoration;


import androidx.annotation.ColorInt;

/**
 * Created by lambert on 2018/3/7.
 */

public class SideLine {
    public boolean isHave = false;
    //A single color value in the form 0xAARRGGBB.
    public int color;
    //单位px
    public int widthPx;
    //startPaddingPx,分割线起始的padding，水平方向左为start，垂直方向上为start
    public int startPaddingPx;
    //endPaddingPx,分割线尾部的padding，水平方向右为end，垂直方向下为end
    public int endPaddingPx;

    public SideLine(boolean isHave, @ColorInt int color, int widthPx, int startPaddingPx, int endPaddingPx) {
        this.isHave = isHave;
        this.color = color;
        this.widthPx = widthPx;
        this.startPaddingPx = startPaddingPx;
        this.endPaddingPx = endPaddingPx;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStartPaddingPx() {
        return startPaddingPx;
    }

    public void setStartPaddingPx(int startPaddingPx) {
        this.startPaddingPx = startPaddingPx;
    }

    public int getEndPaddingPx() {
        return endPaddingPx;
    }

    public void setEndPaddingPx(int endPaddingPx) {
        this.endPaddingPx = endPaddingPx;
    }

    public int getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(int widthPx) {
        this.widthPx = widthPx;
    }
}
