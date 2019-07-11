package com.heyongrui.base.widget.imagewatcher;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heyongrui.base.R;

import java.util.List;


/**
 * Created by lambert on 2018/11/19.
 */

public class ImageWatcherHelper<T> {
    private static final int VIEW_IMAGE_WATCHER_ID = R.id.view_image_watcher;
    private final Activity holder;
    private final ViewGroup activityDecorView;
    private ImageWatcher<T> mImageWatcher;
    private ImageWatcher.Loader<T> loader;
    private Integer statusBarHeight;
    private Integer resErrorImage;
    private ImageWatcher.OnPictureLongPressListener<T> listener;
    private ImageWatcher.IndexProvider<T> indexProvider;
    private ImageWatcher.LoadingUIProvider loadingUIProvider;
    private ImageWatcher.OnStateChangedListener<T> onStateChangedListener;

    private ImageWatcherHelper(Activity activity) {
        holder = activity;
        activityDecorView = (ViewGroup) activity.getWindow().getDecorView();
    }

    public static <T> ImageWatcherHelper<T> with(Activity activity, ImageWatcher.Loader<T> l) { // attach
        if (activity == null) throw new NullPointerException("activity is null");
        if (l == null) throw new NullPointerException("loader is null");
        ImageWatcherHelper iwh = new ImageWatcherHelper(activity);
        iwh.loader = l;
        return iwh;
    }

    public ImageWatcherHelper<T> setTranslucentStatus(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
        return this;
    }

    public ImageWatcherHelper<T> setErrorImageRes(int resErrorImage) {
        this.resErrorImage = resErrorImage;
        return this;
    }

    public ImageWatcherHelper<T> setOnPictureLongPressListener(ImageWatcher.OnPictureLongPressListener<T> listener) {
        this.listener = listener;
        return this;
    }

    public ImageWatcherHelper<T> setIndexProvider(ImageWatcher.IndexProvider<T> ip) {
        indexProvider = ip;
        return this;
    }

    public ImageWatcherHelper<T> setLoadingUIProvider(ImageWatcher.LoadingUIProvider lp) {
        loadingUIProvider = lp;
        return this;
    }

    public ImageWatcherHelper<T> setOnStateChangedListener(ImageWatcher.OnStateChangedListener<T> listener) {
        onStateChangedListener = listener;
        return this;
    }

    public void show(ImageView i, SparseArray<ImageView> imageGroupList, List<T> urlList) {
        init();
        mImageWatcher.show(i, imageGroupList, urlList);
    }

    public void show(List<T> urlList, int initPos) {
        init();
        mImageWatcher.show(urlList, initPos);
    }

    private void init() {
        mImageWatcher = new ImageWatcher(holder);
        mImageWatcher.setId(VIEW_IMAGE_WATCHER_ID);
        mImageWatcher.setLoader(loader);
        mImageWatcher.setDetachAffirmative(); // helper
        if (statusBarHeight != null) mImageWatcher.setTranslucentStatus(statusBarHeight);
        if (resErrorImage != null) mImageWatcher.setErrorImageRes(resErrorImage);
        if (listener != null) mImageWatcher.setOnPictureLongPressListener(listener);
        if (indexProvider != null) mImageWatcher.setIndexProvider(indexProvider);
        if (loadingUIProvider != null) mImageWatcher.setLoadingUIProvider(loadingUIProvider);
        if (onStateChangedListener != null)
            mImageWatcher.setOnStateChangedListener(onStateChangedListener);

        removeExistingOverlayInView(activityDecorView); // 理论上是无意义的操作。在ImageWatcher 'dismiss' 时会移除自身。但检查一下不错
        activityDecorView.addView(mImageWatcher);
    }

    public boolean handleBackPressed() {
        return mImageWatcher != null && mImageWatcher.handleBackPressed();
    }

    private void removeExistingOverlayInView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child.getId() == VIEW_IMAGE_WATCHER_ID) {
                parent.removeView(child);
            }
            if (child instanceof ViewGroup) {
                removeExistingOverlayInView((ViewGroup) child);
            }
        }
    }

    public interface Provider<T> {
        ImageWatcherHelper<T> iwHelper();
    }
}
