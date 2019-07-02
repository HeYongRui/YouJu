package com.heyongrui.network.configure;


import com.heyongrui.network.core.CoreApiException;
import com.heyongrui.network.core.CoreHeader;
import com.heyongrui.network.core.CoreResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * lambert
 * 2019/6/20 17:15
 */
public class RxHelper {

    /**
     * 统一简化线程
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 对返回数据结果统一泛型处理(适用于所有接口返回数据格式统一类型，可选)
     */
    public static <T> ObservableTransformer<CoreResponse<T>, T> handleResult() {
        return upstream -> upstream.flatMap((Function<CoreResponse<T>, ObservableSource<T>>) tCoreResponse -> {
            CoreHeader header = tCoreResponse.getHeader();
            int responseCode = header == null ? -1 : header.getStatus();
            if (responseCode == 200) {
                return Observable.create((ObservableOnSubscribe<T>) emitter -> {
                    try {
                        emitter.onNext(tCoreResponse.getData());
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
            } else {
                return Observable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
            }
        });
    }
}
