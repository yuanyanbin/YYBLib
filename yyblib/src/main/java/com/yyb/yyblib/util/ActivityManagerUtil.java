package com.yyb.yyblib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

public class ActivityManagerUtil {
    private static ActivityManagerUtil instance;
    //activity栈
    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 单例模式
     *
     * @return 单例
     */

    public static ActivityManagerUtil getInstance() {
        if (instance == null) {
            instance = new ActivityManagerUtil();
        }
        return instance;
    }

    /**
     * 把一个activity压入栈中
     *
     * @param actvity activity
     */
    public void pushOneActivity(Activity actvity) {
        activityStack.add(actvity);
    }


    /**
     * 移除一个activity
     *
     * @param activity activity
     */
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
    }

    /**
     * 获取栈顶的activity，先进后出原则
     *
     * @return 栈顶的activity
     */
    public Activity getLastActivity() {
        return activityStack.isEmpty() ? null : activityStack.lastElement();
    }

    /**
     * 结束指定的Activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls 指定的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        try {
            for (int i = 0; i < activityStack.size(); i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 退出应用程序
     */
    public void appExit(Context context, Class<? extends Service> clazz) {
        try {
            //结束WebService
            Intent i=new Intent(context, clazz);
            context.stopService(i);
            finishAllActivity();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断哪个activity是否在前台
     * @return
     */
    public static boolean isActivityTop(Context context, Class cls){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }

    /**
     * 获取返回栈里面activity个数
     * @return
     */
    public int getActivityStackSize() {
        return activityStack.size();
    }
}
