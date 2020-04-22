package com.heyongrui.network.convert;

import java.io.IOException;

/**
 * 2020-04-15 14:29
 * heyongrui
 */
public class CustomIOException extends IOException {

    private int errorCode;
    private String msg;

    public CustomIOException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public CustomIOException(Throwable cause, int errorCode, String msg) {
        super(msg, cause);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
