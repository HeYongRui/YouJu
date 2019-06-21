package com.heyongrui.base.rxbus;

/**
 * RxBus 事件封装类
 * 封装了 事件主体 和 事件标记
 */
public class RxBusEvent {
    private String tag;
    private Object obj;

    public RxBusEvent(Object obj, String tag) {
        this.tag = tag;
        this.obj = obj;
    }

    public String getTag() {
        return tag;
    }

    public Object getObj() {
        return obj;
    }
}
