package com.heyongrui.module2.data.api;

import com.heyongrui.module2.data.dto.WelfareDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mr.He on 2019/3/10.
 */

public interface GankApi {

    @GET("data/福利/{per_page}/{page}")
    Observable<WelfareDto> getWelfare(@Path("per_page") int per_page, @Path("page") int page);
}
