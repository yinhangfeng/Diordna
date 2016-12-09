package com.example.yhf.webviewtest;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.webkit.WebView;

import com.example.lib.util.L;
import com.squareup.leakcanary.RefWatcher;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                Log.e("LABApplication", "xxxxxxxxxxxxxxxxx", ex);
//            }
//        });

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
