package com.heyongrui.module2.data.api;

import com.heyongrui.module2.data.dto.QingMangArticleListDto;
import com.heyongrui.module2.data.dto.QingMangCategoriesDto;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lambert on 2018/11/2.
 */

public interface QingMangApi {

    @GET("category.list")
    Observable<QingMangCategoriesDto> getQingMangCategories(@Query("token") String token);

    @GET("article.list")
    Observable<QingMangArticleListDto> getQingMangArticleList(@QueryMap Map<String, String> fields);
}
