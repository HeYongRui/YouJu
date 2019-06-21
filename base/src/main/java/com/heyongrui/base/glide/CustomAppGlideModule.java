package com.heyongrui.base.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by lambert on 2018/1/17.
 * Glide全局配置
 */

@GlideModule
public class CustomAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        //覆写缓存大小
        int memoryCacheSizeBytes = 1024 * 1024 * 20;//20Mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        //覆写BitmapPool的大小
        int bitmapPoolSizeBytes = 1024 * 1024 * 20;//20Mb
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes));
        //覆写磁盘缓存的大小
        int diskCacheSizeBytes = 1024 * 1024 * 50;//50Mb
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
    }
}
