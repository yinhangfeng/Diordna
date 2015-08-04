package com.example.commonlibrary;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DisplayUtils {

    private static DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();

    public static int dp2px(float dp) {
        return (int) (dp * displayMetrics.density + 0.5f);
    }

    public static int px2dp(float px) {
        return (int) (px / displayMetrics.density + 0.5f);
    }

    public static int px2sp(float pixelValue) {
        return (int) (pixelValue / displayMetrics.scaledDensity + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * displayMetrics.scaledDensity + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }
}
