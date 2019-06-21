package com.heyongrui.base.widget.catloadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.FloatRange;

/**
 * Created by lambert on 2018/10/15.
 */

public class EyelidView extends View {

    private float mEyelidHeightPercent;
    private Paint mPaint;

    public EyelidView(Context context) {
        super(context);
        init();
    }

    public EyelidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EyelidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        setBackgroundDrawable(null);
        setFocusable(false);
        setEnabled(false);
        setFocusableInTouchMode(false);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setEyelidHeightPercent(@FloatRange(from = 0f, to = 1f) float heightPercent) {
        if (this.mEyelidHeightPercent != heightPercent) {
            this.mEyelidHeightPercent = heightPercent;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bottom = mEyelidHeightPercent * getHeight();
        canvas.drawRect(0, 0, getWidth(), bottom, mPaint);
    }
}
