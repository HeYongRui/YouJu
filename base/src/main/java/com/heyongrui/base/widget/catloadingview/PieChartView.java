package com.heyongrui.base.widget.catloadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.heyongrui.base.R;

import java.util.List;


/**
 * Created by lambert on 2018/11/29.
 */

public class PieChartView extends View {
    //饼图白色轮廓画笔
    private Paint mOuterLinePaint;
    //饼状图画笔
    private Paint mPiePaint;
    //内圆画笔
    private Paint mInnerPaint;
    //饼状图外圆半径
    private float mRadius = dip2px(60) + OUTER_LINE_WIDTH;
    //构成饼状图的数据集合
    private List<PieData> mPieDataList;
    //绘制弧形的sweep数组
    private float[] mPieSweep;
    //饼状图动画效果
    private PieChartAnimation mAnimation;
    //初始画弧所在的角度
    private static final int START_DEGREE = -90;

    private static final int PIE_ANIMATION_VALUE = 100;
    //外圆边框的宽度
    private static int OUTER_LINE_WIDTH = 3;
    //动画时间
    private static final int ANIMATION_DURATION = 800;

    private RectF mRectF = new RectF();
    //圆周率
    private static final float PI = 3.1415f;

    private static final int PART_ONE = 1;

    private static final int PART_TWO = 2;

    private static final int PART_THREE = 3;

    private static final int PART_FOUR = 4;

    private boolean isStartDrawBitmap;
    private Rect bitmapDst;
    private Bitmap catClawBitmap;

    public void setOnSpecialTypeClickListener(OnSpecialTypeClickListener listener) {
        this.mListener = listener;
    }

    private OnSpecialTypeClickListener mListener;

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化画笔和效果动画
    private void init() {
        //读取猫爪原图的1/2大小bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        catClawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_claw, options);

        mOuterLinePaint = new Paint();
        mOuterLinePaint.setAntiAlias(true);
        mOuterLinePaint.setStyle(Paint.Style.STROKE);
        mOuterLinePaint.setStrokeWidth(OUTER_LINE_WIDTH);
        mOuterLinePaint.setColor(Color.WHITE);

        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);
        mPiePaint.setStyle(Paint.Style.FILL);
        //设置动画
        mAnimation = new PieChartAnimation();
        mAnimation.setDuration(ANIMATION_DURATION);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(Color.WHITE);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setAntiAlias(true);
        initRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStartDrawBitmap) {
            Rect bitmapSrc = new Rect(0, 0, catClawBitmap.getWidth(), catClawBitmap.getHeight());
            canvas.drawBitmap(catClawBitmap, bitmapSrc, bitmapDst, null);
            isStartDrawBitmap = false;
            return;
        }
        if (mPieDataList != null && !mPieDataList.isEmpty()) {
            //起始是从-90°位置开始画
            float pieStart = START_DEGREE;
            if (mPieSweep == null) {
                mPieSweep = new float[mPieDataList.size()];
            }
            for (int i = 0; i < mPieDataList.size(); i++) {
                //设置弧形颜色
                mPiePaint.setColor(getResources().getColor(mPieDataList.get(i).getColorId()));
                //绘制弧形区域，以构成饼状图
                float pieSweep = mPieDataList.get(i).getValue() * 360;
//                canvas.drawArc(mRectF, pieStart, mPieSweep[i], true, mPiePaint);
//                canvas.drawArc(mRectF, pieStart, mPieSweep[i], true, mOuterLinePaint);
                //获取下一个弧形的起点
                pieStart += pieSweep;
            }
        } else {
            //无数据时，显示灰色圆环
            mPiePaint.setColor(Color.parseColor("#dadada"));//灰色
            canvas.drawCircle(mRadius, mRadius, mRadius, mPiePaint);
        }
