package com.heyongrui.main.data.api;

import com.heyongrui.main.data.dto.DictionaryDto;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.GeocoderDto;
import com.heyongrui.main.data.dto.IDCardDto;
import com.heyongrui.main.data.dto.IdiomDto;
import com.heyongrui.main.data.dto.MobResponse;
import com.heyongrui.main.data.dto.WeatherDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MobApi {

    /**
     * 分词解析
     */
    @GET("word/analyzer")
    Observable<MobResponse<List<String>>> participleParse(@Query("key") String key,
                                                          @Query("duid") String duid,
                                                          @Query("type") String type,
                                                          @Query("text") String base64Str);

    /**
     * 成语查询
     */
    @GET("appstore/idiom/query")
    Observable<MobResponse<IdiomDto>> idiomQuery(@Query("key") String key,
                                                 @Query("duid") String duid,
                                                 @Query("name") String idiomStr);

    /**
     * 新华字典查询
     */
    @GET("appstore/dictionary/query")
    Observable<MobResponse<DictionaryDto>> dictionaryQuery(@Query("key") String key,
                                                           @Query("duid") String duid,
                                                           @Query("name") String word);

    /**
     * 身份证信息查询
     */
    @GET("idcard/query")
    Observable<MobResponse<IDCardDto>> idCardQuery(@Query("key") String key,
                                                   @Query("duid") String duid,
                                                   @Query("cardno") String cardNo);

    /**
     * 根据起落城市名或者机场代码查询航班信息（start=北京/BJS）
     */
    @GET("flight/line/query")
    Observable<MobResponse<List<FlightDto>>> flightQuery(@Query("key") String key,
                                                         @Query("duid") String duid,
                                                         @Query("start") String start,
                                                         @Query("end") String end);

    /**
     * 根据航班号查询航班信息（name=CZ9902）
     */
    @GET("flight/no/query")
    Observable<MobResponse<List<FlightDto>>> flightNoQuery(@Query("key") String key,
                                                           @Query("duid") String duid,
                                                           @Query("name") String flightNo);

    /**
     * 查询天气（根据ip地址）
     */
    @GET("v1/weather/ip")
    Observable<MobResponse<List<WeatherDto>>> weatherQueryByIp(@Query("key") String key,
                                                               @Query("duid") String duid,
                                                               @Query("ip") String ip);

    /**
     * 查询天气（根据城市名称）
     */
    @GET("v1/weather/query")
    Observable<MobResponse<List<WeatherDto>>> weatherQueryByCity(@Query("key") String key,
                                                                 @Query("duid") String duid,
                                                                 @Query("city") String city);

    /**
     * 根据经纬度获取地址信息
     */
    @GET("geocoder")
    Observable<GeocoderDto> getAddressByLocation(@Query("output") String output,
                                                 @Query("location") String location,
                                                 @Query("ak") String ak);

}
