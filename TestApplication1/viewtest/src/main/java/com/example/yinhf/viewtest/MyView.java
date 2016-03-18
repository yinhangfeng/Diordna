package com.example.yinhf.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
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
        canvas.drawCircle(50, 50, 50, paint);

        ++onDrawCount;
        long curTime = SystemClock.elapsedRealtime();
        Log.i(TAG, "onDraw: frame dt=" + (curTime - lastOnDrawTime));
        lastOnDrawTime = curTime;
        if(lastOnDrawTimeSec == 0) {
            lastOnDrawTimeSec = curTime;
        } else {
            long dt = curTime - lastOnDrawTimeSec;
            if(dt >= 1000) {
                Log.i(TAG, "onDraw: dt=" + dt + " onDrawCount=" + onDrawCount);
                lastOnDrawTimeSec = curTime;
                onDrawCount = 0;
            }
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 50);
        //ViewCompat.postInvalidateOnAnimation(this);
    }
}
