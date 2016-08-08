package com.example.yhf.statusbartest;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * Created by yinhf on 16/8/1.
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
        Log.i(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + MeasureSpec.toString(widthMeasureSpec) + "], heightMeasureSpec = [" + MeasureSpec.toString(heightMeasureSpec) + "]");
        Log.i(TAG, "onMeasure: xxxxx paddingTop=" + getPaddingTop() + " paddingBottom" + getPaddingBottom());
        setPadding(0, 0, 0, 0);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        //API 20以上 获取键盘 系统栏高度的更好方法
        WindowInsets ret = super.onApplyWindowInsets(insets);
        Log.d(TAG, "onApplyWindowInsets() called with " + "insets = " + insets + " ret=" + ret);
        return ret;
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        //API 20以下 获取键盘 系统栏高度方法
        Log.d(TAG, "fitSystemWindows() called with " + "insets = " + insets + "");
        return super.fitSystemWindows(insets);
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        return super.dispatchApplyWindowInsets(insets);
    }
}
