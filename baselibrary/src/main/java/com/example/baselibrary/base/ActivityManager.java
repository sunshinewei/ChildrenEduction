package com.example.baselibrary.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class ActivityManager {
    private static ActivityManager mActivityManager;
    private Stack<Activity> mActivityStack;

    private ActivityManager() {

    }

    public static  ActivityManager getInstance() {
        if (mActivityManager == null) {
            synchronized (ActivityManager.class){
                if (mActivityManager==null){
                    mActivityManager = new ActivityManager();
                }
            }
        }
        return mActivityManager;
    }

    /**
     * 实例化一个Activity，将这个Activity加入到栈中
     *
     * @param mActivity
     */
    public void addActivity(Activity mActivity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(mActivity);
    }

    /**
     * 移除Activity到堆外
     */
    public void removeActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getTopActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 结束栈顶Activity
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

}
