package com.heyongrui.network.core;

/**
 * Created by Brian Wu on 2016/12/15.
 */
public class CoreApiException extends Throwable {
    /*错误码*/
    private int code;
    /*显示的信息*/
    private String displayMessage;

    public CoreApiException(Throwable e) {
        super(e);
    }

    public CoreApiException(int code, String showMsg) {
        setCode(code);
        setDisplayMessage(showMsg);
    }

    public CoreApiException(Throwable cause, int code, String showMsg) {
        super(showMsg, cause);
        setCode(code);
        setDisplayMessage(showMsg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
