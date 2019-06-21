package com.heyongrui.base.assist;

import android.app.Activity;
import android.os.Build;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by lambert on 2018/3/5.
 */

public final class AppManager {
    /**
     * 是否使用桥接模式
     **/
    private boolean isBridge = false;
    /**
     * AppManager管理activity的委托类
     **/
    private AppManagerDelegate mDelegate;
    /**
     * 维护activity的栈结构
     **/
    private Stack<Activity> mActivityStack;
    private static volatile AppManager sInstance;

    /**
     * 隐藏构造器
     *
     * @param isBridge 是否开启桥接模式
     */
    private AppManager(boolean isBridge) {
        this.isBridge = isBridge;
        mDelegate = AppManagerDelegate.getInstance();
    }

    /**
     * 单例
     *
     * @return 返回AppManager的单例
     */
    public static AppManager getInstance() {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager(true);
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity activity实例
     */
    public void addActivity(Activity activity) {
        if (isBridge) {
            mDelegate.addActivity(activity);
        } else {
            if (mActivityStack == null) {
                mActivityStack = new Stack<>();
            }
            mActivityStack.add(activity);
        }
    }

    /**
     * 从堆栈移除Activity
     *
     * @param activity activity实例
     */
    public void removeActivity(Activity activity) {
        if (isBridge) {
            mDelegate.removeActivity(activity);
        } else {
            if (mActivityStack == null) {
                mActivityStack = new Stack<>();
            }
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return 当前（栈顶）activity
     */
    public Activity currentActivity() {
        if (isBridge) {
            return mDelegate.currentActivity();
        } else {
            if (mActivityStack != null && !mActivityStack.isEmpty()) {
                return mActivityStack.lastElement();
            }
            return null;
        }
    }

    /**
     * 结束除当前activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    public void finishOtherActivity(Activity activity) {
        if (isBridge) {
            mDelegate.finishOtherActivity(activity);
        } else {
            if (mActivityStack != null) {
                for (Iterator<Activity> it = mActivityStack.iterator(); it.hasNext(); ) {
                    Activity temp = it.next();
                    if (temp != null && temp != activity) {
                        finishActivity(temp);
                    }
                }
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
        if (isBridge) {
            mDelegate.finishOtherActivity(cls);
        } else {
            if (mActivityStack != null) {
                for (Iterator<Activity> it = mActivityStack.iterator(); it.hasNext(); ) {
                    Activity activity = it.next();
                    if (!activity.getClass().equals(cls)) {
                        finishActivity(activity);
                    }
                }
            }
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (isBridge) {
            mDelegate.finishActivity();
        } else {
            if (mActivityStack != null && !mActivityStack.isEmpty()) {
                Activity activity = mActivityStack.lastElement();
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的activity实例
     */
    public void finishActivity(Activity activity) {
        if (isBridge) {
            mDelegate.finishActivity(activity);
        } else {
            if (activity != null) {
                if (mActivityStack != null && mActivityStack.contains(activity)) {// 兼容未使用AppManager管理的实例
                    mActivityStack.remove(activity);
                }
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的所有Activity
     *
     * @param cls 指定的类的class
     */
    public void finishActivity(Class<?> cls) {
        if (isBridge) {
            mDelegate.finishActivity(cls);
        } else {
            if (mActivityStack != null) {
                for (Iterator<Activity> it = mActivityStack.iterator(); it.hasNext(); ) {
                    Activity activity = it.next();
                    if (activity.getClass().equals(cls)) {
                        finishActivity(activity);
                    }
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (isBridge) {
            mDelegate.finishAllActivity();
        } else {
            if (mActivityStack != null) {
                for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                    if (null != mActivityStack.get(i)) {
                        mActivityStack.get(i).finish();
                    }
                }
                mActivityStack.clear();
            }
        }
    }

    /**
     * 判断特定Activity是否被销毁
     */
    public boolean isDestroy(Class<?> cls) {
        boolean isDestroy = true;
        if (isBridge) {
            isDestroy = mDelegate.isDestroy(cls);
        } else {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        isDestroy = activity.isDestroyed() || activity.isFinishing();
                    } else {
                        isDestroy = activity.isFinishing();
                    }
                }
            }
        }
        return isDestroy;
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        if (isBridge) {
            mDelegate.exitApp();
        } else {
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
}