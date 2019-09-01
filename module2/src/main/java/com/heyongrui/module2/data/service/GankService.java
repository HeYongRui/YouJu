package com.heyongrui.module2.data.service;

import com.heyongrui.module2.data.api.GankApi;
import com.heyongrui.module2.data.dto.GankDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;

/**
 * Created by Mr.He on 2019/3/10.
 */

public class GankService {

    private final String BASE_URL_GANK = "http://gank.io/api/";

    public GankService() {

    }

    /**
     * @param category 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param perPage  请求个数： 数字，大于0
     * @param page     第几页：数字，大于0
     */
    public Observable<GankDto> getGankCategory(String category, int perPage, int page) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getGankCategory(category, perPage, page)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
