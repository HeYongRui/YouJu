package com.heyongrui.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Created by lambert on 2018/8/30.
 * Drawable工具类，避免过多的drawable.xml资源创建
 */
public class DrawableUtil {
    /**
     * 对目标Drawable 进行着色
     */
    public static Drawable tintDrawable(Context context, @DrawableRes int drawableResId, int color) {
        try {
            Drawable drawable = ContextCompat.getDrawable(context, drawableResId).mutate();
            Drawable wrappedDrawable = getCanTintDrawable(drawable);
            ColorStateList colors = ColorStateList.valueOf(color);
            DrawableCompat.setTintList(wrappedDrawable, colors);
            return wrappedDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对目标Drawable 进行着色
     */
    public static Drawable tintDrawable(Context context, @DrawableRes int drawableResId, ColorStateList colors) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId).mutate();
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 对 drawable 进行着色
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 对目标Drawable 进行着色
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
        try {
            Drawable wrappedDrawable = getCanTintDrawable(drawable);
            ColorStateList colors = ColorStateList.valueOf(color);
            DrawableCompat.setTintList(wrappedDrawable, colors);
            return wrappedDrawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对目标Drawable 进行着色
     */
    public static Drawable tintDrawable(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 对 drawable 进行着色
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 获取可以进行tint 的Drawable
     */
    private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
        // 获取此drawable的共享状态实例
        Drawable.ConstantState state = drawable.getConstantState();
        // 对drawable 进行重新实例化、包装、可变操作
        return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
    }

    public static class DrawableBuilder {
        //公共参数
        private Context mContext;
        private int mColor;
        //圆角渐变等参数
        private int mGradientLTRoundRadius;
        private int mGradientRTRoundRadius;
        private int mGradientRBRoundRadius;
        private int mGradientLBRoundRadius;
        private int mGradientRoundRadius;//有值默认四个圆角一样
        private int mGradientShapeType = GradientDrawable.RECTANGLE;//形状类型
        private int[] mGradientColors;//渐变色
        private ColorStateList mGradientSolidColors;//填充色(与渐变色只能取一个)
        private ColorStateList mGradientStrokeColor;//描边线颜色
        private GradientDrawable.Orientation mGradientOrientation = GradientDrawable.Orientation.TL_BR;//默认渐变方向
        private int mGradientType;
        private int mGradientStrokeWidth;
        private float mGradientDashWidth;
        private float mGradientDashGap;
        //圆环参数
        private Shader mRingShader;
        private int mRingLTOutRadius;
        private int mRingRTOutRadius;
        private int mRingRBOutRadius;
        private int mRingLBOutRadius;
        private int mRingOutRadius;//有值默认四个外圆角一样
        private int mRingLTInnerRadius;
        private int mRingRTInnerRadius;
        private int mRingRBInnerRadius;
        private int mRingLBInnerRadius;
        private int mRingInnerRadius;//有值默认四个内圆角一样
        private int mRingLTSpaceOutAndInner;
        private int mRingRTSpaceOutAndInner;
        private int mRingRBSpaceOutAndInner;
        private int mRingLBSpaceOutAndInner;
        private int mRingSpaceOutAndInner;//有值默认四个内外圆环间距一样
        //选择器
        @DrawableRes
        private int mStateListNormalRes;
        @DrawableRes
        private int mStateListPressedRes;
        @DrawableRes
        private int mStateListFocusedRes;
        @DrawableRes
        private int mStateListUnableRes;
        private Drawable mStateListNormalDrawable;//StateList
        private Drawable mStateListPressedDrawable;//Selector
        private Drawable mStateListFocusedDrawable;
        private Drawable mStateListUnableDrawable;
        //水波纹(5.0以上)
        private int mRippleNormalColor;
        private int mRippleColor;
        private Drawable mRippleNormalDrawable;
        private Drawable mRippleMaskDrawable;

        public DrawableBuilder(Context context) {
            mContext = context;
        }

        public DrawableBuilder setColor(int mColor) {
            this.mColor = mColor;
            return this;
        }

        public DrawableBuilder setGradientLTRoundRadius(int mGradientLTRoundRadius) {
            this.mGradientLTRoundRadius = mGradientLTRoundRadius;
            return this;
        }

        public DrawableBuilder setGradientRTRoundRadius(int mGradientRTRoundRadius) {
            this.mGradientRTRoundRadius = mGradientRTRoundRadius;
            return this;
        }

        public DrawableBuilder setGradientRBRoundRadius(int mGradientRBRoundRadius) {
            this.mGradientRBRoundRadius = mGradientRBRoundRadius;
            return this;
        }

        public DrawableBuilder setGradientLBRoundRadius(int mGradientLBRoundRadius) {
            this.mGradientLBRoundRadius = mGradientLBRoundRadius;
            return this;
        }

        public DrawableBuilder setGradientRoundRadius(int mGradientRoundRadius) {
            this.mGradientRoundRadius = mGradientRoundRadius;
            return this;
        }

        public DrawableBuilder setGradientShapeType(int mGradientShapeType) {
            this.mGradientShapeType = mGradientShapeType;
            return this;
        }

        public DrawableBuilder setGradientColors(int[] mGradientColors) {
            this.mGradientColors = mGradientColors;
            return this;
        }

        public DrawableBuilder setGradientSolidColors(ColorStateList mGradientSolidColors) {
            this.mGradientSolidColors = mGradientSolidColors;
            return this;
        }

        public DrawableBuilder setGradientStrokeColor(ColorStateList mGradientStrokeColor) {
            this.mGradientStrokeColor = mGradientStrokeColor;
            return this;
        }

        public DrawableBuilder setGradientOrientation(GradientDrawable.Orientation mGradientOrientation) {
            this.mGradientOrientation = mGradientOrientation;
            return this;
        }

        public DrawableBuilder setGradientType(int mGradientType) {
            this.mGradientType = mGradientType;
            return this;
        }

        public DrawableBuilder setGradientStrokeWidth(int mGradientStrokeWidth) {
            this.mGradientStrokeWidth = mGradientStrokeWidth;
            return this;
        }

        public DrawableBuilder setGradientDashWidth(float mGradientDashWidth) {
            this.mGradientDashWidth = mGradientDashWidth;
            return this;
        }

        public DrawableBuilder setGradientDashGap(float mGradientDashGap) {
            this.mGradientDashGap = mGradientDashGap;
            return this;
        }

        public DrawableBuilder setRingShader(Shader mRingShader) {
            this.mRingShader = mRingShader;
            return this;
        }

        public DrawableBuilder setRingLTOutRadius(int mRingLTOutRadius) {
            this.mRingLTOutRadius = mRingLTOutRadius;
            return this;
        }

        public DrawableBuilder setRingRTOutRadius(int mRingRTOutRadius) {
            this.mRingRTOutRadius = mRingRTOutRadius;
            return this;
        }

        public DrawableBuilder setRingRBOutRadius(int mRingRBOutRadius) {
            this.mRingRBOutRadius = mRingRBOutRadius;
            return this;
        }

        public DrawableBuilder setRingLBOutRadius(int mRingLBOutRadius) {
            this.mRingLBOutRadius = mRingLBOutRadius;
            return this;
        }

        public DrawableBuilder setRingOutRadius(int mRingOutRadius) {
            this.mRingOutRadius = mRingOutRadius;
            return this;
        }

        public DrawableBuilder setRingLTInnerRadius(int mRingLTInnerRadius) {
            this.mRingLTInnerRadius = mRingLTInnerRadius;
            return this;
        }

        public DrawableBuilder setRingRTInnerRadius(int mRingRTInnerRadius) {
            this.mRingRTInnerRadius = mRingRTInnerRadius;
            return this;
        }

        public DrawableBuilder setRingRBInnerRadius(int mRingRBInnerRadius) {
            this.mRingRBInnerRadius = mRingRBInnerRadius;
            return this;
        }

        public DrawableBuilder setRingLBInnerRadius(int mRingLBInnerRadius) {
            this.mRingLBInnerRadius = mRingLBInnerRadius;
            return this;
        }

        public DrawableBuilder setRingInnerRadius(int mRingInnerRadius) {
            this.mRingInnerRadius = mRingInnerRadius;
            return this;
        }

        public DrawableBuilder setRingLTSpaceOutAndInner(int mRingLTSpaceOutAndInner) {
            this.mRingLTSpaceOutAndInner = mRingLTSpaceOutAndInner;
            return this;
        }

        public DrawableBuilder setRingRTSpaceOutAndInner(int mRingRTSpaceOutAndInner) {
            this.mRingRTSpaceOutAndInner = mRingRTSpaceOutAndInner;
            return this;
        }

        public DrawableBuilder setRingRBSpaceOutAndInner(int mRingRBSpaceOutAndInner) {
            this.mRingRBSpaceOutAndInner = mRingRBSpaceOutAndInner;
            return this;
        }

        public DrawableBuilder setRingLBSpaceOutAndInner(int mRingLBSpaceOutAndInner) {
            this.mRingLBSpaceOutAndInner = mRingLBSpaceOutAndInner;
            return this;
        }

        public DrawableBuilder setRingSpaceOutAndInner(int mRingSpaceOutAndInner) {
            this.mRingSpaceOutAndInner = mRingSpaceOutAndInner;
            return this;
        }

        public DrawableBuilder setStateListNormalRes(int mStateListNormalRes) {
            this.mStateListNormalRes = mStateListNormalRes;
            return this;
        }

        public DrawableBuilder setStateListPressedRes(int mStateListPressedRes) {
            this.mStateListPressedRes = mStateListPressedRes;
            return this;
        }

        public DrawableBuilder setStateListFocusedRes(int mStateListFocusedRes) {
            this.mStateListFocusedRes = mStateListFocusedRes;
            return this;
        }

        public DrawableBuilder setStateListUnableRes(int mStateListUnableRes) {
            this.mStateListUnableRes = mStateListUnableRes;
            return this;
        }

        public DrawableBuilder setStateListNormalDrawable(Drawable mStateListNormalDrawable) {
            this.mStateListNormalDrawable = mStateListNormalDrawable;
            return this;
        }

        public DrawableBuilder setStateListPressedDrawable(Drawable mStateListPressedDrawable) {
            this.mStateListPressedDrawable = mStateListPressedDrawable;
            return this;
        }

        public DrawableBuilder setStateListFocusedDrawable(Drawable mStateListFocusedDrawable) {
            this.mStateListFocusedDrawable = mStateListFocusedDrawable;
            return this;
        }

        public DrawableBuilder setStateListUnableDrawable(Drawable mStateListUnableDrawable) {
            this.mStateListUnableDrawable = mStateListUnableDrawable;
            return this;
        }

        public DrawableBuilder setRippleNormalColor(int mRippleNormalColor) {
            this.mRippleNormalColor = mRippleNormalColor;
            return this;
        }

        public DrawableBuilder setRippleColor(int mRippleColor) {
            this.mRippleColor = mRippleColor;
            return this;
        }

        public DrawableBuilder setRippleNormalDrawable(Drawable mRippleNormalDrawable) {
            this.mRippleNormalDrawable = mRippleNormalDrawable;
            return this;
        }

        public DrawableBuilder setRippleMaskDrawable(Drawable mRippleMaskDrawable) {
            this.mRippleMaskDrawable = mRippleMaskDrawable;
            return this;
        }

        public GradientDrawable createGradientDrawable() {
            GradientDrawable gradientDrawable = null;
            if (mGradientColors != null && mGradientColors.length > 0) {//渐变色
                gradientDrawable = new GradientDrawable(mGradientOrientation, mGradientColors);
            } else {//填充色
                gradientDrawable = new GradientDrawable();
                if (mGradientSolidColors == null && mColor != 0) {
                    mGradientSolidColors = ColorStateList.valueOf(mColor);
                }
                if (mGradientSolidColors != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        gradientDrawable.setColor(mGradientSolidColors);
                    } else {
                        gradientDrawable.setColor(mGradientSolidColors.getDefaultColor());
                    }
                }
            }
            //描边线
            if (mGradientStrokeColor == null && mColor != 0) {
                mGradientStrokeColor = ColorStateList.valueOf(mColor);
            }
            if (mGradientStrokeColor != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gradientDrawable.setStroke(mGradientStrokeWidth, mGradientStrokeColor, mGradientDashWidth, mGradientDashGap);
                } else {
                    gradientDrawable.setStroke(mGradientStrokeWidth, mGradientStrokeColor.getDefaultColor(), mGradientDashWidth, mGradientDashGap);
                }
            }
            //形状GradientDrawable.RECTANGLE
            gradientDrawable.setShape(mGradientShapeType);
            //形状不为圆形的圆角处理
            if (mGradientShapeType != GradientDrawable.OVAL) {
                // 如果设定的有Corner Radius就认为是4个角一样的Button， 否则就是4个不一样的角 Button
                if (mGradientRoundRadius != 0) {
                    gradientDrawable.setCornerRadius(mGradientRoundRadius);
                } else {
                    //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                    gradientDrawable.setCornerRadii(new float[]{mGradientLTRoundRadius,
                            mGradientLTRoundRadius, mGradientRTRoundRadius, mGradientRTRoundRadius,
                            mGradientRBRoundRadius, mGradientRBRoundRadius, mGradientLBRoundRadius,
                            mGradientLBRoundRadius});
                }
            }
            //渐变梯度模式GradientDrawable.LINEAR_GRADIENT
            gradientDrawable.setGradientType(mGradientType);
            return gradientDrawable;
        }

        public ShapeDrawable createRingDrawable() {
            //外圆半径
            if (mRingOutRadius != 0) {
                mRingLTOutRadius = mRingOutRadius;
                mRingRTOutRadius = mRingOutRadius;
                mRingRBOutRadius = mRingOutRadius;
                mRingLBOutRadius = mRingOutRadius;
            }
            float[] outerRadii = {mRingLTOutRadius, mRingLTOutRadius, mRingRTOutRadius, mRingRTOutRadius,
                    mRingRBOutRadius, mRingRBOutRadius, mRingLBOutRadius, mRingLBOutRadius};//左上x2,右上x2,右下x2,左下x2
            //内圆半径
            if (mRingInnerRadius != 0) {
                mRingLTInnerRadius = mRingInnerRadius;
                mRingRTInnerRadius = mRingInnerRadius;
                mRingRBInnerRadius = mRingInnerRadius;
                mRingLBInnerRadius = mRingInnerRadius;
            }
            float[] innerRadii = {mRingLTInnerRadius, mRingLTInnerRadius, mRingRTInnerRadius, mRingRTInnerRadius,
                    mRingRBInnerRadius, mRingRBInnerRadius, mRingLBInnerRadius, mRingLBInnerRadius};//内矩形 圆角半径
            //内外圆间距
            if (mRingSpaceOutAndInner != 0) {
                mRingLTSpaceOutAndInner = mRingSpaceOutAndInner;
                mRingRTSpaceOutAndInner = mRingSpaceOutAndInner;
                mRingRBSpaceOutAndInner = mRingSpaceOutAndInner;
                mRingLBSpaceOutAndInner = mRingSpaceOutAndInner;
            }
            RectF inset = new RectF(mRingLTSpaceOutAndInner, mRingRTSpaceOutAndInner,
                    mRingRBSpaceOutAndInner, mRingLBSpaceOutAndInner);

            RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);
            ShapeDrawable circularRingDrawable = new ShapeDrawable(roundRectShape);
            if (mColor != 0) {
                circularRingDrawable.getPaint().setColor(mColor);
            }
            if (mRingShader != null) {
                circularRingDrawable.getPaint().setShader(mRingShader);
            }
            circularRingDrawable.getPaint().setAntiAlias(true);
            circularRingDrawable.getPaint().setStyle(Paint.Style.FILL);
            return circularRingDrawable;
        }

        public Drawable createRippleDrawable() {
            if (mRippleColor == 0) {
                throw new IllegalArgumentException("RippleDrawable requires a non-null color");
            }
            Drawable drawable;
            if (mRippleNormalDrawable == null && mContext != null && mRippleNormalColor != 0) {
                mRippleNormalDrawable = new ColorDrawable(mRippleNormalColor);
            }
            if (mRippleMaskDrawable == null && mContext != null && mRippleColor != 0) {
                mRippleMaskDrawable = new ColorDrawable(mRippleColor);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mRippleColor != 0) {
                ColorStateList stateList = ColorStateList.valueOf(mRippleColor);
                RippleDrawable rippleDrawable = new RippleDrawable(stateList, mRippleNormalDrawable, mRippleMaskDrawable);
                drawable = rippleDrawable;
            } else {
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, mRippleMaskDrawable);
                stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, mRippleNormalDrawable);
                drawable = stateListDrawable;
            }
            return drawable;
        }

        public StateListDrawable createStateListDrawable() {
            StateListDrawable stateListDrawable = new StateListDrawable();
            if (mStateListNormalDrawable == null && mContext != null && mStateListNormalRes != 0) {
                mStateListNormalDrawable = ContextCompat.getDrawable(mContext, mStateListNormalRes);
            }
            if (mStateListPressedDrawable == null && mContext != null && mStateListPressedRes != 0) {
                mStateListPressedDrawable = ContextCompat.getDrawable(mContext, mStateListPressedRes);
            }
            if (mStateListFocusedDrawable == null && mContext != null && mStateListFocusedRes != 0) {
                mStateListFocusedDrawable = ContextCompat.getDrawable(mContext, mStateListFocusedRes);
            }
            if (mStateListUnableDrawable == null && mContext != null && mStateListUnableRes != 0) {
                mStateListUnableDrawable = ContextCompat.getDrawable(mContext, mStateListUnableRes);
            }
            // View.PRESSED_ENABLED_STATE_SET
            if (mStateListPressedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, mStateListPressedDrawable);
            }
            // View.ENABLED_FOCUSED_STATE_SET
            if (mStateListFocusedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, mStateListFocusedDrawable);
            }
            // View.ENABLED_STATE_SET
            if (mStateListNormalDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, mStateListNormalDrawable);
            }
            // View.FOCUSED_STATE_SET
            if (mStateListFocusedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_focused}, mStateListFocusedDrawable);
            }
            // View.WINDOW_FOCUSED_STATE_SET
            if (mStateListUnableDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_window_focused}, mStateListUnableDrawable);
            }
            // View.EMPTY_STATE_SET
            if (mStateListNormalDrawable != null) {
                stateListDrawable.addState(new int[]{}, mStateListNormalDrawable);
            }
            return stateListDrawable;
        }
    }
}
