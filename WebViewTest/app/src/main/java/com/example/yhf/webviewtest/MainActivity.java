package com.example.yhf.webviewtest;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lib.BaseTestActivity;
import com.example.lib.util.L;
import com.example.lib.util.TestUtils;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrong type of context, can't create fullscreen video 要全屏播放视屏 创建WebView传入的Context 必须为activity
 */
public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";
    private static final String BAIDU = "http://www.baidu.com";

    private CookieManager mCookieManager;
    private ImageView imageView;
    private ViewGroup webContainer;
    private MyWebView webView;
    private HorizontalScrollView hsv;
    private ViewGroup hsvContent;
    private EditText uriEdit;

    private static String[] urls = {"file:///android_asset/test.html", "file:///android_asset/test.html"};

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

        mCookieManager = TestUtils.getCookieManager(this);
        mCookieManager.setAcceptCookie(true);

        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        setContentView(R.layout.activity_main);

        uriEdit = (EditText) findViewById(R.id.uri_edit);
        uriEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_GO == actionId) {
                    webView.loadUrl(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        imageView = (ImageView) findViewById(R.id.image);
        videoViewLayout = (FrameLayout) findViewById(R.id.video_view_layout);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsvContent = (ViewGroup) findViewById(R.id.hsv_content);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 5; ++i) {
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

        //initWebViewPager();
        //initHsv();
        //webView = webViews.get(0);


        //设置pc的user agent
        //webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.27 Safari/537.36");
        initWebView(webView, "https://login.1688.com");
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewGroup p = (ViewGroup) webView.getParent();
            if (p != null) {
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
        if (Build.VERSION.SDK_INT >= 21) {
            mCookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        webView.setBackgroundColor(0);
        WebSettings webSettings = webView.getSettings();
        //启用支持JS
        webSettings.setJavaScriptEnabled(true);
        //设置缓存
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabaseEnabled(true);
//        String databasePath = this.getDir("web_database_storage", Context.MODE_PRIVATE).getPath();
//        webSettings.setDatabasePath(databasePath);

        //WebViewClient帮助WevView处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回则WebView内的连接在WebView打开，否则会弹出调用第三方浏览器
                Log.i(TAG, "shouldOverrideUrlLoading tid=" + Thread.currentThread().getId() + " url=" + url);
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i(TAG, "shouldOverrideUrlLoading tid=" + Thread.currentThread().getId() + " request url:" + request.getUrl() + " method:" + request.getMethod() + " getRequestHeaders:" + request.getRequestHeaders() + " hasGesture:" + request.hasGesture() + " isForMainFrame:" + request.isForMainFrame());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                L.i(TAG, "onPageStarted() called with url = [", url, "] tid=", Thread.currentThread().getId());
                injectionInfo(view, "onPageStarted");
                //logWebViewSize();
                //printBackForwardList();


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.i(TAG, "onPageFinished() called with url = [", url, "] tid=", Thread.currentThread().getId() + " title:" + view.getTitle());
                injectionInfo(view, "onPageFinished");
                //logWebViewSize();
                //printBackForwardList();

                uriEdit.setText(url);
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
                return null;

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
                //return new WebResourceResponse("text/html", "UTF-8", new TestInputStream());


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
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                        L.i(TAG, "onShowCustomView v", i, "  ", L.getRecursionViewInfo(viewGroup.getChildAt(i)));
                    }
                }
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (xCustomView != null) {
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

//        jsObject = new JsObject(this, webView);
//        webView.addJavascriptInterface(jsObject, "test");

        injectionInfo(webView, "111");
//        Map<String, String> additionalHttpHeaders = new HashMap<>();
//        additionalHttpHeaders.put("xxx", "xxxx");
        if (url != null) {
            webView.loadUrl(url);
        }
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
            } else {
                if (webView != null && webView.getParent() != null) {
                    printBackForwardList();
                    if (webView.canGoBack()) {
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
        for (int i = 0, size = list.getSize(); i < size; ++i) {
            Log.d(TAG, "printBackForwardList index:" + i + " url:" + list.getItemAtIndex(i).getUrl());
        }
        Log.d(TAG, "printBackForwardList END CurrentIndex:" + list.getCurrentIndex());
    }

    public boolean inCustomViewShow() {
        return (xCustomView != null);
    }

    public void hideCustomView() {
        L.i(TAG, "hideCustomView xCustomView=" + xCustomView);
        if (xCustomView == null) {
            return;
        }
        xCustomView = null;
        try {
            xCustomViewCallback.onCustomViewHidden();
        } catch (Exception e) {
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
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        L.d(TAG, "onSaveInstanceState() called with outState = [", outState);
        if (webView != null) {
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
        //logWebViewSize();
        webView.loadUrl("http://www.baidu.com");
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
        webView.loadUrl("javascript:(function(){document.getElementById('content-div').style.height = (document.querySelector('#content-div').clientHeight + 100) + 'px'})();");
    }

    @Override
    protected void test6() {
        webView.loadUrl("javascript:(function(){document.getElementById('content-div').style.height = (document.querySelector('#content-div').clientHeight - 100) + 'px'})();");
    }

    @Override
    protected void test7() {
        mCookieManager.removeAllCookie();
    }

    @Override
    protected void test8() {
        Log.i(TAG, "cookie 1688.com:\n" + mCookieManager.getCookie("https://1688.com"));
        Log.i(TAG, "cookie taobao.com:\n" + mCookieManager.getCookie("https://taobao.com"));
        Log.i(TAG, "cookie: sycm.1688.com:\n" + mCookieManager.getCookie("https://sycm.1688.com"));
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

    @Override
    protected void reload() {
        webView.reload();
    }

    private JsObject jsObject;
    private TestLeakObject testLeakObject;

    public TestLeakObject leakTest() {
        TestLeakObject testLeakObject = new TestLeakObject();
        return testLeakObject;
    }


    private void destroyWebView() {
        ViewGroup p = (ViewGroup) webView.getParent();
        if (p != null) {
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
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private static void process() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"ls", "/mnt/sdcard/"});
            String str = IOUtils.toString(process.getInputStream(), "UTF-8");
            Log.i(TAG, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startActionView() {
        Uri uri = Uri.parse(BAIDU);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void initWebViewPager() {
        webViews = new ArrayList<>();
        for (int i = 0; i < urls.length; ++i) {
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
        if (webViews == null) {
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
        for (int i = 0; i < urls.length; ++i) {
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

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("webview", "   现在是横屏1");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("webview", "   现在是竖屏1");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 0, 0, "finestwebview");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == 1) {
            switch (item.getItemId()) {
                case 0:
                    finestwebview();
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void finestwebview() {
        new FinestWebView.Builder(this).show("http://1688.com");
    }
}
