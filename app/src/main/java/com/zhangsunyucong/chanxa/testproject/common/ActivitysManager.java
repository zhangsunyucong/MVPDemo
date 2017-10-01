package com.zhangsunyucong.chanxa.testproject.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.util.Log;

import com.zhangsunyucong.chanxa.testproject.App;

import java.util.Stack;

/**
 * Created by hyj on 2017/8/9 0009.
 */

public class ActivitysManager {

    private static ActivitysManager mActivityManager;
    private Stack<Activity> activityStack;

    private ActivitysManager(){
        activityStack = new Stack<Activity>();
    }

    public static ActivitysManager getInstance(){
        if(null == mActivityManager){
            synchronized (ActivityManager.class) {
                if(null == mActivityManager){
                    mActivityManager = new ActivitysManager();
                }
            }
        }
        return mActivityManager;
    }

    public void addActivity(Activity activity){
        if(null != activity){
            activityStack.add(activity);
        }
    }

    public Activity getFirstActivity(){
        if(activityStack.size() <= 0) {
            return null;
        }
        return activityStack.firstElement();
    }

    public Activity getActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(null != activity){
                if(activity.getClass().equals(cls)){
                    return activity;
                }
            }
        }
        return null;
    }

    public Activity currentActivity(){
        return activityStack.lastElement();
    }

    public Class<?> getcurrentClass() {
        if (getStackSize() != 0) {
            Class<?> Cls = activityStack.lastElement().getClass();
            return Cls;
        }
        return null;
    }

    public int getStackSize() {
        if (activityStack != null) {
            return activityStack.size();
        }
        return 0;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if(null != activity){
            finishActivity(activity);
        }
    }

    public void finishActivity(Activity activity){
        if(null != activity){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    public void popActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
                if(null != activity){
                    activityStack.remove(activity);
                }
            }
        }
    }

    public void finishAllActivity(){
        for(int i = 0; i < activityStack.size(); i++){
            if(null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void finishAllActivityButActivity(Class<?> cls) {
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
               continue;
            } else {
                finishActivity(activity);
            }
        }
    }

    public void appExit(){
        try{
            finishAllActivity();
            Application application = App.getApp();

            if(null != application){
                application.onTerminate();
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "退出失败");
        }finally{
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}
