package com.heyongrui.base.assist;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lambert on 2018/3/5.
 */

public final class AppManager {
    private static List<Activity> mActivitys = Collections.synchronizedList(new LinkedList<>());
    private static volatile AppManager sInstance;

    public static AppManager getInstance() {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager();
                }
            }
        }
        return sInstance;
    }

    public List<Activity> getActivitys() {
        return mActivitys;
    }

    /**
     * 添加一个activity到list里
     */
    public void addActivity(Activity activity) {
        if (mActivitys == null) {
            return;
        }
        mActivitys.add(activity);
    }


    /**
     * 从list里删除一个activity
     */
    public void removeActivity(Activity activity) {
        if (mActivitys == null) {
            return;
        }
        mActivitys.remove(activity);
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        List<Activity> activityList = new ArrayList<>();
        for (Activity activity : mActivitys) {
            if (activity != null && activity.getClass().equals(cls)) {
                activityList.add(activity);
            }
        }
        mActivitys.removeAll(activityList);
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 结束指定类名的 Activity 集合
     */
    public void finishActivityCollection(@NonNull List<Class<?>> classList) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        List<Activity> needFinishedActivityList = new ArrayList<>();
        for (int i = 0; i < classList.size(); i++) {
            Class<?> aClass = classList.get(i);
            for (Activity activity : mActivitys) {
                if (activity != null && activity.getClass().equals(aClass)) {
                    if (!needFinishedActivityList.contains(activity)) {
                        needFinishedActivityList.add(activity);
                    }
                }
            }
        }

        mActivitys.removeAll(needFinishedActivityList);
        for (int j = 0; j < needFinishedActivityList.size(); j++) {
            Activity activity = needFinishedActivityList.get(j);
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 结束除这一类activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param cls 不需要结束的activity
     */
    public void finishOtherActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        List<Activity> needFinishedActivityList = new ArrayList<>();

        for (Activity activity : mActivitys) {
            if (activity != null && !activity.getClass().equals(cls)) {
                if (!needFinishedActivityList.contains(activity)) {
                    needFinishedActivityList.add(activity);
                }
            }
        }
        if (!needFinishedActivityList.isEmpty()) {
            mActivitys.removeAll(needFinishedActivityList);
            for (Activity activity : needFinishedActivityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }

        List<Activity> needFinishedActivityList = new ArrayList<>();
        for (int i = 0; i < mActivitys.size(); i++) {
            for (Activity activity : mActivitys) {
                if (activity != null) {
                    if (!needFinishedActivityList.contains(activity)) {
                        needFinishedActivityList.add(activity);
                    }
                }
            }
        }

        mActivitys.removeAll(needFinishedActivityList);
        for (int j = 0; j < needFinishedActivityList.size(); j++) {
            Activity activity = needFinishedActivityList.get(j);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return null;
        }
        return mActivitys.get(mActivitys.size() - 1);
    }


    /**
     * 判断特定Activity是否被销毁
     */
    public boolean isDestroy(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return true;
        }
        boolean isDestroy = true;
        for (Activity activity : mActivitys) {
            if (null != activity && activity.getClass().equals(cls)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroy = activity.isDestroyed() || activity.isFinishing();
                } else {
                    isDestroy = activity.isFinishing();
                }
            }
        }
        return isDestroy;
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
            // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}