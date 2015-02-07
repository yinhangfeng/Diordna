package com.yinhangfeng.diordna;

import android.view.View;

/**
 * Created by yhf on 2015/2/7.
 */
public class U {

    public static String getModeStr(int measureSpec) {
        switch(View.MeasureSpec.getMode(measureSpec)) {
        case View.MeasureSpec.UNSPECIFIED:
            return "UNSPECIFIED";
        case View.MeasureSpec.EXACTLY:
            return "EXACTLY";
        default:
            return "AT_MOST";
        }
    }

    public static String getOnMeasureStr(String msg, int widthMeasureSpec, int heightMeasureSpec) {
        StringBuilder sb = new StringBuilder(msg);
        sb.append("widthMeasureSpec mode:")
                .append(getModeStr(widthMeasureSpec))
                .append(" size:")
                .append(View.MeasureSpec.getSize(widthMeasureSpec))
                .append("  heightMeasureSpec mode:")
                .append(getModeStr(heightMeasureSpec))
                .append(" size:")
                .append(View.MeasureSpec.getSize(heightMeasureSpec));
        return sb.toString();
    }
}
