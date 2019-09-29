package com.heyongrui.module.data.service;

import android.text.TextUtils;

import com.heyongrui.module.data.api.DailyLifeApi;
import com.heyongrui.module.data.dto.QDailyArticleDto;
import com.heyongrui.module.data.dto.QDailyColumnDto;
import com.heyongrui.module.data.dto.QDailyLabDetailDto;
import com.heyongrui.module.data.dto.QDailyLabsDto;
import com.heyongrui.module.data.dto.QDailyNewsDto;
import com.heyongrui.module.data.dto.QDailyResponse;
import com.heyongrui.module.data.dto.QDilyColumnInfoDto;
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.core.CoreApiException;
import com.heyongrui.network.service.ApiService;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

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

    /**
     * 获取好奇心日报首页NEWS
     *
     * @param pageKey 页面参数，用于分页(0.json,1558824932.json)
     */
    public Observable<QDailyNewsDto> getQDailyNews(String pageKey) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(pageKey)) {
            stringBuffer.append("0.json");
        } else {
            stringBuffer.append(pageKey);
            stringBuffer.append(".json");
        }
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyNews(stringBuffer.toString())
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报首页LABS
     *
     * @param pageKey 页面参数，用于分页(0.json,1558824932.json)
     */
    public Observable<QDailyLabsDto> getQDailyLabs(String pageKey) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(pageKey)) {
            stringBuffer.append("0.json");
        } else {
            stringBuffer.append(pageKey);
            stringBuffer.append(".json");
        }
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyLabs(stringBuffer.toString())
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报LABS详情
     *
     * @param postId LABS的ID(3027)
     */
    public Observable<QDailyLabDetailDto> getQDailyLabDetail(int postId) {
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyLabDetail(postId + ".json")
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报栏目中心
     *
     * @param pageKey 页面参数，用于分页(0,1558824932)
     */
    public Observable<QDailyColumnDto> getQDailyColumns(String pageKey) {
        pageKey = TextUtils.isEmpty(pageKey) ? "0" : pageKey;
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyColumns(pageKey)
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报栏目信息
     *
     * @param columnId 栏目ID(46)
     */
    public Observable<QDilyColumnInfoDto> getQDailyColumnInfo(int columnId) {
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyColumnInfo(columnId + ".json")
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报栏目下的文章列表
     *
     * @param columnId 栏目ID(46)
     * @param pageKey  页面参数，用于分页(0,1558824932)
     */
    public Observable<QDailyArticleDto> getQDailyColumnArticles(int columnId, String pageKey) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(pageKey)) {
            stringBuffer.append("0.json");
        } else {
            stringBuffer.append(pageKey);
            stringBuffer.append(".json");
        }
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyColumnArticles(columnId, stringBuffer.toString())
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 获取好奇心日报不同分类的文章列表
     *
     * @param category 类别(1-长文章 17-设计 16-Top15 19-时尚 3-娱乐 63-大公司头条 5-文化 18-商业 54-游戏 4-智能)
     * @param pageKey  页面参数，用于分页(0,1558824932)
     */
    public Observable<QDailyArticleDto> getQDailyCategoryArticles(int category, String pageKey) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(pageKey)) {
            stringBuffer.append("0.json");
        } else {
            stringBuffer.append(pageKey);
            stringBuffer.append(".json");
        }
        return ApiService.createApi(DailyLifeApi.class, "http://app3.qdaily.com/app3/")
                .getQDailyCategoryArticles(category, stringBuffer.toString())
                .compose(RxHelper.rxSchedulerHelper())
                .compose(qdailyHandleResult());
    }

    /**
     * 好奇心日报数据统一再处理
     */
    private <T> ObservableTransformer<QDailyResponse<T>, T> qdailyHandleResult() {
        return new ObservableTransformer<QDailyResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<QDailyResponse<T>> upstream) {
                return upstream.flatMap(new Function<QDailyResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(QDailyResponse<T> tCoreResponse) throws Exception {
                        QDailyResponse.Meta meta = tCoreResponse.getMeta();
                        int responseCode = meta == null ? -1 : meta.getStatus();
                        if (responseCode == 200) {
                            return Observable.create((ObservableOnSubscribe<T>) emitter -> {
                                try {
                                    emitter.onNext(tCoreResponse.getResponse());
                                    emitter.onComplete();
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            });
                        } else {
                            return Observable.error(new CoreApiException(responseCode, tCoreResponse.getMeta().getMsg()));
                        }
                    }
                });
            }
        };
    }
}
