package com.heyongrui.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.heyongrui.base.glide.GlideApp;
import com.heyongrui.base.glide.GlideRequest;
import com.heyongrui.base.glide.transformations.RoundedCornersTransformation;

import java.util.concurrent.ExecutionException;


/**
 * lambert
 * 2018/1/18 18:04
 */
public class GlideUtil {

    /**
     * 默认方式加载资源图片
     */
    public static void loadImage(Context context, Object resource, ImageView imageView, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load(resource).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载缩略图
     */
    public static void loadThumbnail(Context context, Object resource, ImageView imageView,
                                     @FloatRange(from = 0, to = 1) float thumbnailSizeMultiplier, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load(resource).thumbnail(thumbnailSizeMultiplier).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载指定大小图片
     */
    public static void loadSize(Context context, Object resource, ImageView imageView, int width, int height, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .override(width, height)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load(resource).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载指定循环次数的Gif
     */
    public static void loadGif(Context context, Object resource, ImageView imageView, int loopCount,
                               Animatable2Compat.AnimationCallback animationCallback, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).asGif().load(resource).apply(options).listener(new RequestListener<GifDrawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                    if (resource != null) {
                        if (loopCount > 0) {
                            resource.setLoopCount(loopCount);
                        }
                        if (animationCallback != null) {
                            resource.registerAnimationCallback(animationCallback);
                        }
                    }
                    return false;
                }
            }).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载asserts目录下的图片
     */
    public static void loadAssets(Context context, String path, ImageView imageView, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load("file:///android_asset/" + path).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载圆角资源图片
     */
    public static void loadRound(Context context, Object resource, ImageView imageView, int radius,
                                 RoundedCornersTransformation.CornerType cornerType, @DrawableRes int placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .centerCrop()
                    .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCornersTransformation(radius, 0, cornerType)))
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load(resource).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载圆形资源图片
     */
    public static void loadCircle(Context context, Object resource, ImageView imageView, @Nullable Drawable placeholder) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .error(placeholder)
                    .dontAnimate()
                    .centerCrop()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context).load(resource).apply(options).into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过图片URL获得Bitmap(需在子线程中运行)
     */
    public static Bitmap getBitmap(Context context, Object resource, boolean is_circle, Integer width, Integer height) {
        Bitmap bitmap = null;
        try {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            if (is_circle) {
                options.circleCrop();
            }
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideRequest<Bitmap> apply = GlideApp.with(context).asBitmap().load(resource).apply(options);
            if (width != null && width != 0 && height != null && height != 0) {
                bitmap = apply.submit(width, height).get();
            } else {
                bitmap = apply.submit().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 清除缓存
     */
    public void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(() -> clearDiskCache(context)).start();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }
}
