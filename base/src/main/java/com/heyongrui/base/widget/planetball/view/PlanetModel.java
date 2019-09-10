package com.heyongrui.base.widget.planetball.view;

import android.graphics.Color;
import android.view.View;

/**
 * 星球属性实体
 */
public class PlanetModel {

    /**
     * 默认权重
     */
    private static final int DEFAULT_POPULARITY = 5;
    /**
     * 权重
     */
    private int popularity;
    /**
     * 3D坐标位置
     */
    private float locX, locY, locZ;
    /**
     * 2D坐标位置
     */
    private float loc2DX, loc2DY;
    /**
     * 缩放比
     */
    private float scale;
    /**
     * 透明度
     */
    private float alpha;
    /**
     * 颜色
     */
    private float[] argb;
    /**
     * View
     */
    private View mView;


    public PlanetModel() {
        this(0f, 0f, 0f, 1.0f, 0);
    }

    public PlanetModel(float locX, float locY, float locZ, float scale, int popularity) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;

        this.loc2DX = 0;
        this.loc2DY = 0;

        this.argb = new float[]{1.0f, 0.5f, 0.5f, 0.5f};

        this.scale = scale;
        this.popularity = popularity;
    }

    public PlanetModel(int popularity) {
        this(0f, 0f, 0f, 1.0f, popularity);
    }

    public PlanetModel(float locX, float locY, float locZ) {
        this(locX, locY, locZ, 1.0f, DEFAULT_POPULARITY);
    }

    public PlanetModel(float locX, float locY, float locZ, float scale) {
        this(locX, locY, locZ, scale, DEFAULT_POPULARITY);
    }

    public float getLocX() {
        return locX;
    }

    public void setLocX(float locX) {
        this.locX = locX;
    }

    public float getLocY() {
        return locY;
    }

    public void setLocY(float locY) {
        this.locY = locY;
    }

    public float getLocZ() {
        return locZ;
    }

    public void setLocZ(float locZ) {
        this.locZ = locZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        if (null != this.mView && this.mView instanceof PlanetView) {
            ((PlanetView) this.mView).setScale(scale);
        }
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.argb[0] = alpha;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public float getLoc2DX() {
        return loc2DX;
    }

    public void setLoc2DX(float loc2dx) {
        loc2DX = loc2dx;
    }

    public float getLoc2DY() {
        return loc2DY;
    }

    public void setLoc2DY(float loc2dy) {
        loc2DY = loc2dy;
    }

    public void setColorByArray(float[] rgb) {
        if (rgb != null) {
            System.arraycopy(rgb, 0, this.argb, this.argb.length - rgb.length, rgb.length);
        }
    }

    public int getColor() {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (int) (this.argb[i] * 0xff);
        }
        return Color.argb(result[0], result[1], result[2], result[3]);
    }
}