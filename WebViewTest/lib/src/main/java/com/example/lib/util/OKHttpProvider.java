package com.example.lib.util;

import okhttp3.OkHttpClient;

/**
 * Created by yhf on 2015/8/9.
 */
public class OKHttpProvider {

    private OKHttpProvider() {
    }

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

//    private static class OKCookieHandler extends CookieHandler {
//
//        @Override
//        public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
//            String host = uri.getHost();
//            String url = uri.toString();
//            L.i("OKCookieHandler", "get uri=", uri, " getHost=", host, " url=", url, " requestHeaders=", requestHeaders);
//            String cookieStr = CookieManager.getInstance().getCookie(url);
//            L.i("OKCookieHandler", "get cookieStr=", cookieStr);
//            if(cookieStr == null) {
//                return Collections.emptyMap();
//            }
//            return Collections.singletonMap("Cookie", Collections.singletonList(cookieStr));
//        }
//
//        @Override
//        public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
//            L.i("OKCookieHandler", "put responseHeaders:", responseHeaders);
//        }
//    }
}