//        drawInnerCircle(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int length = (int) (2 * mRadius);
        setMeasuredDimension(length, length);
    }

    /**
     * 设置需要绘制的数据集合
     */

    public void setPieDataList(List<PieData> pieDataList) {
        this.mPieDataList = pieDataList;
        if (mPieSweep == null) {
            mPieSweep = new float[mPieDataList.size()];
        }
        startAnimation(mAnimation);
    }

    /**
     * 设置外圆半径
     *
     * @param radius 外圆半径 dp为单位
     **/
    public void setOuterRadius(float radius) {
        this.mRadius = dip2px(radius) + OUTER_LINE_WIDTH;
        initRectF();
    }

    /**
     * 初始化绘制弧形所在矩形的四点坐标
     **/
    private void initRectF() {
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = 2 * mRadius;
        mRectF.bottom = 2 * mRadius;
    }

    private class PieChartAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mPieSweep = new float[mPieDataList.size()];
            if (interpolatedTime < 1.0f) {
                for (int i = 0; i < mPieDataList.size(); i++) {
                    mPieSweep[i] = (mPieDataList.get(i).getValue() * PIE_ANIMATION_VALUE) * interpolatedTime / PIE_ANIMATION_VALUE * 360;
                }
            } else {
                for (int i = 0; i < mPieDataList.size(); i++) {
                    mPieSweep[i] = mPieDataList.get(i).getValue() * 360;
                }
            }
            invalidate();
        }
    }

    protected void drawInnerCircle(Canvas canvas) {
        canvas.drawCircle(mRadius, mRadius, (float) (mRadius * 0.72), mInnerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doOnSpecialTypeClick(event);
                break;
        }
        return super.onTouchEvent(event);
    }


    private void doOnSpecialTypeClick(MotionEvent event) {
        if (mPieDataList == null || mPieDataList.isEmpty()) return;
        float eventX = event.getX();
        float eventY = event.getY();
        double alfa = 0;
        float startArc = 0;
        //点击的位置到圆心距离的平方
        double distance = Math.pow(eventX - mRadius, 2) + Math.pow(eventY - mRadius, 2);
        //判断点击的坐标是否在环内
        if (distance < Math.pow(mRadius, 2) && distance > Math.pow(0.72 * mRadius, 2)) {
            int which = touchOnWhichPart(event);
            switch (which) {
                case PART_ONE:
                    alfa = Math.atan2(eventX - mRadius, mRadius - eventY) * 180 / PI;
                    break;
                case PART_TWO:
                    alfa = Math.atan2(eventY - mRadius, eventX - mRadius) * 180 / PI + 90;
                    break;
                case PART_THREE:
                    alfa = Math.atan2(mRadius - eventX, eventY - mRadius) * 180 / PI + 180;
                    break;
                case PART_FOUR:
                    alfa = Math.atan2(mRadius - eventY, mRadius - eventX) * 180 / PI + 270;
                    break;
            }

            isStartDrawBitmap = true;
            float x = event.getX();
            float y = event.getY();
            int offX = catClawBitmap.getWidth() / 2;
            int offY = catClawBitmap.getHeight() / 2;
            bitmapDst = new Rect((int) (x - offX), (int) (y - offY), (int) (x + offX), (int) (y + offY));
            invalidate();
            for (PieData data : mPieDataList) {
                startArc = startArc + data.getValue() * 360;
                if (alfa != 0 && alfa < startArc) {
                    if (mListener != null) mListener.onSpecialTypeClick(data.getType(), alfa);
                    break;
                }
            }
        }
    }

    /**
     * 4 |  1
     * -----|-----
     * 3 |  2
     * 圆被分成四等份，判断点击在园的哪一部分
     */
    private int touchOnWhichPart(MotionEvent event) {
        if (event.getX() > mRadius) {
            if (event.getY() > mRadius) return PART_TWO;
            else return PART_ONE;
        } else {
            if (event.getY() > mRadius) return PART_THREE;
            else return PART_FOUR;
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnSpecialTypeClickListener {
        void onSpecialTypeClick(String type, double angle);
    }

    public static class PieData {

        private String type;

        private float value;

        private int colorId;

        public PieData(String type, float value, int colorId) {
            this.type = type;
            this.value = value;
            this.colorId = colorId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public int getColorId() {
            return colorId;
        }

        public void setColorId(int colorId) {
            this.colorId = colorId;
        }
    }
}