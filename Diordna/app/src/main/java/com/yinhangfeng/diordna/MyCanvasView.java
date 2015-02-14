package com.yinhangfeng.diordna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;

import java.lang.reflect.Field;

/**
 * Created by yhf on 2015/2/7.
 */
public class MyCanvasView extends View {
    private static int LAST_ID = 0;

    private final int id = LAST_ID++;
    private final String TAG = MyCanvasView.class.getSimpleName() + id;
    private int number;

    public MyCanvasView(Context context) {
        super(context);
        init();
    }

    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //setWillNotCacheDrawing(true);
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
        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(160);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(id + ":" + Integer.toString(number++), getWidth() / 2, getHeight() / 2, textPaint);
        canvas.drawText("X", getWidth(), getHeight() / 2 + 200, textPaint);
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
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent event=" + event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void myOffsetLeftAndRight(int offset) {
        try {
            Field fieldmLeft = View.class.getDeclaredField("mLeft");
            Field fieldmRight = View.class.getDeclaredField("mRight");
            fieldmLeft.set(this, getLeft() + offset);
            fieldmRight.set(this, getRight() + offset);
        } catch(Exception e) {
            e.printStackTrace();
        }
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

}
