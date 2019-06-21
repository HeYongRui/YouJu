package com.heyongrui.network.configure;


import com.heyongrui.network.core.CoreApiException;
import com.heyongrui.network.core.CoreHeader;
import com.heyongrui.network.core.CoreResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hpw on 16/11/2.
 */

public class RxHelper {

    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<CoreResponse<T>, T> handleResult() {
        return new ObservableTransformer<CoreResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return createData(tCoreResponse.getData());
                        } else {
                            return Observable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 倒计时
     */
    public static Observable<Integer> countdown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
