package com.heyongrui.module.data.api;


import com.heyongrui.module.data.dto.MonoAlbumDto;
import com.heyongrui.module.data.dto.MonoCategoryDto;
import com.heyongrui.module.data.dto.MonoHistoryTeaDateDto;
import com.heyongrui.module.data.dto.MonoTabDto;
import com.heyongrui.module.data.dto.MonoTeaDto;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lambert on 2018/11/5.
 */

public interface MonoApi {

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/tea/{query_date}/full/")
    Observable<MonoTeaDto> getTea(@Path("query_date") String query_date);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/tea/history/")
    Observable<MonoHistoryTeaDateDto> getTeaHistoryDate(@Query("start") Integer start);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/tea/{id}/")
    Observable<Object> getHistoryTea(@Path("id") int id);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/domain_category/{category_id}/")
    Observable<MonoCategoryDto> getCategory(@Path("category_id") int category_id, @Query("start") Integer start);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("g/meow/{meow_id}/")
    Observable<ResponseBody> getMeowDetail(@Path("meow_id") int meow_id);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/tab/")
    Observable<MonoTabDto> getTab(@Query("tab_id") int tab_id, @Query("start") String start, @Query("tab_type") int tab_type);

    @Headers({"HTTP-AUTHORIZATION: baca3af8ad0611e99452525400b42a60"})
    @GET("api/v3/tab/")
    Observable<MonoAlbumDto> getAlbums(@Query("tab_id") String tab_id, @Query("tab_type") String tab_type, @Query("start") String start);
}
