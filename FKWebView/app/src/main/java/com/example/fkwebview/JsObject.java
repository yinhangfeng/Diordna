package com.example.fkwebview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by dmd on 2015/1/6.
 */
public class JsObject {
    private static final String TAG = "JsObject";
    private Handler handler = new Handler(Looper.getMainLooper());

    private byte[] data = new byte[64 * 1024];

    private MainActivity mainActivity;
    private WebView webView;
    private JsObject1 jsObject1;

    public JsObject(MainActivity mainActivity, WebView webView) {
        LOGI("JsObject", "");
        this.mainActivity = mainActivity;
        jsObject1 = new JsObject1(mainActivity);
        this.webView = webView;
    }

    public void toast() {
        Log.i(TAG, "toast Thread.currentThread().getId()=" + Thread.currentThread().getId());
        Toast.makeText(mainActivity, "test", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void setText(final String text) {
        LOGI("setText", text + data);
    }

    @JavascriptInterface
    public int basicData(byte _byte, char _char, int _int, long _long, float _float, double _double, String _String) {
        LOGI("basicData", "_byte=" + _byte + " _char=" + _char + " _int=" + _int + " _long=" + _long + " _float=" + _float + " _double=" + _double + " _String=" + _String);
        return _int;
    }

    @JavascriptInterface
    public void aaa(String a) {
        LOGI("aaa", "String a=" + a);
    }

    @JavascriptInterface
    public void aaa() {
        LOGI("aaa", "xxxxxxxxxxxxxxxxxxxxx");
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

    @JavascriptInterface
    public JsObject1 xxx() {
        LOGI("xxx", "");
        return jsObject1;
    }

    @JavascriptInterface
    public void testLoadUrl() {
        Log.d(TAG, "testLoadUrl() called with " + "");
        webView.loadUrl("javascript:console.log('native testLoadUrl');");
    }
}
