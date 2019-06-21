package com.heyongrui.base.rxbus;


import androidx.annotation.StringDef;
import androidx.collection.ArrayMap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;


/**
 * 线程调度器常量类
 */
public class RxBusScheduler {

    private static ArrayMap<String, Scheduler> sSchedulersMapper;

    public static final String NEW_THREAD = "newThread";
    public static final String COMPUTATION = "computation";
    public static final String IMMEDIATE = "immediate";
    public static final String IO = "io";
    public static final String TEST = "test";
    public static final String SINGLE = "single";
    public static final String MAIN_THREAD = "main_thread";

    @StringDef({NEW_THREAD, COMPUTATION, IMMEDIATE, IO, TEST, SINGLE, MAIN_THREAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Theme {
    }

    static {
        sSchedulersMapper = new ArrayMap<>();
        sSchedulersMapper.put(NEW_THREAD, Schedulers.newThread());
        sSchedulersMapper.put(COMPUTATION, Schedulers.computation());
        sSchedulersMapper.put(IMMEDIATE, Schedulers.trampoline());
        sSchedulersMapper.put(IO, Schedulers.io());
        sSchedulersMapper.put(TEST, new TestScheduler());
        sSchedulersMapper.put(SINGLE, Schedulers.single());
        sSchedulersMapper.put(MAIN_THREAD, AndroidSchedulers.mainThread());
    }

    public static Scheduler getScheduler(@Theme String key) {
        return sSchedulersMapper.get(key);
    }
}
