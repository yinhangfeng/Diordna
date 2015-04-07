package com.example.fragmenttest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class MyFrameLayout extends FrameLayout {
	private static final String TAG = "MyFrameLayout";
	
	public MyFrameLayout(Context context) {
		super(context);
	}

	public MyFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//Log.v(TAG, "dispatchTouchEvent");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//Log.d(TAG, "onInterceptTouchEvent");
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.i(TAG, "onTouchEvent");
		return true;
	}

}
