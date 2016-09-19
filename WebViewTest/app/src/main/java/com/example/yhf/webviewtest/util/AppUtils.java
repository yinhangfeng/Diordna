package com.example.yhf.webviewtest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用程序相关工具
 * Created by yhf on 2015/5/26.
 */
public class AppUtils {

    private AppUtils() {

    }

    /**
     * 获取应用程序名称
     * @return null 出错
     */
    public static String getAppName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int labelRes = pi.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取versionCode
     * @return -1 出错
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *  获取versionName
     *  @return null 出错
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
