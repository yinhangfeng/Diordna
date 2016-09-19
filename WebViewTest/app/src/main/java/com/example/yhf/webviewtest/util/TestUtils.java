package com.example.yhf.webviewtest.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.File;

/**
 * Created by yinhf on 16/9/19.
 */
public class TestUtils {

    public static final File TEST_ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "TEST");

    private static CookieManager cookieManager;

    public static CookieManager getCookieManager(Context context) {
        if (cookieManager == null) {
            if (Build.VERSION.SDK_INT < 21) {
                // This is to work around a bug where CookieManager may fail to instantiate if
                // CookieSyncManager has never been created. Note that the sync() may not be required but is
                // here of legacy reasons.
                CookieSyncManager syncManager = CookieSyncManager.createInstance(context.getApplicationContext());
                syncManager.sync();
            }
            cookieManager = CookieManager.getInstance();
        }
        return cookieManager;
    }
}
