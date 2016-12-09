package com.example.lib.util;

import android.content.res.Resources;

public class DisplayUtils {

    public static int dp2Px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2Dp(float px) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2Sp(float pixelValue) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pixelValue / scaledDensity + 0.5f);
    }

    public static int sp2Px(float spValue) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity);
    }
}
