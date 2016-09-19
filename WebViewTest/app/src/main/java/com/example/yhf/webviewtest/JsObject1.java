package com.example.yhf.webviewtest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by yhf on 2015/6/4.
 */
public class JsObject1 {
    private static final String TAG = "JsObject1";
    private Handler handler = new Handler(Looper.getMainLooper());

    private MainActivity mainActivity;

    public JsObject1(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @JavascriptInterface
    public void toast() {
        Log.i(TAG, "toast Thread.currentThread().getId()=" + Thread.currentThread().getId());
        Toast.makeText(mainActivity, "test", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void xxx() {

    }
}
