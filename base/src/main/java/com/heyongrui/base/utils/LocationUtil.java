package com.heyongrui.base.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class LocationUtil {

    //    private volatile static LocationUtil uniqueInstance;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mLocation;
    private Context mContext;
    private String TAG;

    public LocationUtil(@NonNull Context context) {
        TAG = this.getClass().getSimpleName();
        mContext = context;
        init();
    }

//    //采用Double CheckLock(DCL)实现单例
//    public static LocationUtil getInstance(Context context) {
//        if (uniqueInstance == null) {
//            synchronized (LocationUtil.class) {
//                if (uniqueInstance == null) {
//                    uniqueInstance = new LocationUtil(context);
//                }
//            }
//        }
//        return uniqueInstance;
//    }

    //判断用户选择的定位类型
    private void init() {
        //获取位置管理器
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //获取已经启用的位置提供器
//        List<String> enableProviderList = mLocationManager.getProviders(true);
//        if (null != enableProviderList) {
//            for (String provider : enableProviderList) {
//                if (TextUtils.equals(LocationManager.NETWORK_PROVIDER, provider)) {
//                    mLocationProvider = LocationManager.NETWORK_PROVIDER;
//                } else if (TextUtils.equals(LocationManager.GPS_PROVIDER, provider)) {
//                    mLocationProvider = LocationManager.GPS_PROVIDER;
//                    break;
//                }
//            }
//        }
//
//        if (null == mLocationProvider) {
//            Log.i(TAG, "没有可用的位置提供器");
//            return;
//        }
//
//        if (TextUtils.equals(LocationManager.NETWORK_PROVIDER, mLocationProvider)) {
//            Log.i(TAG, "是网络定位");
//        } else if (TextUtils.equals(LocationManager.GPS_PROVIDER, mLocationProvider)) {
//            Log.i(TAG, "是GPS定位");
//        }

//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        //获取上次的位置，一般第一次运行，此值为null
        getLastKnownLocation();
//        Location lastKnownLocation = mLocationManager.getLastKnownLocation(mLocationProvider);
//        if (lastKnownLocation != null) {
//            setLocation(lastKnownLocation);
//        }
//        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
//        mLocationManager.requestLocationUpdates(mLocationProvider, 0, 0, locationListener);
    }

    private void setLocation(Location location) {
        this.mLocation = location;
        if (null != location) {
            Log.d(TAG, "纬度：" + location.getLatitude() + "经度：" + location.getLongitude());
        }
    }

    public Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return mLocation;
        }

        List<String> enableProviderList = mLocationManager.getProviders(true);
        Location bestLocation = null;
        if (null != enableProviderList) {
            for (String provider : enableProviderList) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
        }
        if (null != bestLocation) {
            setLocation(bestLocation);
            return bestLocation;
        } else {
            return mLocation;
        }
    }

    public void addLocationListener() {
        addLocationListener(null);
    }

    public void addLocationListener(LocationListener locationListener) {
        mLocationListener = null != locationListener ? locationListener : new LocationListener() {
            /**
             * 当某个位置提供者的状态发生改变时
             */
            @Override
            public void onStatusChanged(String provider, int status, Bundle arg2) {

            }

            /**
             * 某个设备打开时
             */
            @Override
            public void onProviderEnabled(String provider) {

            }

            /**
             * 某个设备关闭时
             */
            @Override
            public void onProviderDisabled(String provider) {

            }

            /**
             * 手机位置发生变动
             */
            @Override
            public void onLocationChanged(Location location) {
                location.getAccuracy();//精确度
                setLocation(location);
            }
        };

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<String> enableProviderList = mLocationManager.getProviders(true);
        if (null != enableProviderList) {
            for (String provider : enableProviderList) {
                mLocationManager.requestLocationUpdates(provider, 0, 0, locationListener);
            }
        }
    }

    // 移除定位监听
    public void removeLocationUpdatesListener() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (null != mLocationManager && null != mLocationListener) {
//            uniqueInstance = null;
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     */
    public static boolean isOpenGps(@NonNull Context context) {
        if (null == context) return false;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("isOpenGps", "Gps: " + gps + "\tNetWork: " + network);
        if (gps || network) {
            return true;
        }
        return false;
    }
}
