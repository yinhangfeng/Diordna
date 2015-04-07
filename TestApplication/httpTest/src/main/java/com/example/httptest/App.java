package com.example.httptest;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.os.Environment;
import android.util.Log;

public class App extends Application {

	private static final String TAG = "App";
	public static HttpResponseCache cache;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			File httpCacheDir = new File(
					Environment.getExternalStorageDirectory(),
					"TEST/HttpTest/cache");
			long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
			cache = HttpResponseCache.install(httpCacheDir, httpCacheSize);
		} catch (IOException e) {
			Log.e(TAG, "HTTP response cache installation failed:" + e);
		}
	}

}
