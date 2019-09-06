package com.heyongrui.module.data.api;

import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DailyLifeApi {

    /**
     * 查询垃圾分类
     */
    @GET("api/GetRefuseClassification.php")
    Observable<Object> getGarbageCategory(@Query("key") String garbage);

    /**
     * 获取知乎日报最新消息（当天）
     */
    @GET("news/latest")
    Observable<ZhiHuDailyNewsDto> getZhiHuNewsLatest();

    /**
     * 获取知乎日报过往消息
     *
     * @param date 格式：20131119，获取的是20131118的消息
     */
    @GET("news/before/{date}")
    Observable<ZhiHuDailyNewsDto> getZhiHuPastNews(@Path("date") String date);

    /**
     * 获取知乎日报具体消息内容HTML
     *
     * @param id StoryBean中的id
     */
    @GET("news/{id}")
    Observable<Object> getZhiHuNewsHtmlContent(@Path("id") int id);
}
