package com.heyongrui.base.widget.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An implement of {@link RecyclerView} which support auto play.
 */

public class AutoPlayRecyclerView extends RecyclerView {

    private AutoPlaySnapHelper autoPlaySnapHelper;

    public AutoPlayRecyclerView(Context context) {
        this(context, null);
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        autoPlaySnapHelper = new AutoPlaySnapHelper(AutoPlaySnapHelper.TIME_INTERVAL, AutoPlaySnapHelper.RIGHT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (autoPlaySnapHelper != null) {
                    autoPlaySnapHelper.pause();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (autoPlaySnapHelper != null) {
                    autoPlaySnapHelper.start();
                }
        }
        return result;
    }

    public void setTimeInterval(int timeInterval) {
        if (null != autoPlaySnapHelper) {
            autoPlaySnapHelper.setTimeInterval(timeInterval);
        }
    }

    public void setDirection(int direction) {
        if (null != autoPlaySnapHelper) {
            autoPlaySnapHelper.setDirection(direction);
        }
    }

    public void start() {
        autoPlaySnapHelper.start();
    }

    public void pause() {
        autoPlaySnapHelper.pause();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        autoPlaySnapHelper.attachToRecyclerView(this);
    }
}
