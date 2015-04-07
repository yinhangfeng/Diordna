package com.example.configchangetest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 1 android:configChanges="orientation|screenSize"
 * 使得在屏幕旋转时只调用onConfigurationChanged 而不重新onCreate
 * 但会使layout-land layout-port values-land values-port无效 始终为最开始layout
 * 2 不设android:configChanges时 不仅重新onCreate 连activity都是新的实例(不管是否有layout-land layout-port values-land values-port)
 *
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	{
		Log.i(TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.i(TAG, "onPostCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.i(TAG, "onConfigurationChanged newConfig=" + newConfig);
		Log.i(TAG, "test=" + getResources().getString(R.string.test));
	}
	
	public void testOnClick(View v) {
		Log.i(TAG, "testOnClick");
	}
	
	
}
