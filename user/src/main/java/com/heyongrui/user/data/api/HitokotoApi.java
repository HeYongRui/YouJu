package com.heyongrui.user.data.api;

import com.heyongrui.user.data.dto.DuJiTangDto;
import com.heyongrui.user.data.dto.HitokotoDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HitokotoApi {

    /**
     * 获取毒鸡汤
     */
    @GET("json")
    Observable<DuJiTangDto> getDuJiTang();

    /**
     * 获取一言
     *
     * @param c 类型 （a-动画 b-漫画 c-游戏 d-小说 e-原创 f-网络 g-其他）
     */
    @GET("/")
    Observable<HitokotoDto> getHitokoto(@Query("c") String c);

}
