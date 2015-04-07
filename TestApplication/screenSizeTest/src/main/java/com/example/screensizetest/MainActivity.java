package com.example.screensizetest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		Log.i(TAG, "getResources().getDisplayMetrics()=" + displayMetrics);
		
		Display display = getWindowManager().getDefaultDisplay();
		Log.i(TAG, "getWindowManager().getDefaultDisplay() display=" + display);
		Log.i(TAG, "getWindowManager().getDefaultDisplay() display.getWidth()=" + display.getWidth() + " display.getHeight()=" + display.getHeight());
		Point p = new Point();
		display.getSize(p);
		Log.i(TAG, "getWindowManager().getDefaultDisplay().getSize(p) p.x=" + p.x + " p.y=" + p.y);
		
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		Log.i(TAG, "getWindowManager().getDefaultDisplay().getMetrics(outMetrics) outMetrics=" + outMetrics);
		
		int wDp = (int) (displayMetrics.widthPixels / displayMetrics.density);
		Log.i(TAG, "wdp=" + wDp);
		float dimension = this.getResources().getDimension(R.dimen.picture_marker_symbol_width);
		Log.i(TAG, "dimension=" + dimension);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.i(TAG, "onConfigurationChanged newConfig=" + newConfig);
	}
	
	public void orientationTestOnClick(View v) {
		Log.i(TAG, "orientationTestOnClick");
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		if (width > height) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}
}
