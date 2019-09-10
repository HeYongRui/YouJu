package com.heyongrui.base.widget.layoutmanager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ScrollHelper {
    /* package */
    static void smoothScrollToPosition(RecyclerView recyclerView, ViewPagerLayoutManager viewPagerLayoutManager, int targetPosition) {
        final int delta = viewPagerLayoutManager.getOffsetToPosition(targetPosition);
        if (viewPagerLayoutManager.getOrientation() == ViewPagerLayoutManager.VERTICAL) {
            recyclerView.smoothScrollBy(0, delta);
        } else {
            recyclerView.smoothScrollBy(delta, 0);
        }
    }

    public static void smoothScrollToTargetView(RecyclerView recyclerView, View targetView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof ViewPagerLayoutManager)) return;
        final int targetPosition = ((ViewPagerLayoutManager) layoutManager).getLayoutPositionOfView(targetView);
        smoothScrollToPosition(recyclerView, (ViewPagerLayoutManager) layoutManager, targetPosition);
    }
}
