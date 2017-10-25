package com.jusenr.eg.demo.base;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by riven_chris on 2017/4/13.
 */

public class ActivityManager {
    private final String TAG = this.getClass().getSimpleName();

    private List<Activity> mActivityList;
    private Activity mCurrentActivity;

    public ActivityManager() {
    }

    /**
     * 释放资源
     */
    public void release() {
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
    }

    /**
     * 将在前台的activity保存
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获得当前在前台的activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 返回一个存储所有未销毁的activity的集合
     *
     * @return
     */
    public List<Activity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }


    /**
     * 添加Activity到集合
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (ActivityManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 删除集合里的指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList == null) {
            return;
        }
        synchronized (ActivityManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     *
     * @param location
     */
    public Activity removeActivity(int location) {
        if (mActivityList == null) {
            return null;
        }
        synchronized (ActivityManager.class) {
            if (location > 0 && location < mActivityList.size()) {
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     * 关闭指定activity
     *
     * @param activityClass
     */
    public void finishActivity(Class<?> activityClass) {
        if (mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }


    /**
     * 指定的activity实例是否存活
     *
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(Activity activity) {
        if (mActivityList == null) {
            return false;
        }
        return mActivityList.contains(activity);
    }


    /**
     * 指定的activity class是否存活(一个activity可能有多个实例)
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivityList == null) {
            return false;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 关闭所有activity
     */
    public void finishAll() {
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
            iterator.remove();
        }

    }

    /**
     * 关闭除activityClass之外的所有activity
     */
    public void finishAll(@NonNull Class<?>... activityClass) {
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!Arrays.asList(activityClass).contains(activity.getClass())) {
                activity.finish();
                iterator.remove();
            }
        }
    }

}
