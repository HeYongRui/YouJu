package com.heyongrui.module.data.service;

import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.module.data.api.MonoApi;
import com.heyongrui.module.data.dto.MonoAlbumDto;
import com.heyongrui.module.data.dto.MonoCategoryDto;
import com.heyongrui.module.data.dto.MonoHistoryTeaDateDto;
import com.heyongrui.module.data.dto.MonoTabDto;
import com.heyongrui.module.data.dto.MonoTeaDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import java.util.Date;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by lambert on 2018/11/5.
 */

public class MonoSerevice {

    private final String BASE_URL_MONO = "http://mmmono.com/";

    public MonoSerevice() {

    }

    public Observable<MonoTeaDto> getTea() {
        String dateString = null;
        try {
            dateString = TimeUtil.getDateString(new Date(), TimeUtil.DAY_ONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getTea(dateString)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<MonoHistoryTeaDateDto> getTeaHistoryDate(Integer start) {
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getTeaHistoryDate(start)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<Object> getHistoryTea(int id) {
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getHistoryTea(id)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<MonoCategoryDto> getCategory(int category_id, Integer start) {
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getCategory(category_id, start)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<ResponseBody> getMeowDetail(int meow_id) {
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getMeowDetail(meow_id)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<MonoTabDto> getMusicTab(int page, int per_page) {
        String start = page + "," + per_page;
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getTab(8, start, 3)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<MonoAlbumDto> getAlbums(int start, int end) {
        return ApiService.createApi(MonoApi.class, BASE_URL_MONO)
                .getAlbums("9", "3", start + "," + end)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
