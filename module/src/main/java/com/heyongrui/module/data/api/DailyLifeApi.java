package com.heyongrui.module.data.api;

import com.heyongrui.module.data.dto.QDailyArticleDto;
import com.heyongrui.module.data.dto.QDailyColumnDto;
import com.heyongrui.module.data.dto.QDailyLabDetailDto;
import com.heyongrui.module.data.dto.QDailyLabsDto;
import com.heyongrui.module.data.dto.QDailyNewsDto;
import com.heyongrui.module.data.dto.QDailyResponse;
import com.heyongrui.module.data.dto.QDilyColumnInfoDto;
import com.heyongrui.module.data.dto.ZhiHuDailyNewsDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DailyLifeApi {

    /**
     * 查询垃圾分类
     */
    @GET("api/GetRefuseClassification.php")
    Observable<Object> getGarbageCategory(@Query("key") String garbage);

    /**
     * 获取知乎日报最新消息（当天）
     */
    @GET("news/latest")
    Observable<ZhiHuDailyNewsDto> getZhiHuNewsLatest();

    /**
     * 获取知乎日报过往消息
     *
     * @param date 格式：20131119，获取的是20131118的消息
     */
    @GET("news/before/{date}")
    Observable<ZhiHuDailyNewsDto> getZhiHuPastNews(@Path("date") String date);

    /**
     * 获取知乎日报具体消息内容HTML
     *
     * @param id StoryBean中的id
     */
    @GET("news/{id}")
    Observable<Object> getZhiHuNewsHtmlContent(@Path("id") int id);

    /**
     * 获取好奇心日报首页NEWS
     *
     * @param pageKey 页面参数，用于分页(0.json,1558824932.json)
     */
    @GET("homes/index_v2/{page_key}")
    Observable<QDailyResponse<QDailyNewsDto>> getQDailyNews(@Path("page_key") String pageKey);

    /**
     * 获取好奇心日报首页LABS
     *
     * @param pageKey 页面参数，用于分页(0.json,1558824932.json)
     */
    @GET("papers/index/{page_key}")
    Observable<QDailyResponse<QDailyLabsDto>> getQDailyLabs(@Path("page_key") String pageKey);

    /**
     * 获取好奇心日报LABS详情
     *
     * @param detailKey 详情键(3027.json)
     */
    @GET("papers/detail/{detail_key}")
    Observable<QDailyResponse<QDailyLabDetailDto>> getQDailyLabDetail(@Path("detail_key") String detailKey);

    /**
     * 获取好奇心日报栏目中心
     *
     * @param pageKey 页面参数，用于分页(0,1558824932)
     */
    @GET("columns/all_columns_index/{page_key}")
    Observable<QDailyResponse<QDailyColumnDto>> getQDailyColumns(@Path("page_key") String pageKey);

    /**
     * 获取好奇心日报栏目信息
     *
     * @param columnId 栏目ID(0.json,56.json)
     */
    @GET("columns/info/{column_id}")
    Observable<QDailyResponse<QDilyColumnInfoDto>> getQDailyColumnInfo(@Path("column_id") String columnId);

    /**
     * 获取好奇心日报栏目下的文章列表
     *
     * @param columnId 栏目ID(46)
     * @param pageKey  页面参数，用于分页(0.json,1558824932.json)
     */
    @GET("columns/index/{column_id}/{page_key}")
    Observable<QDailyResponse<QDailyArticleDto>> getQDailyColumnArticles(@Path("column_id") int columnId,
                                                                         @Path("page_key") String pageKey);

    /**
     * 获取好奇心日报不同分类的文章列表
     *
     * @param category 类别(1-长文章 17-设计 16-Top15 19-时尚 3-娱乐 63-大公司头条 5-文化 18-商业 54-游戏 4-智能)
     * @param pageKey  页面参数，用于分页(0.json,1558824932.json)
     */
    @GET("categories/index/{category}/{page_key}")
    Observable<QDailyResponse<QDailyArticleDto>> getQDailyCategoryArticles(@Path("category") int category,
                                                                           @Path("page_key") String pageKey);
}
