package com.heyongrui.network.configure;


import com.blankj.utilcode.util.Utils;
import com.heyongrui.base.utils.FileUtil;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Brian Wu on 2016/12/15.
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        File cacheDirectory = FileUtil.getCacheDirectory(Utils.getApp(), "");
        Cache cache = new Cache(cacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
        return cache;
    }
}
