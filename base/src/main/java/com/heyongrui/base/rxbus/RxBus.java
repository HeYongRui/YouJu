package com.heyongrui.base.rxbus;


import androidx.collection.ArrayMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;


/**
 * RxBus 基于 RxJava 设计的用于组件间通讯的事件总线。
 */
public class RxBus {
    public static final String DEFAULT_TAG = "default";
    private static RxBus sBus;
    private PublishSubject<RxBusEvent> mSubject;
    private PublishSubject<RxBusEvent> mStickySubject;
    private ArrayMap<String, CompositeDisposable> mSubscribeMapper;

    private RxBus() {
        mSubject = PublishSubject.create();
        mStickySubject = PublishSubject.create();
        mSubscribeMapper = new ArrayMap<String, CompositeDisposable>();
    }

    /**
     * 使用双重检查锁重新定义单例生成RxBus对象
     *
     * @return
     */
    public static RxBus getDefault() {
        if (sBus == null) {
            synchronized (RxBus.class) {
                if (sBus == null)
                    sBus = new RxBus();
            }
        }
        return sBus;
    }

    /**
     * 发送一个事件，并标记该事件为 tag。只有指定为 tag 的地方才能响应该 事件。
     *
     * @param event
     * @param tag
     */
    public void post(Object event, String tag) {
        if (mSubject != null) {
            mSubject.onNext(new RxBusEvent(event, tag));
        }
    }

    /**
     * 发送一个事件，该事件的标记为默认的tag
     *
     * @param event
     */
    public void post(Object event) {
        post(event, DEFAULT_TAG);
    }

    /**
     * 发送一个 黏性事件，并标记该事件为 tag。同理，只有标记为 tag 的方法才能响应该 事件。
     * 所谓 黏性事件 是指即使在该事件发送后才进行 注册RxBus 的组件，也能接收到该 事件
     * 整个 RxBus 只能维持一个 黏性事件，最后发送的黏性事件会取代前面的事件。
     *
     * @param event
     */
    public void postSticky(Object event, String tag) {
        if (mStickySubject != null) {
            mStickySubject.onNext(new RxBusEvent(event, tag));
        }
    }

    /**
     * 发送一个默认tag的黏性事件
     *
     * @param event
     */
    public void postSticky(Object event) {
        postSticky(event, DEFAULT_TAG);
    }

    /**
     * 取消 黏性事件
     * 黏性事件 可能存在着一些未知隐患，请谨慎使用。
     * 建议发布黏性事件后，需要在合适的时机取消该 黏性事件
     */
    public void cancelStickyEvent() {
        mStickySubject = PublishSubject.create();
    }

    /**
     * 返回 事件发布者。
     * 熟悉 RxJava 的开发者可以通过本方法获取到 事件发布者，自定义事件响应策略。
     *
     * @return
     */
    public Observable<RxBusEvent> getObservable() {
        return mSubject.toSerialized().mergeWith(mStickySubject.toSerialized());
    }

    /**
     * 注册 RxBus
     *
     * @param object
     */
    public void register(final Object object) {
        CompositeDisposable subscriptions = new CompositeDisposable();
        for (final Method method : object.getClass().getDeclaredMethods()) {
            RxBusReact accept = method.getAnnotation(RxBusReact.class);
            if (accept != null) {
                final Class clazz = accept.clazz();
                final String tag = accept.tag();
                Scheduler observeScheduler = RxBusScheduler.getScheduler(accept.observeOn());
                Scheduler subscribeScheduler = RxBusScheduler.getScheduler(accept.subscribeOn());
                Disposable subscription = getObservable()
                        .subscribeOn(subscribeScheduler)
                        .filter(new Predicate<RxBusEvent>() {
                            @Override
                            public boolean test(RxBusEvent rxBusEvent) throws Exception {
                                return clazz.equals(rxBusEvent.getObj().getClass()) &&
                                        tag.equals(rxBusEvent.getTag());
                            }
                        })
                        .observeOn(observeScheduler)
                        .subscribe(new Consumer<RxBusEvent>() {
                            @Override
                            public void accept(RxBusEvent rxBusEvent) throws Exception {
                                try {
                                    method.invoke(object, rxBusEvent.getObj(), rxBusEvent.getTag());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                subscriptions.add(subscription);
            }
        }
        unregister(object);
        mSubscribeMapper.put(object.getClass().getName(), subscriptions);
    }

    /**
     * 反注册 RxBus
     *
     * @param object
     */
    public void unregister(Object object) {
        String key = object.getClass().getName();
        CompositeDisposable subscriptions = mSubscribeMapper.get(key);
        if (subscriptions != null && !subscriptions.isDisposed()) {
            subscriptions.clear();
            mSubscribeMapper.remove(key);
        }
    }
}
