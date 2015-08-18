package com.example.yhf.webviewtest;

import android.webkit.CookieManager;

import com.example.yhf.webviewtest.util.L;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yhf on 2015/8/9.
 */
public class OKHttpProvider {

    private OKHttpProvider() {
    }

    private static OkHttpClient okHttpClient;

    public static void init() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(25 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(25 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.setCookieHandler(new OKCookieHandler());
    }

    public static OkHttpClient getInstance() {
        return okHttpClient;
    }

    private static class OKCookieHandler extends CookieHandler {

        @Override
        public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
            L.i("OKCookieHandler", "get uri=", uri, " getHost=", uri.getHost(), " requestHeaders=", requestHeaders);
            String host = uri.getHost();
            String cookieStr = CookieManager.getInstance().getCookie(host);
            L.i("OKCookieHandler", "get cookieStr=", cookieStr);

            return Collections.singletonMap("Cookie", Collections.singletonList(cookieStr));
        }

        @Override
        public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
            L.i("OKCookieHandler", "put responseHeaders:", responseHeaders);
        }
    }
}
