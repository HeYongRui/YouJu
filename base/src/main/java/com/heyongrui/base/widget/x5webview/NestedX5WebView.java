package com.heyongrui.base.widget.x5webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lambert on 2018/3/14.
 * 嵌套ScrollView的X5WebView
 */

public class NestedX5WebView extends X5WebView {
    public NestedX5WebView(Context context) {
        super(context);
    }

    public NestedX5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedX5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
