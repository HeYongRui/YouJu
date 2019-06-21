package com.heyongrui.base.assist;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * Created by lambert on 2017/11/7.
 */

public interface NetStateChangeObserver {
    void onNetDisconnected();

    void onNetConnected(NetworkUtils.NetworkType networkType);
}
