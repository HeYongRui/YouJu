package com.heyongrui.module.data.service;

import com.heyongrui.module.data.api.DouBanApi;
import com.heyongrui.module.data.dto.DouBanDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;

public class DouBanService {
    /**
     * 获取豆瓣索引标签
     * {"tags":["热门","最新","经典","可播放","豆瓣高分","冷门佳片","华语","欧美","韩国","日本","动作","喜剧","爱情","科幻","悬疑","恐怖","治愈"]}
     *
     * @param type movie/tv
     */
    public Observable<Object> getIndexTags(String type, String source) {
        return ApiService.createApi(DouBanApi.class, "https://movie.douban.com/")
                .getIndexTags(type, source)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取豆瓣分类数据
     *
     * @param type      movie/tv
     * @param tag       豆瓣高分
     * @param sort      time/rank/recommend
     * @param pageSize  10
     * @param pageStart 0
     */
    public Observable<DouBanDto> getSubjectsData(String type, String tag, String sort, int pageSize, int pageStart) {
        return ApiService.createApi(DouBanApi.class, "https://movie.douban.com/")
                .getSubjectsData(type, tag, sort, pageSize, pageStart)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
