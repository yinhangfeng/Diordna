package com.example.yhf.webviewtest.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class MiscUtils {

    /**
     * Log进程id与进程名
     */
    public static void LogCurProcessInfo(Context context) {
        int pid = android.os.Process.myPid();
        String packageName = context.getPackageName();
        StringBuilder sb = new StringBuilder();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        sb.append("进程列表=====================================\n");
        for(ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if(appProcess.pid == pid) {
                sb.append("当前进程>>>>> ");
            } else if(appProcess.processName.contains(packageName)) {
                sb.append("当前包>>>>> ");
            }
            sb.append("Process pid=").append(appProcess.pid).append(" | processName=").append(appProcess.processName).append('\n');
        }
        sb.append("进程列表结束====================================");
        Log.i("LogCurProcessInfo", sb.toString());
    }

    /**
     * 获取当前进程名
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if(appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断当前进程是否为默认进程(跟包名相同)
     */
    public static boolean isDefaultProcess(Context context) {
        String packageName = context.getPackageName();
        String curProcessName = getCurProcessName(context);
        Log.i("MiscUtils", "isDefaultProcess packageName=" + packageName + "  |  curProcessName=" + curProcessName);
        return packageName.equalsIgnoreCase(curProcessName);
    }
}
