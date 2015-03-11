package com.yinhangfeng.applicationtest;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yhf on 2015/3/6.
 */
public class App extends Application {
    static final String TAG = "App";

    private int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i(TAG, "onActivityCreated activity=" + activity);
                ++activityCount;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i(TAG, "onActivityStarted activity=" + activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, "onActivityResumed activity=" + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, "onActivityPaused activity=" + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i(TAG, "onActivityStopped activity=" + activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i(TAG, "onActivitySaveInstanceState activity=" + activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i(TAG, "onActivityDestroyed activity=" + activity);
                --activityCount;
                if(activityCount == 0) {
                    Toast.makeText(App.this, "00000", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, "onTrimMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
    }

}
