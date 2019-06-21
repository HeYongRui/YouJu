package com.heyongrui.base.rxbus;


import androidx.annotation.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RxBus 注解类
 * 通过注解某个方法，使该方法响应 RxBus 事件
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RxBusReact {
    @NonNull Class clazz();                                     // 事件实体的 Class对象

    String tag() default RxBus.DEFAULT_TAG;                     // 事件的标记

    @RxBusScheduler.Theme
    String subscribeOn() default RxBusScheduler.IMMEDIATE;      // 事件发布所在的线程

    @RxBusScheduler.Theme
    String observeOn() default RxBusScheduler.IMMEDIATE;        // 事件响应所在的线程
}
