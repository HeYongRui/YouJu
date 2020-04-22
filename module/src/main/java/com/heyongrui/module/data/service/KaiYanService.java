package com.heyongrui.module.data.service;

import com.heyongrui.module.data.api.KaiYanApi;
import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;

public class KaiYanService {
    /**
     * 获取发现
     */
    public Observable<KaiYanDto> getDiscovery() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/")
                .getDiscovery()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取推荐
     */
    public Observable<KaiYanDto> getRecommend() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/")
                .getRecommend()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取日报
     */
    public Observable<KaiYanDto> getDaily() {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/")
                .getDaily()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取相关推荐
     */
    public Observable<KaiYanDto> getRelatedRecommend(int id) {
        return ApiService.createApi(KaiYanApi.class,
                "http://baobab.kaiyanapp.com/api/")
                .getRelatedRecommend(id)
                .compose(RxHelper.rxSchedulerHelper());
    }

//    public Flowable<Optional<KaiYanDto>> contractExpire(int id) {
//        return ApiService.createApi(KaiYanApi.class, "", CustomConverterFactory.create())
//                .getRelatedRecommend(id)
//                .compose(RxHelper.rxSchedulerHelperFlowable())
//                .compose(RxHelper.rxHandleResultFlowableNullable());
//    }

}
