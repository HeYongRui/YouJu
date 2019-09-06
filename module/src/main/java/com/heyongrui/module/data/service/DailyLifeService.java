package com.heyongrui.module.data.service;

import com.heyongrui.module.data.api.DailyLifeApi;
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto;
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

    /**
     * 获取知乎日报最新消息（当天）
     */
    public Observable<ZhiHuDailyNewsDto> getZhiHuNewsLatest() {
        return ApiService.createApi(DailyLifeApi.class, "https://news-at.zhihu.com/api/4/")
                .getZhiHuNewsLatest()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取知乎日报过往消息
     *
     * @param date 格式：20131119，获取的是20131118的消息
     */
    public Observable<ZhiHuDailyNewsDto> getZhiHuPastNews(String date) {
        return ApiService.createApi(DailyLifeApi.class, "https://news-at.zhihu.com/api/4/")
                .getZhiHuPastNews(date)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取知乎日报具体消息内容HTML
     *
     * @param id StoryBean中的id
     */
    public Observable<Object> getZhiHuNewsHtmlContent(int id) {
        return ApiService.createApi(DailyLifeApi.class, "https://news-at.zhihu.com/api/4/")
                .getZhiHuNewsHtmlContent(id)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
