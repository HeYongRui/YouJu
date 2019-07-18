package com.heyongrui.base.widget.catloadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.heyongrui.base.R;

/**
 * lambert
 * 2019/7/17 17:43
 * 猫爪
 */
public class CatClaw extends View {

    private boolean isStartDrawBitmap;
    private Rect bitmapDst;
    private Bitmap mCatClawBitmap;
    private CatClawListener mCatClawListener;

    public CatClaw(Context context) {
        super(context);
        init();
    }

    public CatClaw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CatClaw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //读取猫爪原图的1/2大小bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        mCatClawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_claw, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStartDrawBitmap) {
            Rect bitmapSrc = new Rect(0, 0, mCatClawBitmap.getWidth(), mCatClawBitmap.getHeight());
            canvas.drawBitmap(mCatClawBitmap, bitmapSrc, bitmapDst, null);
            isStartDrawBitmap = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isStartDrawBitmap = true;
                float x = event.getX();
                float y = event.getY();
                int offX = mCatClawBitmap.getWidth() / 2;
                int offY = mCatClawBitmap.getHeight() / 2;
                bitmapDst = new Rect((int) (x - offX), (int) (y - offY), (int) (x + offX), (int) (y + offY));
                if (mCatClawListener != null) {
                    mCatClawListener.onClaw((int) event.getRawX(), (int) event.getRawY(), bitmapDst);
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setCatClawListener(CatClawListener catClawListener) {
        this.mCatClawListener = catClawListener;
    }

    public interface CatClawListener {//猫爪按下监听器

        void onClaw(int rawX, int rawY, Rect catClawRect);
    }
}
