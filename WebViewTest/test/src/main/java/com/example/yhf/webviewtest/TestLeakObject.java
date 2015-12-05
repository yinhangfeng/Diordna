package com.example.yhf.webviewtest;

import android.webkit.JavascriptInterface;

/**
 * Created by yinhf on 2015/10/28.
 */
public class TestLeakObject {

    private int[] data = new int[10 * 1024 * 1024];

    @JavascriptInterface
    public String test(String a) {
        return "xxx-" + a + data;
    }
}
