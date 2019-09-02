package com.heyongrui.module.data.service;


import com.heyongrui.base.utils.StringUtil;
import com.heyongrui.module.data.api.TextApi;
import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.data.dto.PoemDetailDto;
import com.heyongrui.module.data.dto.PoemGroupDetailDto;
import com.heyongrui.module.data.dto.PoemGroupDto;
import com.heyongrui.module.data.dto.PoemSearchDto;
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
                .subscribeOn(Schedulers.io());
        //返回数据格式：{"status":200,"message":"","data":{"tp":"秋天","url":"http://kkpoem.bs2dl.yy.com/5e73db78037d79a2ac1bda808c5e7f0d.jpg"}}
    }

    /**
     * 获取今日荐诗
     */
    public Observable<TodayRecommendPoemDto> getTodayRecommendPoem() {
        return ApiService.createApi(TextApi.class, "http://kkpoem.duowan.com/")
                .getTodayRecommendPoem()
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取诗歌完整信息
     *
     * @param id id
     */
    public Observable<PoemDetailDto> getPoemDetail(int id) {
        return ApiService.createApi(TextApi.class, "http://kkpoem.duowan.com/")
                .getPoemDetail(id, StringUtil.MD5encrypt32BitLowerCase(id + "6d767896a2e4b60d"))
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据关键词搜索诗歌
     *
     * @param keywords 关键词
     * @param type     搜索类型 1-原文 2-标题 4-作者
     */
    public Observable<PoemSearchDto> searchPoem(String keywords, int type) {
        return ApiService.createApi(TextApi.class, "http://kkpoem.duowan.com/")
                .searchPoem(keywords, type, StringUtil.MD5encrypt32BitLowerCase(keywords + "6d767896a2e4b60d"))
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 分组诗歌
     */
    public Observable<PoemGroupDto> groupList(int sort, int pageNo, int pageSize) {
        return ApiService.createApi(TextApi.class, "http://kkpoembbs.duowan.com/")
                .groupList(sort, pageNo, pageSize)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 分组详情
     */
    public Observable<PoemGroupDetailDto> groupDetail(String groupId, int pageNo, int pageSize) {
        return ApiService.createApi(TextApi.class, "http://kkpoembbs.duowan.com/")
                .groupDetail(groupId, pageNo, pageSize)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 智能机器人(天气、翻译、藏头诗、笑话、歌词、计算、域名信息/备案/收录查询、IP查询、手机号码归属、人工智能聊天)
     * {"result":0,"content":"内容"}
     */
    public Observable<Object> smartRobot(String conversationContent) {
        return ApiService.createApi(TextApi.class, "http://api.qingyunke.com/")
                .smartRobot(conversationContent)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
