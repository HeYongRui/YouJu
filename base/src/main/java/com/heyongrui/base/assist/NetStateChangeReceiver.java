package com.heyongrui.base.assist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lambert on 2017/11/7.
 */

public class NetStateChangeReceiver extends BroadcastReceiver {

    private static class InstanceHolder {
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();

    public NetStateChangeReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
            notifyObservers(networkType);
        }
    }

    /**
     * 注册网络监听
     */
    public static void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    /**
     * 取消网络监听
     */
    public static void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }

    /**
     * 注册网络变化Observer
     */
    public static void registerObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    public static void unregisterObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (InstanceHolder.INSTANCE.mObservers == null)
            return;
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    /**
     * 通知所有的Observer网络状态变化
     */
    private void notifyObservers(NetworkUtils.NetworkType networkType) {
        if (networkType == NetworkUtils.NetworkType.NETWORK_NO) {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(networkType);
            }
        }
    }

}
