package com.heyongrui.module2.data.service;

import com.heyongrui.module2.data.api.GankApi;
import com.heyongrui.module2.data.dto.GankDto;
import com.heyongrui.module2.data.dto.GankResponse;
import com.heyongrui.module2.data.dto.LeisureReadCategoryDto;
import com.heyongrui.module2.data.dto.LeisureReadDto;
import com.heyongrui.module2.data.dto.LeisureReadSubCategoryDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mr.He on 2019/3/10.
 */

public class GankService {

    private final String BASE_URL_GANK = "http://gank.io/api/";

    public GankService() {

    }

    /**
     * 获取分类数据
     *
     * @param category 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param perPage  请求个数： 数字，大于0
     * @param page     第几页：数字，大于0
     */
    public Observable<GankResponse<List<GankDto>>> getGankCategory(String category, int perPage, int page) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getGankCategory(category, perPage, page)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取闲读主分类
     */
    public Observable<GankResponse<List<LeisureReadCategoryDto>>> getLeisureReadCategory() {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getLeisureReadCategory()
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取闲读子分类
     *
     * @param subCategory 后面可接受参数为主分类返回的en_name,例如【apps， wow， android，iOS】
     */
    public Observable<GankResponse<List<LeisureReadSubCategoryDto>>> getLeisureReadSubCategory(String subCategory) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getLeisureReadSubCategory(subCategory)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 获取闲读数据
     *
     * @param id      后面可接受参数为子分类返回的id
     * @param perPage 每页的个数
     * @param page    第几页，从1开始
     */
    public Observable<GankResponse<List<LeisureReadDto>>> getLeisureRead(String id, int perPage, int page) {
        return ApiService.createApi(GankApi.class, BASE_URL_GANK)
                .getLeisureRead(id, perPage, page)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
