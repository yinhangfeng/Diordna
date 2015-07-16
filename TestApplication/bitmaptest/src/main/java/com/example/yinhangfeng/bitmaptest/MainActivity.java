package com.example.yinhangfeng.bitmaptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.commonlibrary.BaseTestActivity;
import com.example.commonlibrary.L;


public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    private View testView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testView = findViewById(R.id.test_view);
        webView = (WebView) testView.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl("http://www.baidu.com");

    }

    private static void logBitmap(Bitmap bm) {
        if(bm == null) {
            Log.i(TAG, "bm == null");
        } else {
            Log.i(TAG, "bm=" + bm + " w=" + bm.getWidth() + " h=" + bm.getHeight() + " getByteCount=" + bm.getByteCount() + " hasAlpha=" + bm.hasAlpha() + " Config=" + bm.getConfig() + " isRecycled=" + bm.isRecycled());
        }
    }

    @Override
    protected void test1() {
        long start = SystemClock.elapsedRealtime();
        testView.buildDrawingCache();
        Bitmap bm = testView.getDrawingCache();
        L.logTime(TAG, "getDrawingCache time: ", start, SystemClock.elapsedRealtime());
        logBitmap(bm);
        testView.destroyDrawingCache();
        logBitmap(bm);
    }

    @Override
    protected void test2() {
        long start = SystemClock.elapsedRealtime();
        Bitmap bm = Bitmap.createBitmap(testView.getWidth(), testView.getHeight(), Bitmap.Config.ARGB_8888);
        L.logTime(TAG, "createBitmap time: ", start, SystemClock.elapsedRealtime());
        logBitmap(bm);
    }

    @Override
    protected void test3() {
        long start = SystemClock.elapsedRealtime();
        Bitmap bm = Bitmap.createBitmap(testView.getWidth(), testView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        testView.draw(canvas);
        L.logTime(TAG, "createBitmap draw time: ", start, SystemClock.elapsedRealtime());
        logBitmap(bm);
    }

    private ViewCapture viewCapture = new ViewCapture();

    @Override
    protected void test4() {
        long start = SystemClock.elapsedRealtime();
        Bitmap bm = viewCapture.getViewBitmap(testView);
        L.logTime(TAG, "createBitmap getViewBitmap time: ", start, SystemClock.elapsedRealtime());
        logBitmap(bm);
        viewCapture.recycleBitmap(bm);
    }
}
