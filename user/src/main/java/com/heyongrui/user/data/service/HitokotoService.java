package com.heyongrui.user.data.service;


import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;
import com.heyongrui.user.data.api.HitokotoApi;
import com.heyongrui.user.data.dto.DuJiTangDto;
import com.heyongrui.user.data.dto.HitokotoDto;

import io.reactivex.Observable;

/**
 * lambert
 * 2019/6/25 10:52
 */
public class HitokotoService {

    /**
     * 获取毒鸡汤
     */
    public Observable<DuJiTangDto> getDuJiTang() {
        return ApiService.createApi(HitokotoApi.class, "https://soulposion.utilapi.bid/")
                .getDuJiTang()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取一言
     *
     * @param c 类型 （a-动画 b-漫画 c-游戏 d-小说 e-原创 f-网络 g-其他）
     */
    public Observable<HitokotoDto> getHitokoto(String c) {
        return ApiService.createApi(HitokotoApi.class, "https://v1.hitokoto.cn/")
                .getHitokoto(c)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
