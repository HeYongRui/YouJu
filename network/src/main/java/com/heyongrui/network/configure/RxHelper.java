package com.heyongrui.network.configure;


import com.heyongrui.network.core.CoreApiException;
import com.heyongrui.network.core.CoreHeader;
import com.heyongrui.network.core.CoreResponse;
import com.heyongrui.network.core.Optional;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * lambert
 * 2019/6/20 17:15
 */
public class RxHelper {

    /**
     * 统一线程处理（常规式）
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理（背压式）
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelperFlowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 请求数据统一再处理（常规式）
     */
    public static <T> ObservableTransformer<CoreResponse<T>, T> rxHandleResultObservable() {
        return new ObservableTransformer<CoreResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(CoreResponse<T> tCoreResponse) throws Exception {
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
                    }
                });
            }
        };
    }

    /**
     * 请求数据统一再处理（常规式，可为空）
     */
    public static <T> ObservableTransformer<CoreResponse<T>, Optional<T>> rxHandleResultObservableNullable() {
        return new ObservableTransformer<CoreResponse<T>, Optional<T>>() {
            @Override
            public ObservableSource<Optional<T>> apply(Observable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, ObservableSource<Optional<T>>>() {
                    @Override
                    public ObservableSource<Optional<T>> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Observable.create(e -> {
                                try {
                                    e.onNext(tCoreResponse.transform());
                                    e.onComplete();
                                } catch (Exception exc) {
                                    e.onError(exc);
                                }
                            });
                        } else {
                            return Observable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 请求数据统一再处理（背压式）
     */
    public static <T> FlowableTransformer<CoreResponse<T>, T> rxHandleResultFlowable() {
        return new FlowableTransformer<CoreResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Flowable.create(new FlowableOnSubscribe<T>() {
                                @Override
                                public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                                    try {
                                        emitter.onNext(tCoreResponse.getData());
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else {
                            return Flowable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 请求数据统一再处理（背压式，可为空）
     */
    public static <T> FlowableTransformer<CoreResponse<T>, Optional<T>> rxHandleResultFlowableNullable() {
        return new FlowableTransformer<CoreResponse<T>, Optional<T>>() {
            @Override
            public Publisher<Optional<T>> apply(Flowable<CoreResponse<T>> upstream) {
                return upstream.flatMap(new Function<CoreResponse<T>, Publisher<Optional<T>>>() {
                    @Override
                    public Publisher<Optional<T>> apply(CoreResponse<T> tCoreResponse) throws Exception {
                        CoreHeader header = tCoreResponse.getHeader();
                        int responseCode = header == null ? -1 : header.getStatus();
                        if (responseCode == 200) {
                            return Flowable.create(new FlowableOnSubscribe<Optional<T>>() {
                                @Override
                                public void subscribe(FlowableEmitter<Optional<T>> emitter) throws Exception {
                                    try {
                                        emitter.onNext(tCoreResponse.transform());
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else {
                            return Flowable.error(new CoreApiException(responseCode, tCoreResponse.getHeader().getMsg()));
                        }
                    }
                });
            }
        };
    }
}
