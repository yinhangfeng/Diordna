package com.yinhangfeng.viewpagertest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by yhf on 2015/8/17.
 */
public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";

    public MyViewPager(Context context) {
        super(context);
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //setClipChildren(false);
        //setStaticTransformationsEnabled(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.e(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }
}
