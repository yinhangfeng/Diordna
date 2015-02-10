package com.yinhangfeng.diordna;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yhf on 2015/2/7.
 */
public class MyLinearLayout extends LinearLayout {
    static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, U.getOnMeasureStr("onMeasure before super", widthMeasureSpec, heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure after super");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout before super changed=" + changed + " l=" + l + " t=" + t + " r=" + r + " b=" + b);
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout after super");
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i(TAG, "draw before super");
        super.draw(canvas);
        Log.d(TAG, "draw after super");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i(TAG, "dispatchDraw before super");
        super.dispatchDraw(canvas);
        Log.d(TAG, "dispatchDraw after super");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw before super");
        super.onDraw(canvas);
        Log.d(TAG, "onDraw after super");
    }

    @Override
    public void requestLayout() {
        Log.i(TAG, "requestLayout");
        super.requestLayout();
    }

    @Override
    public void invalidate() {
        Log.i(TAG, "invalidate");
        super.invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i(TAG, "onInterceptTouchEvent event=" + event);
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent event=" + event);
        return true;
        //return super.onTouchEvent(event);
    }
}
