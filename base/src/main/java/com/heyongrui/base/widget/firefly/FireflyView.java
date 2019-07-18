package com.heyongrui.base.widget.firefly;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.heyongrui.base.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Mr.He on 2019/3/3.
 */

public class FireflyView extends SurfaceView implements SurfaceHolder.Callback {
    // 粒子的最大数量
    private static final int MAX_NUM = 400;
    // 粒子集合
    private List<FloatParticle> mListParticles;
    // 随机数
    private Random mRandom;

    private SurfaceHolder mHolder;

    // 粒子半径
    private int mParticleMaxRadius;

    // 粒子数量
    private int mParticleNum;

    // 粒子移动速率
    private int mParticleMoveRate;

    private boolean mIsPlaying = false;

    public FireflyView(Context context) {
        this(context, null);
    }

    public FireflyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireflyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FireflyView);
        mParticleMaxRadius = ta.getInt(R.styleable.FireflyView_firefly_max_radius, 5);
        mParticleNum = ta.getInt(R.styleable.FireflyView_firefly_num, MAX_NUM);
        mParticleMoveRate = ta.getInt(R.styleable.FireflyView_firefly_move_rate, 5);
        ta.recycle();
    }

    private void init() {
        // 设置透明
        setZOrderOnTop(true);
        // 配合清屏 canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
        // 初始化随机数
        mRandom = new Random();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    // 初始化浮点粒子数据
    private void initParticlesData(int width, int height) {
        mListParticles = new ArrayList<>();
        for (int i = 0; i < mParticleNum; i++) {
            FloatParticle fp = new FloatParticle(width, height);
            mParticleMaxRadius = mParticleMaxRadius < 2 ? 2 : mParticleMaxRadius;
            fp.setRadius(mRandom.nextInt(mParticleMaxRadius - 1) + 1);
            mListParticles.add(fp);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mListParticles == null || mListParticles.isEmpty()) {
            initParticlesData(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void stopAnimation() {
        mIsPlaying = false;
    }

    public void startAnimation() {
        mIsPlaying = true;
        new Thread(() -> {
            while (mIsPlaying) {
                Canvas canvas = null;
                try {
                    canvas = mHolder.lockCanvas(null);
                    if (canvas != null) {
                        synchronized (mHolder) {
                            // 清屏
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            if (mListParticles != null) {
                                for (FloatParticle floatParticle : mListParticles) {
                                    if (mIsPlaying) {
                                        floatParticle.drawParticle(canvas);
                                    } else {
                                        break;
                                    }
                                }
                            }
                            // 控制帧数
                            Thread.sleep(25);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }).start();

    }

    public int getParticleMoveRate() {
        return mParticleMoveRate;
    }

    public void setParticleMoveRate(int particleMoveRate) {
        mParticleMoveRate = particleMoveRate;
    }

    public int getParticleMaxRadius() {
        return mParticleMaxRadius;
    }

    public int getParticleNum() {
        return mParticleNum;
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }
}
