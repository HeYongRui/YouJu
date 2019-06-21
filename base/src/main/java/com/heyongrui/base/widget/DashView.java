package com.heyongrui.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.heyongrui.base.R;


/**
 * Created by lambert on 2019/5/13.
 */

public class DashView extends View {
    //虚线颜色
    private int mDashLineColor;
    //虚线长度
    private int mDashLineLength;
    //虚线间隙长度
    private int mDashLinespaceLength;
    //虚线方向 0横 1竖
    private int orientation;
    //画笔
    private Paint mPaint;

    public DashView(Context context) {
        super(context);
        init(context, null);
    }

    public DashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashView);

        orientation = typedArray.getInt(R.styleable.DashView_orientation, 0);
        mDashLineColor = typedArray.getColor(R.styleable.DashView_dash_color, 0xFFCCCCCC);
        mDashLineLength = typedArray.getInt(R.styleable.DashView_dash_length, 10);
        mDashLinespaceLength = typedArray.getInt(R.styleable.DashView_dash_space_length, 5);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mDashLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new DashPathEffect(new float[]{mDashLineLength, mDashLinespaceLength}, 0));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取宽高
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        //根据方向绘制虚线
        switch (orientation) {
            case 0://横向
                mPaint.setStrokeWidth(measuredHeight);
                Path horizonPath = new Path();
                horizonPath.moveTo(0, measuredHeight / 2);
                horizonPath.lineTo(measuredWidth, measuredHeight / 2);
                canvas.drawPath(horizonPath, mPaint);
                break;
            case 1://竖直
                mPaint.setStrokeWidth(measuredWidth);
                Path vertiPath = new Path();
                vertiPath.moveTo(measuredWidth / 2, 0);
                vertiPath.lineTo(measuredWidth / 2, measuredHeight);
                canvas.drawPath(vertiPath, mPaint);
                break;
        }
    }

    public void setmDashLineColor(int mDashLineColor) {
        this.mDashLineColor = mDashLineColor;
        invalidate();
    }

    public void setmDashLineLength(int mDashLineLength) {
        this.mDashLineLength = mDashLineLength;
        invalidate();
    }

    public void setmDashLinespaceLength(int mDashLinespaceLength) {
        this.mDashLinespaceLength = mDashLinespaceLength;
        invalidate();
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        invalidate();
    }
}
