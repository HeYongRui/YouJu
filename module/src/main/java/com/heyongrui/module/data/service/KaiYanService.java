package com.heyongrui.module.data.service;

import com.heyongrui.module.data.api.KaiYanApi;
import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;

public class KaiYanService {
    /**
     * 获取推荐
     */
    public Observable<KaiYanDto> getRecommend() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/v4/video/related?id=162083&udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23/")
                .getRecommend()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取发现
     */
    public Observable<KaiYanDto> getDiscovery() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery?udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23/")
                .getDiscovery()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取日报
     */
    public Observable<KaiYanDto> getDaily() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/v5/index/tab/feed?udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23/")
                .getDaily()
                .compose(RxHelper.rxSchedulerHelper());
    }
}
