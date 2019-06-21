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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lambert on 2018/10/15.
 */

public class CatLoadingView extends Dialog {

    private View mouse, smileCat, eyeLeft, eyeRight;
    private EyelidView eyelidLeft, eyelidRight;
    private GraduallyTextView mGraduallyTextView;
    private AnimatorSet mAnimatorSet;
    private int mCatchTime;
    private float mRotateAngle;

    public CatLoadingView(@NonNull Context context) {
        this(context, R.style.Dialog);
    }

    public CatLoadingView(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_catloading);
        getWindow().setGravity(Gravity.CENTER);
        //初始化控件
        View view = getWindow().getDecorView();
        mouse = view.findViewById(R.id.mouse);
        PieChartView pieChartView = view.findViewById(R.id.pie_chart_view);
        List<PieChartView.PieData> pieDataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int colorRes = i % 2 == 0 ? R.color.black : R.color.colorAccent;
            PieChartView.PieData pieData = new PieChartView.PieData("TYPE" + i, 0.15f, colorRes);
            pieDataList.add(pieData);
        }
        pieChartView.setPieDataList(pieDataList);
        pieChartView.setOnSpecialTypeClickListener((type, angle) -> {
            //老鼠旋转起始角度和手势触控旋转起始角度不同，相差180°，故先对手势点击角度进行处理再进行比较
            double touchAngle = angle > 180 ? (angle - 180) : (angle + 180);
            float rotateAngle = mRotateAngle > 360 ? mRotateAngle % 360 : mRotateAngle;
            double abs = Math.abs((rotateAngle - touchAngle));
            if (abs <= 10) {//如果老鼠头的旋转角度和点击处角度误差不超过10°，就认为砸中了老鼠
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
        smileCat = view.findViewById(R.id.smile_cat);
        eyeLeft = view.findViewById(R.id.eye_left);
        eyeRight = view.findViewById(R.id.eye_right);
        eyelidLeft = view.findViewById(R.id.eyelid_left);
        eyelidLeft.setColor(Color.parseColor("#d0ced1"));
        eyelidRight = view.findViewById(R.id.eyelid_right);
        eyelidRight.setColor(Color.parseColor("#d0ced1"));
        mGraduallyTextView = view.findViewById(R.id.graduallyTextView);
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
}
