package com.example.yhf.webviewtest;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.example.yhf.webviewtest.util.L;
import com.example.yhf.webviewtest.util.T;

/**
 * Created by dmd on 2015/1/6.
 */
public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        T.init(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.d(TAG, "onConfigurationChanged() called with newConfig = [", newConfig, "]");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        L.i(TAG, "onTerminate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        L.d(TAG, "onTrimMemory() called with level = [", level, "]");
    }
}
