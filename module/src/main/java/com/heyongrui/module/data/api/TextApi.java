package com.heyongrui.module.data.api;


import com.heyongrui.module.data.dto.HitokotoDto;
import com.heyongrui.module.data.dto.PoemDetailDto;
import com.heyongrui.module.data.dto.PoemGroupDetailDto;
import com.heyongrui.module.data.dto.PoemGroupDto;
import com.heyongrui.module.data.dto.PoemSearchDto;
import com.heyongrui.module.data.dto.PoetryDto;
import com.heyongrui.module.data.dto.TodayRecommendPoemDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TextApi {

    /**
     * 获取一言
     *
     * @param c 类型 （a-动画 b-漫画 c-游戏 d-小说 e-原创 f-网络 g-其他）
     */
    @GET("/")
    Observable<HitokotoDto> getHitokoto(@Query("c") String c);

    /**
     * 获取诗词
     */
    @Headers("X-User-Token: sqXg71pyTuRQIcdOkkSMYkkLvrwY58oG")
    @GET("sentence")
    Observable<PoetryDto> getPoetrySentence();

    /**
     * 获取今日荐诗的封面
     */
    @GET("api/recommend/cover.do")
    Observable<Object> getTodayRecommendCover();

    /**
     * 获取今日荐诗
     */
    @GET("api/recommend/list.do")
    Observable<TodayRecommendPoemDto> getTodayRecommendPoem();

    /**
     * 获取诗歌完整信息
     *
     * @param id   id
     * @param sign 签名
     */
    @GET("api/poem/get.do")
    Observable<PoemDetailDto> getPoemDetail(@Query("id") int id, @Query("sign") String sign);

    /**
     * 根据关键词搜索诗歌
     *
     * @param keywords 关键词
     * @param type     搜索类型 1-原文 2-标题 4-作者
     * @param sign     签名
     */
    @GET("api/poem/query.do")
    Observable<PoemSearchDto> searchPoem(@Query("s") String keywords, @Query("type") int type, @Query("sign") String sign);

    /**
     * 分组诗歌
     */
    @GET("api/group/list.do")
    Observable<PoemGroupDto> groupList(@Query("sort") int sort, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 分组详情
     */
    @GET("api/group/topicList.do")
    Observable<PoemGroupDetailDto> groupDetail(@Query("groupId") String groupId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 智能机器人(天气、翻译、藏头诗、笑话、歌词、计算、域名信息/备案/收录查询、IP查询、手机号码归属、人工智能聊天)
     * {"result":0,"content":"内容"}
     */
    @GET("api.php?key=free&appid=0")
    Observable<Object> smartRobot(@Query("msg") String msg);
}
