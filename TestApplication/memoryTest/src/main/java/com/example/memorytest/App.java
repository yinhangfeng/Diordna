package com.example.memorytest;

import android.app.Application;
import android.util.Log;

public class App extends Application {
	private static final String TAG = "App";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.i(TAG, "onLowMemory");
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.i(TAG, "onTerminate");
	}
	
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		Log.i(TAG, "onTrimMemory level=" + level);
	}

}
