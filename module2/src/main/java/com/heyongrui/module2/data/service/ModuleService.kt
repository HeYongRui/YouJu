package com.heyongrui.module2.data.service

import com.heyongrui.module2.data.api.ModuleApi
import com.heyongrui.module2.data.dto.HistoryTodayDto
import com.heyongrui.network.configure.RxHelper
import com.heyongrui.network.service.ApiService
import io.reactivex.Observable

class ModuleService {

    /**
     * 历史上的今天
     *
     * @param day 查询的日期，例：0918（月日）
     */
    fun getTodayHistory(day: String): Observable<HistoryTodayDto> {
        return ApiService.createApi(ModuleApi::class.java, "http://apicloud.mob.com/appstore/")
                .getTodayHistory("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", day)
                .compose(RxHelper.rxSchedulerHelper())
    }
}