package com.example.yhf.webviewtest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yhf.webviewtest.util.L;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrong type of context, can't create fullscreen video 要全屏播放视屏 创建WebView传入的Context 必须为activity
 */
public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";
    private static final String BAIDU = "http://www.baidu.com";

    private ImageView imageView;
    private ViewGroup webContainer;
    private MyWebView webView;
    private HorizontalScrollView hsv;
    private ViewGroup hsvContent;

    private static String[] urls = {"file:///android_asset/test.html", "file:///android_asset/test.html"};
    //private static String[] urls = {"http://www.youku.com/", "http://www.tudou.com/", "http://www.letv.com/", "http://tv.sohu.com/comic/"};
//    private static String[] urls = {
//            "file:///android_asset/test1.html",
//            "file:///android_asset/test.html",
//            "http://app.heyimen.lightappbuilder.com/User/Index/login.html",
//            "http://app.heyimen.lightappbuilder.com/Farm/Task/seed.html",
//            "http://app.heyimen.lightappbuilder.com/Farm/Land/find.html"};

    //测试scroll
//    private static String[] urls = {
//            "http://image.baidu.com/wisebrowse/index?tag1=%E6%91%84%E5%BD%B1&tag2=%E5%85%A8%E9%83%A8&tag3=&pn=0&rn=10&from=index&fmpage=index&pos=magic#/home",
//            "file:///android_asset/iframe_scroll.html"};

    private List<MyWebView> webViews;
    private ViewPager viewPager;

    private TabLayout tabLayout;

    private FrameLayout videoViewLayout;
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;

    private DisplayMetrics dm;

    private static MyWebView newWebView(Context context) {
        return new MyWebView(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = getResources().getDisplayMetrics();

//        if(Build.VERSION.SDK_INT >= 21) {
//            L.e(TAG, "onCreate enableSlowWholeDocumentDraw");
//            WebView.enableSlowWholeDocumentDraw();
        //        }

        if(Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        Log.e(TAG, "onCreate Thread.currentThread().getId()=" + Thread.currentThread().getId());
        L.i(TAG, "onCreate density=", getResources().getDisplayMetrics().density);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        videoViewLayout = (FrameLayout) findViewById(R.id.video_view_layout);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsvContent = (ViewGroup) findViewById(R.id.hsv_content);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for(int i = 0; i < 5; ++i) {
            tabLayout.addTab(tabLayout.newTab().setText(Integer.toString(i)));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //hsv.scrollTo(dm.widthPixels * tab.getPosition(), 0);
                //hsvContent.setTranslationX(-dm.widthPixels * tab.getPosition());
//                for(int i = 0; i < urls.length; ++i) {
//                    WebView webView = webViews.get(i);
//                    if(i == tab.getPosition()) {
//                        webView.setVisibility(View.VISIBLE);
//                    } else {
//                        webView.setVisibility(View.INVISIBLE);
//                    }
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        webContainer = (ViewGroup) findViewById(R.id.web_container);

        //webView = newWebView(this);
        webView = (MyWebView) findViewById(R.id.web_view);
        //webContainer.addView(webView, ViewGroup.LayoutParams.MATCH_PARENT, 500);

        //file:///android_asset/test1.html
        //http://172.18.255.232/appbuilder/lab3/h5/trunk/lab_test.html
        //initWebView(webView, "file:///android_asset/test2.html");
        //initWebView(webView, "http://qxp.lightappbuilder.com/house/recommend/showRecommend/id/173?a=3#aaa");
        //initWebView(webView, "file:///android_asset/test.html");
        //initWebView(webView, "http://image.baidu.com/search/wiseala?tn=wiseala&ie=utf8&from=index&fmpage=index&word=%E7%A7%BB%E8%BD%B4%E6%91%84%E5%BD%B1&pos=magic#!search");
        initWebView(webView, "http://www.baidu.com");

        //initWebViewPager();
        //initHsv();
        //webView = webViews.get(0);

    }

    @Override
    protected void onDestroy() {
        if(webView != null) {
            ViewGroup p = (ViewGroup) webView.getParent();
            if(p != null) {
                p.removeView(webView);
            }
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.destroy();
            webView = null;
        }
//        if(jsObject != null) {
//            App.getRefWatcher().watch(jsObject);
//            jsObject = null;
//        }
        super.onDestroy();
    }

    private void initWebView(final WebView webView, String url) {
        L.i(TAG, "initWebView() called with webView = [", webView, "], url = [", url, "]");
        webView.setBackgroundColor(0);
        WebSettings webSettings = webView.getSettings();
        //启用支持JS
        webSettings.setJavaScriptEnabled(true);
        //设置缓存
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //WebViewClient帮助WevView处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(MainActivity.this, "shouldOverrideUrlLoading url=" + url, Toast.LENGTH_LONG).show();
                //返回则WebView内的连接在WebView打开，否则会弹出调用第三方浏览器
                Log.i(TAG, "shouldOverrideUrlLoading tid=" + Thread.currentThread().getId() + " url=" + url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                L.i(TAG, "onPageStarted() called with url = [", url, "] tid=", Thread.currentThread().getId());
                injectionInfo(view, "onPageStarted");
                logWebViewSize();
                //printBackForwardList();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.i(TAG, "onPageFinished() called with url = [", url, "] tid=", Thread.currentThread().getId() + " title:" + view.getTitle());
                injectionInfo(view, "onPageFinished");
                logWebViewSize();
                //printBackForwardList();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "onReceivedError() called with " + "view = " + view + ", errorCode = " + errorCode + ", description = " + description + ", failingUrl = " + failingUrl + "");
                super.onReceivedError(view, errorCode, description, failingUrl);
                //printBackForwardList();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.e(TAG, "onReceivedHttpError() called with: " + "view = [" + view + "], request = [" + request + "], errorResponse = [" + errorResponse + "]");
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e(TAG, "onReceivedSslError() called with " + "view = [" + view + "], handler = [" + handler + "], error = [" + error + "]");
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                L.i(TAG, "onScaleChanged() called with view = [", view, "], oldScale = [", oldScale, "], newScale = [", newScale, "]");
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(TAG, "shouldInterceptRequest url=" + url);

//                if (url.startsWith("http://www.baidu.com")) {
//                    //在低版本手机上无法直接在shouldInterceptRequest中发起网络请求 (目前测试4.4不行)
//                    try {
//                        Response response = OKHttpProvider.getInstance().newCall(new Request.Builder().url("http://www.baidu.com").build()).execute();
//                        String s = response.body().string();
//                        Log.i(TAG, "shouldInterceptRequest: xxxxxx");
//                        return new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(s.getBytes()));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                //在低版本手机上 返回的WebResourceResponse的InputStream中读取错误会引起WebView native崩溃 (目前测试4.4会)
                return new WebResourceResponse("text/html", "UTF-8", new TestInputStream());
                //return null;


                //SystemClock.sleep(60000);
                //js线程调用
//                if(url.endsWith("test.png")) {
//                    try {
//                        InputStream is = new InputStreamWrapper(getAssets().open("aaa.png"), url);
//                        return new WebResourceResponse("image/png", null, is);
////                        Map<String, String> responseHeaders = new HashMap<String, String>();
////                        responseHeaders.put("Cache-Control", "max-age=30");
////                        return new WebResourceResponse(null, null, 200, "OK", responseHeaders, is);
//                    } catch(IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                //Log.i(TAG, "shouldInterceptRequest() request{ url=" + request.getUrl() + ", method=" + request.getMethod() + ", hasGesture=" + request.hasGesture() + ", isForMainFrame=" + request.isForMainFrame() + ", Headers=" + request.getRequestHeaders() + "} tid=" + Thread.currentThread().getId());
//                String url = request.getUrl().toString();
//                if(url.endsWith("test.png")) {
//                    try {
//                        InputStream is = new InputStreamWrapper(getAssets().open("aaa.png"), url);
//                        //return new WebResourceResponse("image/png", null, is);
//                        Map<String, String> responseHeaders = new HashMap<String, String>();
//                        responseHeaders.put("Cache-Control", "max-age=30");
//                        return new WebResourceResponse(null, null, 200, "OK", responseHeaders, is);
//                    } catch(IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return null;
//            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //L.w(TAG, "onLoadResource url=", url, " tid=", Thread.currentThread().getId());
                //如果在shouldInterceptRequest 拦截了请求  则不会调用这里
                super.onLoadResource(view, url);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                //super.doUpdateVisitedHistory(view, url, isReload);
                Log.e(TAG, "doUpdateVisitedHistory() called with " + "view = " + view + ", url = " + url + ", isReload = " + isReload + " tid=" + Thread.currentThread().getId());
                printBackForwardList();
                //view.clearHistory();
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        printBackForwardList();
//                        if(webView.canGoBack()) {
//                            webView.post(this);
//                        } else {
//                            Log.i(TAG, "run xxxxxxx");
//                        }
//                    }
//                });
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i(TAG, "WebChromeClient onProgressChanged newProgress=" + newProgress + " tid=" + Thread.currentThread().getId() + " url=" + view.getUrl());
                injectionInfo(view, "onProgressChanged", "p:" + newProgress);
                //printBackForwardList();
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                //Log.i(TAG, "onConsoleMessage " + consoleMessage.message() + " tid=" + Thread.currentThread().getId());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                L.i(TAG, "onJsAlert() called with url = [", url, "], message = [", message, "], result = [", result, "] tid=", Thread.currentThread().getId());
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                L.i(TAG, "onJsPrompt() called with url = [", url, "], message = [", message, "], defaultValue = [", defaultValue, "], result = [", result, "] tid=", Thread.currentThread().getId());
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                L.i(TAG, "onCreateWindow() called with isDialog = [", isDialog, "], isUserGesture = [", isUserGesture, "], resultMsg = [", resultMsg, "]");
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                L.i(TAG, "onShowCustomView view=", view, " parent=", view.getParent(), ", callback=", callback);
                if(view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for(int i = 0; i < viewGroup.getChildCount(); ++i) {
                        L.i(TAG, "onShowCustomView v", i, "  ", L.getRecursionViewInfo(viewGroup.getChildAt(i)));
                    }
                }
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if(xCustomView != null) {
                    L.e(TAG, "onShowCustomView xCustomView != null");
                    callback.onCustomViewHidden();
                    return;
                }
                videoViewLayout.addView(view);
                xCustomView = view;
                xCustomViewCallback = callback;
                videoViewLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHideCustomView() {
                L.i(TAG, "onHideCustomView xCustomView=" + xCustomView + ", xCustomViewCallback=" + xCustomViewCallback + ", tid=" + Thread.currentThread().getId());
                hideCustomView();
            }

            @Override
            public View getVideoLoadingProgressView() {
                L.i(TAG, "getVideoLoadingProgressView");
                return new ProgressBar(MainActivity.this);
            }


            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                L.i(TAG, "openFileChooser() called with uploadMsg = [", uploadMsg, "]");
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                L.i(TAG, "openFileChooser() called with uploadMsg = [", uploadMsg, "], acceptType = [", acceptType, "]");
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                L.i(TAG, "openFileChooser() called with uploadMsg = [", uploadMsg, "], acceptType = [", acceptType, "], capture = [", capture, "]");
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                boolean b = super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                L.i(TAG, "onShowFileChooser() called with webView = [", webView, "], filePathCallback = [", filePathCallback, "], fileChooserParams = [", fileChooserParams, "] b=", b);
                return b;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                L.d(TAG, "onReceivedTitle() called with view = ", view, ", title = ", title, "");
            }
        });

        jsObject = new JsObject(this, webView);
        webView.addJavascriptInterface(jsObject, "test");

        injectionInfo(webView, "111");
        Map<String, String> additionalHttpHeaders = new HashMap<>();
        additionalHttpHeaders.put("xxx", "xxxx");
        webView.loadUrl(url, additionalHttpHeaders);
        injectionInfo(webView, "222");

        L.i(TAG, "initWebView webView.getScale()=", webView.getScale());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomViewShow()) {
                Log.i(TAG, "onKeyDown KEYCODE_BACK inCustomViewShow");
                hideCustomView();
                return true;
            }else {
                if(webView != null && webView.getParent() != null) {
                    printBackForwardList();
                    if(webView.canGoBack()) {
                        Log.e(TAG, "onKeyDown canGoBack");
                        webView.goBack();
                        return true;
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void printBackForwardList() {
        Log.d(TAG, "printBackForwardList START");
        WebBackForwardList list = webView.copyBackForwardList();
        for(int i = 0, size = list.getSize(); i < size; ++i) {
            Log.d(TAG, "printBackForwardList index:" + i + " url:" + list.getItemAtIndex(i).getUrl());
        }
        Log.d(TAG, "printBackForwardList END CurrentIndex:" + list.getCurrentIndex());
    }

    public boolean inCustomViewShow() {
        return (xCustomView != null);
    }

    public void hideCustomView() {
        L.i(TAG, "hideCustomView xCustomView=" + xCustomView);
        if(xCustomView == null) {
            return;
        }
        xCustomView = null;
        try {
            xCustomViewCallback.onCustomViewHidden();
        } catch(Exception e) {
            e.printStackTrace();
        }
        xCustomViewCallback = null;
        videoViewLayout.setVisibility(View.GONE);
        videoViewLayout.removeAllViews();
        L.i(TAG, "hideCustomView after");
    }

    private static void injectionInfo(WebView webView, String msg) {
        injectionInfo(webView, msg, msg);
    }

    private static void injectionInfo(WebView webView, String msg, String msgPush) {
        //webView.loadUrl("javascript:console.log(\"test exec " + msg + "\");window.x = window.x||[];x.push('" + msgPush + "')");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
        if(webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
        if(webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        L.d(TAG, "onSaveInstanceState() called with outState = [", outState);
        if(webView != null) {
            webView.saveState(outState);
        }
        L.d(TAG, "onSaveInstanceState() called with outState = [", outState);
    }

    private TextView tv;

    private void addViewTest() {
//        tv = new TextView(this);
//        tv.setText("987654321");
//        tv.setGravity(Gravity.RIGHT);
//        tv.setTextSize(20);
//        tv.setBackgroundColor(0xffccffcc);
//
//        WebView.LayoutParams lp = new MyWebView.LayoutParams(2300, 1600, 100, 2000, 0, 0);
//
//        webView.addView(tv, lp);
    }

    private void logWebViewSize() {
        L.i(TAG, "logWebViewSize webView.getContentHeight()=", webView.getContentHeight(), " webView.getHeight()=", webView.getHeight(), " webView.computeVerticalScrollExtent()", webView.computeVerticalScrollExtent(), " webView.computeVerticalScrollOffset()", webView.computeVerticalScrollOffset(), " webView.computeVerticalScrollRange()", webView.computeVerticalScrollRange());
    }

    @Override
    protected void test1() {
        //addViewPagerItem("file:///android_asset/test.html");
        //webView.loadUrl("http://172.18.255.71:3100/xxx/flex-scroll1.html");
        logWebViewSize();
    }

    @Override
    protected void test2() {
        webView.reload();
    }

    @Override
    protected void test3() {
        webView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
    }

    @Override
    protected void test4() {
        webView.onOverScrolled(0, 100, false, false);
    }

    @Override
    protected void test5() {
        webView.setTranslationY(50);
    }

    @Override
    protected void test6() {
        webView.requestLayout();
    }

    @Override
    protected void test7() {
        ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
        if(layoutParams != null) {
            layoutParams.height = webView.getHeight() - 10;
            webView.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void test8() {
        URITest.test("https://wx.qiangxianpai.com:8080/?aaa=1#aaa");
    }

    @Override
    protected void test9() {
//        OKHttpProvider.getInstance()
//                .newCall(new Request.Builder()
//                        .url("http://172.18.255.66:8888/aaa")
//                        .build())
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        L.e(TAG, "onFailure() called with request = ", request, ", e = ", e, "");
//                    }
//
//                    @Override
//                    public void onResponse(Response response) throws IOException {
//                        L.i(TAG, "onResponse() called with response = ", response, " body=", response.body().string());
//                    }
//                });
        startActivity(new Intent(this, Test1Activity.class));
    }

    private JsObject jsObject;
    private TestLeakObject testLeakObject;

    public TestLeakObject leakTest() {
        TestLeakObject testLeakObject = new TestLeakObject();
        return testLeakObject;
    }


    private void destroyWebView() {
        ViewGroup p = (ViewGroup) webView.getParent();
        if(p != null) {
            p.removeView(webView);
        }
        webView.destroy();
    }

    private void layerType() {
        L.i(TAG, "test9 isHardwareAccelerated=", webView.isHardwareAccelerated(), " getLayerType=", webView.getLayerType());
    }

    private void scrollTest() {
        Log.i(TAG, "test1 v-1: " + webView.canScrollVertically(-1) + " v1: " + webView.canScrollVertically(1) +
                " h-1: " + webView.canScrollHorizontally(-1) + " h1: " + webView.canScrollHorizontally(1));
        long start = SystemClock.elapsedRealtime();
        int offset = webView.computeVerticalScrollOffset();
        int range = webView.computeVerticalScrollRange();
        int extent = webView.computeVerticalScrollExtent();
        long time = SystemClock.elapsedRealtime() - start;
        Log.i(TAG, "test2 computeVerticalScrollOffset=" + offset + " computeVerticalScrollRange=" + range +
                " computeVerticalScrollExtent=" + extent + " w=" + webView.getWidth() + " h=" + webView.getHeight() + " time=" + time);
        Log.i(TAG, "test2 getScrollX=" + webView.getScrollX() + " getScrollY=" + webView.getScrollY());
    }

    private void anim() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation tranAnim = new TranslateAnimation(0, 700, 0, 0);
        tranAnim.setDuration(3000);
        set.addAnimation(tranAnim);
        AlphaAnimation alphaAnim = new AlphaAnimation(1, 0.3f);
        alphaAnim.setDuration(2000);
        set.addAnimation(alphaAnim);
        webView.startAnimation(set);
    }

    private void loadData() {
        try {
            String html = IOUtils.toString(getAssets().open("test1.html"), "UTF-8");
            Log.i(TAG, "html=" + html);
            webView.loadDataWithBaseURL("http://www.baidu.com", html, "text/html", "UTF-8", "http://www.baidu.com");
        } catch(IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private static void process() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] {"ls", "/mnt/sdcard/"});
            String str = IOUtils.toString(process.getInputStream(), "UTF-8");
            Log.i(TAG, str);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startActionView() {
        Uri uri = Uri.parse(BAIDU);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void initWebViewPager() {
        webViews = new ArrayList<>();
        for(int i = 0; i < urls.length; ++i) {
            MyWebView webView = newWebView(getApplication());
            webView.setId(i);
            initWebView(webView, urls[i]);
            webViews.add(webView);
        }
        //viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(8);
    }

    private void addViewPagerItem(String url) {
        if(webViews == null) {
            webViews = new ArrayList<>();
        }
        MyWebView webView = newWebView(getApplication());
        webView.setId(webViews.size());
        initWebView(webView, url);
        webViews.add(webView);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    private void initHsv() {
        int width = getResources().getDisplayMetrics().widthPixels;
        webViews = new ArrayList<>();
        for(int i = 0; i < urls.length; ++i) {
            MyWebView webView = newWebView(getApplication());
            webView.setId(i);
            initWebView(webView, urls[i]);
            webViews.add(webView);
            hsvContent.addView(webView, width - 30, -1);
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return webViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            L.i(TAG, "instantiateItem() called with container = [", container, "], position = [", position, "]");
            WebView webView = webViews.get(position);
            container.addView(webView);
            return webView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            L.i(TAG, "destroyItem() called with container = [", container, "], position = [", position, "], object = [", object, "]");
            container.removeView((View) object);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(webViews.get(position));
        }

        @Override
        public int getCount() {
            return webViews.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "" + position;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("testwebview", "=====<<<  onConfigurationChanged  >>>=====");
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i("webview", "   现在是横屏1");
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.i("webview", "   现在是竖屏1");
        }
    }
}
