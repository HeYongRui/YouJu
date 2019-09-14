package com.heyongrui.main.mob;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {

    //贝塞尔的两个控制点
    private PointF controlF1;
    private PointF controlF2;

    public BezierEvaluator(PointF controlF1, PointF controlF2) {
        this.controlF1 = controlF1;
        this.controlF2 = controlF2;
    }

    public BezierEvaluator(PointF controlF1) {
        this.controlF1 = controlF1;
    }

    @Override
    public PointF evaluate(float time, PointF startValue, PointF endValue) {
        float currentX, currentY;
        if (controlF2 == null) {
            //二阶贝塞尔曲线方程(一个控制点)
            currentX = arithmeticProduct(1 - time, 2) * startValue.x
                    + 2 * time * (1 - time) * controlF1.x
                    + arithmeticProduct(time, 2) * endValue.x;
            currentY = arithmeticProduct(1 - time, 2) * startValue.y
                    + 2 * time * (1 - time) * controlF1.y
                    + arithmeticProduct(time, 2) * endValue.y;
        } else {//三阶贝塞尔曲线方程(两个控制点)
            currentX = arithmeticProduct(1 - time, 3) * (startValue.x)
                    + 3 * arithmeticProduct(1 - time, 2) * time * (controlF1.x)
                    + 3 * (1 - time) * arithmeticProduct(time, 2) * (controlF2.x)
                    + arithmeticProduct(time, 3) * (endValue.x);
            currentY = arithmeticProduct(1 - time, 3) * (startValue.y)
                    + 3 * arithmeticProduct(1 - time, 2) * time * (controlF1.y)
                    + 3 * (1 - time) * arithmeticProduct(time, 2) * (controlF2.y)
                    + arithmeticProduct(time, 3) * (endValue.y);
        }
        PointF currentP = new PointF(currentX, currentY);
        return currentP;
    }

    private float arithmeticProduct(float value, float square) {//返回浮点数的开方值
        double pow = Math.pow(value, square);
        return (float) pow;
    }
}