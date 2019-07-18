package com.heyongrui.base.widget.catloadingview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.heyongrui.base.R;


/**
 * Created by lambert on 2018/10/15.
 */

public class CatLoadingDialog extends Dialog {

    private View mouse, smileCat, eyeLeft, eyeRight;
    private EyelidView eyelidLeft, eyelidRight;
    private CatClaw catClaw;
    private GraduallyTextView mGraduallyTextView;
    private AnimatorSet mAnimatorSet;
    private int mCatchTime;
    private float mRotateAngle;
    //老鼠旋转圆参数
    private int mMouseRouteRadius;
    private int mMouseCenterX;
    private int mMouseCenterY;

    public CatLoadingDialog(@NonNull Context context) {
        this(context, R.style.BaseDialogTheme);
    }

    public CatLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_catloading);
        getWindow().setGravity(Gravity.CENTER);
        //初始化控件
        View view = getWindow().getDecorView();
        mouse = view.findViewById(R.id.mouse);
        smileCat = view.findViewById(R.id.smile_cat);
        eyeLeft = view.findViewById(R.id.eye_left);
        eyeRight = view.findViewById(R.id.eye_right);
        eyelidLeft = view.findViewById(R.id.eyelid_left);
        eyelidLeft.setColor(Color.parseColor("#d0ced1"));
        eyelidRight = view.findViewById(R.id.eyelid_right);
        eyelidRight.setColor(Color.parseColor("#d0ced1"));
        mGraduallyTextView = view.findViewById(R.id.graduallyTextView);
        catClaw = view.findViewById(R.id.cat_claw);
        catClaw.setCatClawListener((rawX, rawY, catClawRect) -> {
            //根据猫爪点击的位置坐标和老鼠旋转角度计算出是否在点击区域
            float angle = (mRotateAngle + 90);
            int mouseX = (int) (mMouseCenterX + mMouseRouteRadius * Math.cos(angle * 3.14 / 180));
            int mouseY = (int) (mMouseCenterY + mMouseRouteRadius * Math.sin(angle * 3.14 / 180));
            if (catClawRect.contains(mouseX, mouseY)) {
                stopRotateAnim();
                smileCat.setVisibility(View.VISIBLE);
                mGraduallyTextView.stopLoading();
                mGraduallyTextView.setText("C A U G H T ");
                mCatchTime++;
                new Handler().postDelayed(() -> {
                    smileCat.setVisibility(View.GONE);
                    startRotateAnim();
                    mGraduallyTextView.setText("C A T C H I N G ... ");
                    mGraduallyTextView.startLoading();
                }, 2000);
            }
        });
        mouse.post(() -> {
            mMouseCenterX = mouse.getLeft() + mouse.getWidth() / 2 - dp2px(13);
            mMouseCenterY = mouse.getTop() + mouse.getHeight() / 2 - dp2px(8);
            mMouseRouteRadius = mouse.getHeight() / 2 - dp2px(7);
        });
    }

    private void startRotateAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) return;
        float rotation = mouse.getRotation();
        rotation = rotation > 720 ? rotation % 720 : rotation;
        ObjectAnimator mouseRotationAnim = ObjectAnimator.ofFloat(mouse, "rotation", 360f + rotation, rotation);
        long duration = 2000 - mCatchTime * 400;//根据抓住老鼠的次数动态设置旋转时间，提升游戏难度
        duration = duration < 400 ? 400 : duration;//控制最快时间为400毫秒
        mouseRotationAnim.setDuration(duration);//设置动画时间
        mouseRotationAnim.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        mouseRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mouseRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        ObjectAnimator leftEyeRotationAnim = mouseRotationAnim.clone();
        leftEyeRotationAnim.setTarget(eyeLeft);
        ObjectAnimator rightEyeRotationAnim = mouseRotationAnim.clone();
        rightEyeRotationAnim.setTarget(eyeRight);
        mAnimatorSet = new AnimatorSet();
        mouseRotationAnim.addUpdateListener(valueAnimator -> {
            mRotateAngle = (float) valueAnimator.getAnimatedValue();
            updateEyelidHeight(mRotateAngle);
        });
        mAnimatorSet.playTogether(mouseRotationAnim, leftEyeRotationAnim, rightEyeRotationAnim);
        mAnimatorSet.start();
    }

    private void stopRotateAnim() {
        if (mAnimatorSet == null || !mAnimatorSet.isRunning()) return;
        mAnimatorSet.cancel();
    }

    private void updateEyelidHeight(float rotateAngle) {
        //根据猫眼的旋转角度，动态设置猫眼眼睑高度(解决不同机型可能出现的bug)
        rotateAngle = rotateAngle > 360 ? rotateAngle % 360 : rotateAngle;
        if (60 <= rotateAngle && rotateAngle < 180) {
            eyelidLeft.setEyelidHeightPercent((180 - rotateAngle) / 120f);
            eyelidRight.setEyelidHeightPercent((180 - rotateAngle) / 120f);
            return;
        }
        if (180 <= rotateAngle && rotateAngle <= 300) {
            eyelidLeft.setEyelidHeightPercent(1 - (300 - rotateAngle) / 120f);
            eyelidRight.setEyelidHeightPercent(1 - (300 - rotateAngle) / 120f);
            return;
        }
        eyelidLeft.setEyelidHeightPercent(1);
        eyelidRight.setEyelidHeightPercent(1);
    }

    public void setGraduallyText(@NonNull String gradually_text) {
        //设置更新渐显文本
        if (!TextUtils.isEmpty(gradually_text)) {
            mGraduallyTextView.setText(gradually_text);
        }
    }

    @Override
    public void show() {
        super.show();
        startRotateAnim();
        mGraduallyTextView.startLoading();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        stopRotateAnim();
        mGraduallyTextView.stopLoading();
    }

    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
