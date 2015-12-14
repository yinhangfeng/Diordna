package com.example.yhf.webviewtest;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.example.yhf.webviewtest.util.L;
import com.example.yhf.webviewtest.util.T;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dmd on 2015/1/6.
 */
public class App extends Application {
    private static final String TAG = "App";

    private static RefWatcher refWatcher;

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //refWatcher = LeakCanary.install(this);
        refWatcher = RefWatcher.DISABLED;

        T.init(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        OKHttpProvider.init();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("LABApplication", "xxxxxxxxxxxxxxxxx", ex);
            }
        });
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
