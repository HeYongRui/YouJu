package com.heyongrui.module2.data.service;

import com.heyongrui.module2.data.api.GankApi;
import com.heyongrui.module2.data.dto.WelfareDto;
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

    public Observable<WelfareDto> getWelfare(int perPage, int page) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getGankCategory("福利", perPage, page)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<WelfareDto> getAndroid(int perPage, int page) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getGankCategory("Android", perPage, page)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
