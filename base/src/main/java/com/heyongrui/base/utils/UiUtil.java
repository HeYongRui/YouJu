package com.heyongrui.base.utils;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import java.lang.reflect.Field;


public class UiUtil {
    /**
     * 编辑框错误提示
     **/
    public static void setEditError(TextView view, String error) {
        view.setError(error);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     * 设置控件的可视化
     */
    public static void setVisibility(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 设置字体样式
     */
    public static void setFontStyle(TextView textView, String assetsFontPath) {
        if (textView == null || TextUtils.isEmpty(assetsFontPath)) return;
        Typeface typeFace = Typeface.createFromAsset(textView.getContext().getAssets(), assetsFontPath);
        textView.setTypeface(typeFace);
    }

    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断EditText是否可以垂直滑动
     */
    public static boolean canVerticalScroll(EditText et) {
        //判断EditText竖直方向是否可以滚动
        // 滚动的距离
        int scrollY = et.getScrollY();
        // 控件内容的总高度s
        int scrollRange = et.getLayout().getHeight();
        // 控件实际显示的高度
        int scrollExtent = et.getHeight() - et.getCompoundPaddingTop() - et.getCompoundPaddingBottom();
        // 控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    public static int getCurrentColor(float percent, int[] colors) {
        float[][] f = new float[colors.length][3];
        for (int i = 0; i < colors.length; i++) {
            f[i][0] = (colors[i] & 0xff0000) >> 16;
            f[i][1] = (colors[i] & 0x00ff00) >> 8;
            f[i][2] = (colors[i] & 0x0000ff);
        }
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < f.length; j++) {
                if (f.length == 1 || percent == j / (f.length - 1f)) {
                    result = f[j];
                } else {
                    if (percent > j / (f.length - 1f) && percent < (j + 1f) / (f.length - 1)) {
                        result[i] = f[j][i] - (f[j][i] - f[j + 1][i]) * (percent - j / (f.length - 1f)) * (f.length - 1f);
                    }
                }
            }
        }
        return Color.rgb((int) result[0], (int) result[1], (int) result[2]);
    }

    /**
     * 利用字符串资源反射获取本地资源文件
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将整形颜色值转换为十六进制颜色
     * -16776961转为0x0000FF
     */
    public static int convertHexadecimalColor(@ColorInt int color) {
//        Integer.toHexString(color).toUpperCase();//upercase with alpha
//        Integer.toHexString(color).toUpperCase().substring(2);//uppercase without alpha
        String s = Integer.toHexString(color).toUpperCase();
        return Integer.parseInt("0x" + s);
    }

    /**
     * 计算两种颜色的中间某个值的颜色（利用自带API）
     */
    public static int getCurrentColorBySystem(@FloatRange(from = 0.0, to = 1.0) float percent, int startColor, int endColor) {
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类
        return (int) argbEvaluator.evaluate(percent, startColor, endColor);
    }

    /**
     * 计算两种颜色的中间某个值的颜色（通过计算）
     */
    public static int getCurrentColorByCalculate(float percent, int startColor, int endColor) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + percent * redDifference);
        blueCurrent = (int) (blueStart + percent * blueDifference);
        greenCurrent = (int) (greenStart + percent * greenDifference);
        alphaCurrent = (int) (alphaStart + percent * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }

    public static void adaptiveVirtualKeyboard(Activity activity) {//适配虚拟键盘机型
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        boolean navigationBarShow = UiUtil.isNavigationBarExist(activity);
        if (navigationBarShow) {//虚拟键盘展示了就设置边距高度
            int navigationBarHeight = UiUtil.getNavigationBarHeight(activity);
            rootView.setPadding(0, 0, 0, navigationBarHeight);
        }
    }

    public static void hideBottomVirtualKeyboard(Activity activity) {
        boolean isHasNavigationBar = UiUtil.isNavigationBarExist(activity);
        if (!isHasNavigationBar) return;
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        final String NAVIGATION = "navigationBarBackground";
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != View.NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取NavigationBar的高度
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null) return 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * 创建文本多个状态时颜色列表
     */
    public static ColorStateList createTextColorStateList(int selectedColor, int pressedColor, int normalColor) {
        int[] colors = new int[]{selectedColor, pressedColor, normalColor};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 创建多个状态时颜色列表
     */
    public static ColorStateList createColorStateList(Context context, int normalColor, int pressedColor, int selectedColor) {
        int[] colors = new int[]{normalColor, pressedColor, selectedColor};
        int[][] states = new int[3][];
        states[0] = new int[]{};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_selected};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 创建正常和禁用状态颜色列表
     */
    public static ColorStateList createColorStateList(int normalColor, int unableColor) {
        ColorStateList colorStateList = createColorStateList(normalColor, normalColor, normalColor, unableColor);
        return colorStateList;
    }

    /**
     * 创建多个状态时颜色列表
     */
    public static ColorStateList createColorStateList(int normalColor, int pressedColor, int focusedColor, int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 为单个view设置点击效果，高版本带涟漪反馈
     *
     * @param context     上下文
     * @param normalColor 未点击的颜色
     * @param pressColor  按下的颜色
     * @param view        目标view
     */
    public static void setOnclickFeedBack(Context context, int normalColor, int pressColor, View view) {
        Drawable bgDrawble;
        ColorDrawable drawablePressed = new ColorDrawable(pressColor);//分别解析两种颜色为colordrawble
        ColorDrawable drawableNormal = new ColorDrawable(normalColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//高版本设置RippleDrawable 而低版本设置 StateListDrawable也就是selector
            ColorStateList stateList = ColorStateList.valueOf(pressColor);
            bgDrawble = new RippleDrawable(stateList, drawableNormal, drawablePressed);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawableNormal);
            bgDrawble = stateListDrawable;
        }
        ViewCompat.setBackground(view, bgDrawble);
    }

    /**
     * 支持同时设置多个view
     *
     * @param context     上下文
     * @param normalColor 正常颜色
     * @param pressColor  按下颜色
     * @param views       目标view群
     */
    public static void setOnclickFeedBack(Context context, @ColorInt int normalColor, @ColorInt int pressColor, View... views) {
        for (View view : views) {
            setOnclickFeedBack(context, normalColor, pressColor, view);
        }
    }

    private static long lastClickTime;//标识最后一次点击的时间

    /**
     * 是否可以执行点击事件(防止按钮多次点击导致事件重复的方法判断)
     */
    public static boolean isPerformClickEvent() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= 1000) {
            //两次点击按钮之间的点击间隔不能少于1000毫秒
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
