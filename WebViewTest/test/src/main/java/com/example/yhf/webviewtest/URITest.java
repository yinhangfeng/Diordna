package com.example.yhf.webviewtest;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by yinhf on 2015/12/8.
 */
public class URITest {
    private static final String TAG = "URITest";

    public static void test(String uriStr) {
        //"https://wx.qiangxianpai.com:8080/User/Index/index?aaa=1#aaa"
        try {
            URI uri = new URI(uriStr);
            Log.i(TAG, "test8 getAuthority=" + uri.getAuthority() + "\ngetFragment=" + uri.getFragment() + "\ngetHost=" + uri.getHost() + "\ngetPath=" + uri.getPath() + "\ngetQuery=" + uri.getQuery() + "\ngetScheme=" + uri.getScheme() + "\ngetSchemeSpecificPart=" + uri.getSchemeSpecificPart() + "\ngetUserInfo=" + uri.getUserInfo() + "\ngetPort=" + uri.getPort());
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
