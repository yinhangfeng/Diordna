package com.example.yhf.webviewtest;

import android.app.Application;
import android.util.Log;

/**
 * Created by dmd on 2015/1/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("App", "onCreate");
        Log.d("App", "onCreate");
        Log.i("App", "onCreate");
        Log.w("App", "onCreate");
        Log.e("App", "onCreate");
    }
}
