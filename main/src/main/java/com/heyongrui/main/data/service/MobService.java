package com.heyongrui.main.data.service;

import android.util.Base64;

import com.heyongrui.main.data.api.MobApi;
import com.heyongrui.main.data.dto.DictionaryDto;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.GeocoderDto;
import com.heyongrui.main.data.dto.IDCardDto;
import com.heyongrui.main.data.dto.IdiomDto;
import com.heyongrui.main.data.dto.MobResponse;
import com.heyongrui.main.data.dto.WeatherDto;
import com.heyongrui.network.configure.RxHelper;
import com.heyongrui.network.service.ApiService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.reactivex.Observable;

public class MobService {

    /**
     * 分词解析
     */
    public Observable<MobResponse<List<String>>> participleParse(String originStr) {
        String base64Str;
        try {
            byte[] contentByte = originStr.getBytes("UTF-8");
            base64Str = Base64.encodeToString(contentByte, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            base64Str = "";
        }
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .participleParse("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", "common", base64Str)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 成语查询
     */
    public Observable<MobResponse<IdiomDto>> idiomQuery(String idiomStr) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .idiomQuery("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", idiomStr)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 新华字典查询
     */
    public Observable<MobResponse<DictionaryDto>> dictionaryQuery(String word) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .dictionaryQuery("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", word)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 身份证信息查询
     */
    public Observable<MobResponse<IDCardDto>> idCardQuery(String word) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .idCardQuery("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", word)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据起落城市名或者机场代码查询航班信息（start=北京/BJS）
     */
    public Observable<MobResponse<List<FlightDto>>> flightQuery(String start, String end) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .flightQuery("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", start, end)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据航班号查询航班信息（name=CZ9902）
     */
    public Observable<MobResponse<List<FlightDto>>> flightNoQuery(String flightNo) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .flightNoQuery("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", flightNo)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据ip地址查询天气
     */
    public Observable<MobResponse<List<WeatherDto>>> weatherQueryByIp(String ip) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .weatherQueryByIp("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", ip)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据城市名称查询天气
     */
    public Observable<MobResponse<List<WeatherDto>>> weatherQueryByCity(String city) {
        return ApiService.createApi(MobApi.class, "http://apicloud.mob.com/")
                .weatherQueryByCity("moba6b6c6d6", "43275ff45b034bffaf6b9941a216fe6dbae31dc9", city)
                .compose(RxHelper.rxSchedulerHelper());
    }

    /**
     * 根据经纬度获取地址信息
     */
    public Observable<GeocoderDto> getAddressByLocation(double lat, double lon) {
        return ApiService.createApi(MobApi.class, "http://api.map.baidu.com/")
                .getAddressByLocation("json", lat + "," + lon, "esNPFDwwsXWtsQfw4NMNmur1")
                .compose(RxHelper.rxSchedulerHelper());
    }

}
