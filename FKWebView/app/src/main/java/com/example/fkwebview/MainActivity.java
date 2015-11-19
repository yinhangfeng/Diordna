package com.example.fkwebview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String[] TEST_URLS = {
            "http://game.youku.com/",
            //"https://github.com/",
            "http://image.baidu.com/",
            "http://blog.jobbole.com/",
            "http://www.imooc.com/",
            //"https://github.com/Trinea/android-open-project",
            "http://www.douyutv.com/directory",
            "http://gank.io/",
            "http://image.baidu.com/search/wiseala?tn=wiseala&ie=utf8&from=index&fmpage=index&word=%E9%AB%98%E6%B8%85%E5%A3%81%E7%BA%B8&pos=magic#!search",
            "http://image.baidu.com/wisebrowse/index?tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&tag3=&pn=0&rn=10&from=index&fmpage=index&pos=magic#/home",
            "http://image.baidu.com/wisebrowse/index?tag1=%E5%8A%A8%E6%BC%AB&tag2=%E5%85%A8%E9%83%A8&tag3=&pn=0&rn=10&from=index&fmpage=index&pos=magic#/home",
            "http://image.baidu.com/wisebrowse/index?tag1=%E5%AE%A0%E7%89%A9&tag2=%E5%85%A8%E9%83%A8&tag3=&pn=0&rn=10&from=index&fmpage=index&pos=magic#/home",
            "http://image.baidu.com/wisebrowse/index?tag1=%E6%91%84%E5%BD%B1&tag2=%E5%85%A8%E9%83%A8&tag3=&pn=0&rn=10&from=index&fmpage=index&pos=magic#/home"
    };
    private static final int WEB_VIEW_COUNT = 0;
    private static final int GONE_WEB_VIEW = 0;

    private List<FKWebView> webViews = new ArrayList<>();
    private Handler handler = new Handler();
    private RandomAnimLayout ral;
    private FrameLayout l1;
    private FragmentStackView fragmentStackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l1 = (FrameLayout) findViewById(R.id.l1);

        for(int i = 0; i < GONE_WEB_VIEW; ++i) {
            FKWebView webView = new FKWebView(this, i + 100);
            initWebView(webView, true);
            l1.addView(webView, -1, -1);
        }

        ral = (RandomAnimLayout) findViewById(R.id.ral);
        for(int i = 0; i < WEB_VIEW_COUNT; ++i) {
            FKWebView webView = new FKWebView(this, i);
            initWebView(webView, true);
            webViews.add(webView);
            ral.addView(webView, -1, -1);
        }

        fragmentStackView = (FragmentStackView) findViewById(R.id.stack);
        fragmentStackView.setFragmentManager(getSupportFragmentManager());
        fragmentStackView.push(new WebViewFragment(), "xxx", false);

        Uri uri = Uri.parse("http://aaa.bbb/aaa/bbb/ccc?a=111&b=111%2F222#cccc");
        Log.i(TAG, "onCreate uri=" + uri.toString());
        Log.i(TAG, "onCreate getAuthority=" + uri.getAuthority());
        Log.i(TAG, "onCreate getQuery=" + uri.getQuery());
        Log.i(TAG, "onCreate getEncodedQuery=" + uri.getEncodedQuery());
        Log.i(TAG, "onCreate getFragment=" + uri.getFragment());
        Log.i(TAG, "onCreate getEncodedFragment=" + uri.getEncodedFragment());

        Uri uri1 = uri.buildUpon().clearQuery().build();
        Log.i(TAG, "onCreate uri1=" + uri1);

        Log.i(TAG, "onCreate " + removeTemplateInfo("LAB_TPL=111%2F222#cccc"));
        Log.i(TAG, "onCreate " + removeTemplateInfo("http://aaa.bbb/aaa/bbb/ccc?LAB_TPL=111%2F222"));
        Log.i(TAG, "onCreate " + removeTemplateInfo("http://aaa.bbb/aaa/bbb/ccc?LAB_TPL=111%2F222&a=1"));
        Log.i(TAG, "onCreate " + removeTemplateInfo("http://aaa.bbb/aaa/bbb/ccc?a=111&LAB_TPL=111%2F222#cccc"));
        Log.i(TAG, "onCreate " + removeTemplateInfo("http://aaa.bbb/aaa/bbb/ccc?LAB_TPL=111%2F222#cccc"));
        Log.i(TAG, "onCreate " + removeTemplateInfo("http://aaa.bbb/aaa/bbb/ccc?a=1&LAB_TPL=111%2F222&b=2#cccc"));
    }

    public static String removeTemplateInfo(String url) {
        int start = url.indexOf("LAB_TPL");
        if(start <= 0) {
            return url;
        }
        int end = url.indexOf('&', start);
        if(end < 0) {
            end = url.indexOf('#', start);
            if(end < 0) {
                end = url.length();
            }
            --start;
        } else {
            ++end;
        }
        if(end >= url.length()) {
            return url.substring(0, start);
        }
        return url.substring(0, start) + url.substring(end);
    }

    void initWebView(final FKWebView webView) {
        initWebView(webView, false);
    }

    void initWebView(final FKWebView webView, boolean loadRandomUrl) {
        webView.setBackgroundColor(0x44ee5555);
        webView.setInitialScale(0);
        webView.setVerticalScrollBarEnabled(false);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        try {
            settings.setSavePassword(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
        settings.setSaveFormData(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // Local Storage  Session Storage
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        settings.setUserAgentString(settings.getUserAgentString() + " xxx");

        FKWebView.globalSettings(settings, this);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading() called with " + "view = " + view + ", url = " + url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted() called with " + "view = " + view + ", url = " + url + ", favicon = " + favicon + "");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished() called with " + "view = " + view + ", url = " + url + "");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e(TAG, "onReceivedError() called with " + "view = " + view + ", errorCode = " + errorCode + ", description = " + description + ", failingUrl = " + failingUrl + "");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e(TAG, "onReceivedSslError() called with " + "view = [" + view + "], handler = [" + handler + "], error = [" + error + "]");
                handler.proceed();
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //如果在shouldInterceptRequest 拦截了请求  则不会调用这里
                super.onLoadResource(view, url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                Log.d(TAG, "doUpdateVisitedHistory() called with " + "view = " + view + ", url = " + url + ", isReload = " + isReload + " tid=" + Thread.currentThread().getId());
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.v(TAG, "onProgressChanged() called with " + "view = " + view + ", newProgress = " + newProgress + "");
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, "onJsAlert() called with " + "view = " + view + ", url = " + url + ", message = " + message + ", result = " + result + "");
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG, "onJsPrompt() called with " + "view = " + view + ", url = " + url + ", message = " + message + ", defaultValue = " + defaultValue + ", result = " + result + "");
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                Log.d(TAG, "onShowCustomView() called with " + "view = " + view + ", callback = " + callback + "");
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                Log.d(TAG, "onHideCustomView() called with " + "");
                super.onHideCustomView();
            }
        });

        webView.addJavascriptInterface(new JsObject(this, webView), "test");

        if(loadRandomUrl) {
            webView.loadUrl(TEST_URLS[((int) (Math.random() * TEST_URLS.length))]);
        }
    }

    void deInitWebView(final FKWebView webView) {
        webView.stopLoading();
        webView.removeAllViews();

        webView.removeJavascriptInterface("test");
        webView.setWebViewClient(null);
        webView.setWebChromeClient(null);
    }

    private Runnable loadUrlRunnable = new Runnable() {
        @Override
        public void run() {
            Log.v(TAG, "loadUrlRunnable run");
            int webViewIndex = (int) (Math.random() * webViews.size());
            FKWebView webView = webViews.get(webViewIndex);

            deInitWebView(webView);
            initWebView(webView);

            int urlIndex = (int) (Math.random() * TEST_URLS.length);
            String url = TEST_URLS[urlIndex];
            webView.loadUrl(url);

            handler.postDelayed(this, (int) (Math.random() * 1000) + 1000);
        }
    };

    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            Log.v(TAG, "scrollRunnable run");
            for(WebView webView : webViews) {
                webView.flingScroll(0, Math.random() > 0.5 ? 3000 : -3000);
            }
            handler.postDelayed(this, 500);
        }
    };

    private Runnable fragmentStackPush = new Runnable() {
        @Override
        public void run() {
            fragmentStackView.push(new WebViewFragment(), "xxx");
            handler.postDelayed(fragmentStackPop, (long) (Math.random() * 1000 + 1000));
        }
    };

    private Runnable fragmentStackPop = new Runnable() {
        @Override
        public void run() {
            fragmentStackView.pop();
            handler.postDelayed(fragmentStackPush, (long) (Math.random() * 100 + 700));
        }
    };

    private void startLoadUrl() {
        handler.post(loadUrlRunnable);
    }

    private void stopLoadUrl() {
        handler.removeCallbacks(loadUrlRunnable);
    }

    @Override
    public void onBackPressed() {
        if(fragmentStackView.getStackSize() > 0) {
            fragmentStackView.pop();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
        case R.id.test1:
            test1();
            break;
        case R.id.test2:
            test2();
            break;
        case R.id.test3:
            test3();
            break;
        case R.id.test4:
            test4();
            break;
        case R.id.test5:
            test5();
            break;
        case R.id.test6:
            test6();
            break;
        case R.id.test7:
            test7();
            break;
        case R.id.test8:
            test8();
            break;
        case R.id.test9:
            test9();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void test1() {
        startLoadUrl();
    }

    private void test2() {
        stopLoadUrl();
    }

    private void test3() {
        ral.start();
        l1.postDelayed(new Runnable() {
            @Override
            public void run() {
               l1.setVisibility(View.INVISIBLE);
            }
        }, 10000);
    }

    private void test4() {
        ral.stop();
    }

    private void test5() {
        handler.post(scrollRunnable);
    }

    private void test6() {
        handler.removeCallbacks(scrollRunnable);
    }

    private void test7() {
        fragmentStackPush.run();
    }

    private void test8() {
        handler.removeCallbacks(fragmentStackPush);
        handler.removeCallbacks(fragmentStackPop);
    }

    private void test9() {
    }
}
