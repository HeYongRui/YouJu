package com.heyongrui.module2.data.service;

import com.heyongrui.module2.data.api.UnsplashApi;
import com.heyongrui.module2.data.dto.UnsplashPicDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by lambert on 2018/10/23.
 */

public class UnsplashService {

    private final String BASE_URL_UNSPLASH = "https://api.unsplash.com/";
    private final String UNSPLASH_ACCESS_KEY = "e12a659cb4239701ec163884f081ce2c34d28270b1f6bff2c9ac305a7e9b126a";

    public UnsplashService() {

    }

    public Observable<List<UnsplashPicDto>> getRandomPic(int page, int per_page) {
        return ApiService.createApi(UnsplashApi.class, BASE_URL_UNSPLASH)
                .getRandomPic(UNSPLASH_ACCESS_KEY, page, per_page)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<List<UnsplashPicDto>> getRandomPicMap(int page, int per_page) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("client_id", UNSPLASH_ACCESS_KEY);
        fields.put("page", page);
        fields.put("per_page", per_page);
        return ApiService.createApi(UnsplashApi.class, BASE_URL_UNSPLASH)
                .getRandomPicMap(fields)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
