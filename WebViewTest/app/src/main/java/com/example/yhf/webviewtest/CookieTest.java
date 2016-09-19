package com.example.yhf.webviewtest;

import android.util.Log;
import android.webkit.CookieManager;

import java.net.URLEncoder;

/**
 * Created by yinhf on 2015/12/8.
 */
public class CookieTest {
    private static final String TAG = "CookieTest";

    public static void test() {
        getCookie("172.18.255.66");
        getCookie("//172.18.255.66");
        getCookie("172.18.255.66:8080");
        getCookie("http://172.18.255.66/");
        getCookie("172.18.255.66/");
        getCookie("https://172.18.255.66/");
        getCookie("https://172.18.255.66");
        getCookie("172.18.255.66/aaa");
        getCookie("172.18.255.66/aaa/");
    }

    public static void getCookie(String url) {
        String cookie = CookieManager.getInstance().getCookie(url);
        Log.i(TAG, "getCookie url=" + url + " cookie= " + cookie);
    }

    public static void setCookie(String url, String value) {
        Log.i(TAG, "setCookie() called with " + "url = " + url + ", value = " + value);
        CookieManager.getInstance().setCookie(url, value);
    }

    public static void removeCookie(String url, String name) {
        Log.d(TAG, "removeCookie() called with " + "url = " + url);
        CookieManager.getInstance().setCookie(url, name + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT");
    }
}
