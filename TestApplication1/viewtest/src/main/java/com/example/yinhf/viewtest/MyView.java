package com.example.yinhf.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yinhf on 15/12/30.
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    private long lastOnDrawTime;
    private long lastOnDrawTimeSec;
    private int onDrawCount;
    private Paint paint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    /**
     * onDraw调用最大频率为每秒60帧,多个invalidate调用会被合并
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float start = 0;
        float end = 270;
        boolean clockwise = false;

        float sweep = end - start;
        if (clockwise) {
            if (sweep < 0) {
                sweep = sweep % 360 + 360;
            }
        } else {
            if (sweep > 0) {
                sweep = sweep % 360 - 360;
            }
        }

        Path path = new Path();
        path.addArc(new RectF(100, 100, 500, 500), start, sweep);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        Paint textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.RED);
        canvas.drawText("xxx\naaa", 300, 300, textPaint);

//        canvas.drawCircle(50, 50, 50, paint);
//
//        ++onDrawCount;
//        long curTime = SystemClock.elapsedRealtime();
//        Log.i(TAG, "onDraw: frame dt=" + (curTime - lastOnDrawTime));
//        lastOnDrawTime = curTime;
//        if(lastOnDrawTimeSec == 0) {
//            lastOnDrawTimeSec = curTime;
//        } else {
//            long dt = curTime - lastOnDrawTimeSec;
//            if(dt >= 1000) {
//                Log.i(TAG, "onDraw: dt=" + dt + " onDrawCount=" + onDrawCount);
//                lastOnDrawTimeSec = curTime;
//                onDrawCount = 0;
//            }
//        }
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                invalidate();
//            }
//        }, 50);
        //ViewCompat.postInvalidateOnAnimation(this);
    }
}
