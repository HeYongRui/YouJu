package com.heyongrui.module2.data.api;

import com.heyongrui.module2.data.dto.GankDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mr.He on 2019/3/10.
 */

public interface GankApi {

    /**
     * @param category 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param per_page 请求个数： 数字，大于0
     * @param page     第几页：数字，大于0
     */
    @GET("data/{category}/{per_page}/{page}")
    Observable<GankDto> getGankCategory(@Path("category") String category, @Path("per_page") int per_page, @Path("page") int page);
}
