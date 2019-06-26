package com.heyongrui.module.data.api;


import com.heyongrui.module.data.dto.KaiYanDto;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface KaiYanApi {
    /**
     * 获取推荐
     */
    @GET("/")
    Observable<KaiYanDto> getRecommend();

    /**
     * 获取发现
     */
    @GET("/")
    Observable<KaiYanDto> getDiscovery();

    /**
     * 获取日报
     */
    @GET("/")
    Observable<KaiYanDto> getDaily();
}
