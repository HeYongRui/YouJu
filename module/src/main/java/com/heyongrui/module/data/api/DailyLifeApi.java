package com.heyongrui.module.data.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DailyLifeApi {

    /**
     * 查询垃圾分类
     */
    @GET("api/GetRefuseClassification.php")
    Observable<Object> getGarbageCategory(@Query("key") String garbage);
}
