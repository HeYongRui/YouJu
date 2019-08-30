package com.heyongrui.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

/**
 * 2019/8/28
 * lambert
 * 自定义Spinner，解决重复选择同一位置不回调问题
 */
public class ReSpinner extends AppCompatSpinner {

    private boolean mIsCallbackSamePosition = true;//重复点击同一position，是否触发回调

    public ReSpinner(Context context) {
        super(context);
    }

    public ReSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIsCallbackSamePosition(boolean isCallbackSamePosition) {
        this.mIsCallbackSamePosition = isCallbackSamePosition;
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected && mIsCallbackSamePosition) {//重复点击同一position，手动触发回调，其他position系统会回调
            OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
            if (null != onItemSelectedListener) {
                onItemSelectedListener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
            }
        }
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected && mIsCallbackSamePosition) {//重复点击同一position，手动触发回调，其他position系统会回调
            OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
            if (null != onItemSelectedListener) {
                onItemSelectedListener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
            }
        }
    }

    public void reset() {//重置为空的状态，隐藏选择view，回调position为-1
        OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
        View selectedView = getSelectedView();
        if (null != onItemSelectedListener) {
            onItemSelectedListener.onItemSelected(this, selectedView, -1, -1);
        }
        if (null != selectedView) {
            selectedView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 2019/8/28
     * lambert
     * 自定义OnItemSelectedListener，实现spinner默认是否选中功能
     */
    public static class OnReItemSelectedListener implements OnItemSelectedListener {

        boolean isFirst = true;
        boolean mIsDefaultSelected = true;//是否执行默认选中item第一个逻辑

        public OnReItemSelectedListener() {
        }

        public OnReItemSelectedListener(boolean isDefaultSelected) {
            this.mIsDefaultSelected = isDefaultSelected;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (mIsDefaultSelected) {//初始默认选中第一个
                onReItemSelected(adapterView, view, i, l);
            } else {//初始默认不选中
                if (isFirst) {
                    view.setVisibility(View.INVISIBLE);
                    isFirst = false;
                    onReItemSelected(adapterView, view, -1, -1);
                } else {
                    if (view.getVisibility() != View.VISIBLE) {
                        view.setVisibility(View.VISIBLE);
                    }
                    onReItemSelected(adapterView, view, i, l);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        public void onReItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //使用时用此方法代替onItemSelected即可，用户可在此自定义操作(i=-1代表未选中状态)
        }
    }
}
