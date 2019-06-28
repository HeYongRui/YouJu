package com.heyongrui.module.data.api;


import com.heyongrui.module.data.dto.KaiYanDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KaiYanApi {

    /**
     * 获取发现
     */
    @GET("v7/index/tab/discovery?udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23")
    Observable<KaiYanDto> getDiscovery();

    /**
     * 获取推荐
     */
    @GET("v5/index/tab/allRec?page=0&isOldUser=true&udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23")
    Observable<KaiYanDto> getRecommend();

    /**
     * 获取日报
     */
    @GET("v5/index/tab/feed?udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23")
    Observable<KaiYanDto> getDaily();

    /**
     * 获取相关推荐
     */
    @GET("v4/video/related?udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23")
    Observable<KaiYanDto> getRelatedRecommend(@Query("id") int id);
}
