package com.heyongrui.module2.data.api

import com.heyongrui.module2.data.dto.HistoryTodayDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ModuleApi {

    /**
     * 历史上的今天
     *
     * @param day 查询的日期，例：0918（月日）
     */
    @GET("history/query?duid=&day=0918")
    fun getTodayHistory(@Query("key") key: String,
                        @Query("duid") duid: String,
                        @Query("day") day: String): Observable<HistoryTodayDto>


}