package com.heyongrui.module.data.service;

import com.heyongrui.module.data.api.DailyLifeApi;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;

public class DailyLifeService {

    /**
     * 查询垃圾分类
     */
    public Observable<Object> getGarbageCategory(String garbage) {
        return ApiService.createApi(DailyLifeApi.class, "http://www.atoolbox.net/")
                .getGarbageCategory(garbage)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
