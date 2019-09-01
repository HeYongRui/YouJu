package com.heyongrui.module2.data.api;

import com.heyongrui.module2.data.dto.GankDto;
import com.heyongrui.module2.data.dto.GankResponse;
import com.heyongrui.module2.data.dto.LeisureReadCategoryDto;
import com.heyongrui.module2.data.dto.LeisureReadDto;
import com.heyongrui.module2.data.dto.LeisureReadSubCategoryDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mr.He on 2019/3/10.
 */

public interface GankApi {

    /**
     * 获取分类数据
     *
     * @param category 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param per_page 请求个数： 数字，大于0
     * @param page     第几页：数字，大于0
     */
    @GET("data/{category}/{per_page}/{page}")
    Observable<GankResponse<List<GankDto>>> getGankCategory(@Path("category") String category, @Path("per_page") int per_page, @Path("page") int page);

    /**
     * 获取闲读主分类
     */
    @GET("/api/xiandu/categories")
    Observable<GankResponse<List<LeisureReadCategoryDto>>> getLeisureReadCategory();

    /**
     * 获取闲读子分类
     */
    @GET("/api/xiandu/category/{subCategory}")
    Observable<GankResponse<List<LeisureReadSubCategoryDto>>> getLeisureReadSubCategory(@Path("subCategory") String subCategory);

    /**
     * 获取闲读数据
     *
     * @param id      后面可接受参数为子分类返回的id
     * @param perPage 每页的个数
     * @param page    第几页，从1开始
     */
    @GET("/api/xiandu/data/id/{id}/count/{perPage}/page/{page}")
    Observable<GankResponse<List<LeisureReadDto>>> getLeisureRead(@Path("id") String id, @Path("perPage") int perPage, @Path("page") int page);
}
