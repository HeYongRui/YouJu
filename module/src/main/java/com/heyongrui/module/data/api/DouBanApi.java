package com.heyongrui.module.data.api;

import com.heyongrui.module.data.dto.DouBanDto;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DouBanApi {
    /**
     * 获取豆瓣索引标签
     *
     * @param type movie/tv
     */
    @FormUrlEncoded
    @POST("j/search_tags")
    Observable<Object> getIndexTags(@Field("type") String type, @Field("source") String source);

    /**
     * 获取豆瓣分类数据
     *
     * @param type      movie/tv
     * @param tag       豆瓣高分
     * @param sort      time/rank/recommend
     * @param pageSize  10
     * @param pageStart 0
     */
    @FormUrlEncoded
    @POST("j/search_subjects")
    Observable<DouBanDto> getSubjectsData(@Field("type") String type, @Field("tag") String tag, @Field("sort") String sort,
                                          @Field("page_limit") int pageSize, @Field("page_start") int pageStart);

}
