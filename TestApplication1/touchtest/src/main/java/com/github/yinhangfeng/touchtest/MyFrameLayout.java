package com.github.yinhangfeng.touchtest;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by yinhf on 2017/5/18.
 */

public class MyFrameLayout extends FrameLayout {
    private static final String TAG = "MyFrameLayout";
    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: action" + MotionEvent.actionToString(ev.getAction()) + " x:" + ev.getX() + " y:" + ev.getY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: action" + MotionEvent.actionToString(ev.getAction()) + " x:" + ev.getX() + " y:" + ev.getY());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent: action" + MotionEvent.actionToString(ev.getAction()) + " x:" + ev.getX() + " y:" + ev.getY());
        return true;
    }
}
