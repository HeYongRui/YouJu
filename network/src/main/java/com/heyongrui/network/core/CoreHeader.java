package com.heyongrui.network.core;

import java.io.Serializable;

/**
 * Created by Brian Wu on 2016/12/16.
 */

public class CoreHeader implements Serializable {

    public static final String KEY_STATUS = "status";
    public static final String KEY_MSG = "msg";

    private int status;
    private String msg;
    private String request;

    public CoreHeader(){

    }

    public CoreHeader(int status, String msg, String request) {
        this.status = status;
        this.msg = msg;
        this.request = request;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
