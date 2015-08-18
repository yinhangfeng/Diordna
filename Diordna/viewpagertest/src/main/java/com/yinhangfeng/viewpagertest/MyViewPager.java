package com.yinhangfeng.viewpagertest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by yhf on 2015/8/17.
 */
public class MyViewPager extends ViewPager {

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
}
