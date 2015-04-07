package com.example.yhf.webviewtest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dmd on 2015/1/6.
 */
public class JsObject {
    private static final String TAG = "JsObject";
    private Handler handler = new Handler(Looper.getMainLooper());

    private MainActivity mainActivity;

    public JsObject(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void toast() {
        Log.i(TAG, "toast Thread.currentThread().getId()=" + Thread.currentThread().getId());
        Toast.makeText(mainActivity, "test", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void setText(final String text) {
        LOGI("setText", text);
        handler.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) mainActivity.findViewById(R.id.text_view)).setText(text);
            }
        });
    }

    @JavascriptInterface
    public int basicData(byte _byte, char _char, int _int, long _long, float _float, double _double, String _String) {
        LOGI("basicData", "_byte=" + _byte
                + " _char=" + _char
                + " _int=" + _int
                + " _long=" + _long
                + " _float=" + _float
                + " _double=" + _double
                + " _String=" + _String);
        return _int;
    }

    @JavascriptInterface
    public void aaa(int a) {
        LOGI("aaa", "int a=" + a);
    }

    @JavascriptInterface
    public void aaa(String a) {
        LOGI("aaa", "String a=" + a);
    }

    @JavascriptInterface
    public void wrapData(Integer a, Double b) {
        LOGI("wrapData", "a=" + a + " b=" + b);
    }

    @JavascriptInterface
    public String toString() {
        return "injectedObject";
    }

    private static void LOGI(String method, String text) {
        Log.i(TAG, method + " ThreadId=" + Thread.currentThread().getId() + " " + text);
    }
}
