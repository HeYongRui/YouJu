package com.heyongrui.module2.data.service;

import android.text.TextUtils;

import com.heyongrui.base.utils.StringUtil;
import com.heyongrui.module2.data.api.QingMangApi;
import com.heyongrui.module2.data.dto.QingMangArticleListDto;
import com.heyongrui.module2.data.dto.QingMangCategoriesDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by lambert on 2018/11/2.
 */

public class QingMangService {

    private final String BASE_URL_QINGMANG = "https://api.qingmang.me/v2/";
    private final String QINGMANG_TOKEN = "c400a7e21688496ca3e7f17c6b0d1846";

    public QingMangService() {

    }

    public Observable<QingMangCategoriesDto> getQingMangCategories() {
        return ApiService.createApi(QingMangApi.class, BASE_URL_QINGMANG)
                .getQingMangCategories(QINGMANG_TOKEN)
                .compose(RxHelper.rxSchedulerHelper());
    }

    public Observable<QingMangArticleListDto> getQingMangArticleList(String category_id, String next_url) {
        Map<String, String> fields = new HashMap<>();
        if (TextUtils.isEmpty(next_url)) {//指定参数(第一页)
            fields.put("token", QINGMANG_TOKEN);
            fields.put("category_id", category_id);
        } else {//提取下一页参数
            fields = StringUtil.getParmMap(next_url);
        }
        return ApiService.createApi(QingMangApi.class, BASE_URL_QINGMANG)
                .getQingMangArticleList(fields)
                .compose(RxHelper.rxSchedulerHelper());
    }
}
