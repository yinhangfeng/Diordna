package com.example.yhf.webviewtest.crosswalk;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import org.xwalk.core.XWalkView;

/**
 * Created by yinhf on 16/9/20.
 */
public class MyXWalkView extends XWalkView {
    private static final String TAG = "MyXWalkView";

    public MyXWalkView(Context context) {
        super(context);
    }

    public MyXWalkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyXWalkView(Context context, Activity activity) {
        super(context, activity);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.d(TAG, "onScrollChanged() called with: " + "l = [" + l + "], t = [" + t + "], oldl = [" + oldl + "], oldt = [" + oldt + "]");
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
