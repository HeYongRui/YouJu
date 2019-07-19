package com.heyongrui.module.data.service;


import com.heyongrui.module.data.api.TextApi;
import com.heyongrui.module.data.dto.DuJiTang2Dto;
import com.heyongrui.module.data.dto.DuJiTangDto;
import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.data.dto.PoetryDto;
import com.heyongrui.module.data.dto.TodayRecommendPoemDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * lambert
 * 2019/6/25 10:52
 */
public class TextService {

    /**
     * 获取毒鸡汤
     */
    public Observable<DuJiTangDto> getDuJiTang() {
        return ApiService.createApi(TextApi.class, "https://soulposion.utilapi.bid/")
                .getDuJiTang()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取毒鸡汤
     */
    public Observable<DuJiTang2Dto> getDuJiTang2() {
        return ApiService.createApi(TextApi.class, "https://api.nextrt.com/")
                .getDuJiTang2()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取一言
     *
     * @param c 类型 （a-动画 b-漫画 c-游戏 d-小说 e-原创 f-网络 g-其他）
     */
    public Observable<HitokotoDto> getHitokoto(String c) {
        return ApiService.createApi(TextApi.class, "https://v1.hitokoto.cn/")
                .getHitokoto(c)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取诗词
     */
    public Observable<PoetryDto> getPoetrySentence() {
        return ApiService.createApi(TextApi.class, "https://v2.jinrishici.com/")
                .getPoetrySentence()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取今日荐诗的封面
     */
    public Observable<Object> getTodayRecommendCover() {
        return ApiService.createApi(TextApi.class, "http://kkpoem.duowan.com/")
                .getTodayRecommendCover()
                .compose(RxHelper.rxSchedulerHelper())
                .subscribeOn(Schedulers.io());
        //返回数据格式：{"status":200,"message":"","data":{"tp":"秋天","url":"http://kkpoem.bs2dl.yy.com/5e73db78037d79a2ac1bda808c5e7f0d.jpg"}}
    }

    /**
     * 获取今日荐诗
     */
    public Observable<TodayRecommendPoemDto> getTodayRecommendPoem() {
        return ApiService.createApi(TextApi.class, "http://kkpoem.duowan.com/")
                .getTodayRecommendPoem()
                .compose(RxHelper.rxSchedulerHelper())
                .subscribeOn(Schedulers.io());
    }
}
