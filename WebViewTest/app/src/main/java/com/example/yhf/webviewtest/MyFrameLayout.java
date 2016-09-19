package com.example.yhf.webviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.yhf.webviewtest.util.L;

/**
 * Created by yinhf on 16/7/12.
 */
public class MyFrameLayout extends FrameLayout {
    private static final String TAG = "MyFrameLayout";

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.d(TAG, "onMeasure() called with widthMeasureSpec = ", MeasureSpec.toString(widthMeasureSpec), ", heightMeasureSpec = ", MeasureSpec.toString(heightMeasureSpec), "");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        L.d(TAG, "onLayout() called with changed = ", changed, ", left = ", left, ", top = ", top, ", right = ", right, ", bottom = ", bottom, "");
        super.onLayout(changed, left, top, right, bottom);
    }
}
