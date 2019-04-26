package com.xifan.desktopbadge;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.List;

/**
 * App常用工具
 *
 * @author SJL
 * @date 2016/11/21 21:33
 */
public class AppUtil {
    /**
     * 判断当前应用是否是后台运行
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packname = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0之后getRunningTasks废弃
            //当前程序是否前台运行
            //详见ActivityManager.START_TASK_TO_FRONT(hide),前台任务
            int START_TASK_TO_FRONT = 2;
            Field field = null;
            try {
                field = ActivityManager.RunningAppProcessInfo.class.getField("processState");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfoList) {
                if (runningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    //如果当前是运行在前台的UI
                    try {
                        Integer processState = field.getInt(runningAppProcessInfo);
                        if (START_TASK_TO_FRONT == processState) {
                            packname = runningAppProcessInfo.processName;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            //5.0之前可直接使用getRunningTasks
            //<uses-permission android:name="android.permission.GET_TASKS"/>
            List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(1);
            packname = runningTaskInfoList.get(0).topActivity.getPackageName();
        }
        return !context.getPackageName().equals(packname);
    }
}
