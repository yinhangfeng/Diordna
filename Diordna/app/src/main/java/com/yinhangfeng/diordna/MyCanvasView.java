package com.yinhangfeng.diordna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yhf on 2015/2/7.
 */
public class MyCanvasView extends View {
    static final String TAG = "MyCanvasView";

    private int id;

    public MyCanvasView(Context context) {
        super(context);
    }

    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Log.e(TAG, "onDraw");
        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);
        canvas.drawText(Integer.toString(id++), 50, 100, textPaint);
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

}
