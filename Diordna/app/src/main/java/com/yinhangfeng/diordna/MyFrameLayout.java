package com.yinhangfeng.diordna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * Created by yhf on 2015/2/7.
 */
public class MyFrameLayout extends FrameLayout {
    static final String TAG = MyFrameLayout.class.getSimpleName();

    public MyFrameLayout(Context context) {
        super(context);
        init();
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        //setChildrenDrawingOrderEnabled(true);
        //setStaticTransformationsEnabled(true);
        setClipChildren(false);
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
        Rect clipBounds = canvas.getClipBounds();
        Log.i(TAG, "draw before super clipBounds=" + clipBounds);
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
        Log.e(TAG, "onDraw");
        super.onDraw(canvas);
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged w=" + w + " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean isOpaque() {
        boolean isOpaque = super.isOpaque();
        Log.i(TAG, "isOpaque result=" + isOpaque);
        return isOpaque;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        Log.i(TAG, "getChildDrawingOrder childCount=" + childCount + " i=" + i);
        //return super.getChildDrawingOrder(childCount, i);
        return childCount - 1 - i;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Rect clipBounds = canvas.getClipBounds();
        Log.i(TAG, "drawChild clipBounds=" + clipBounds);
        canvas.save();
        canvas.scale(1f, 0.5f);
        if(getChildAt(1) == child) {
            canvas.translate(-1700, 0);
        }
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return ret;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        Log.i(TAG, "getChildStaticTransformation");
        return super.getChildStaticTransformation(child, t);
    }
}
