package com.example.yhf.webviewtest;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yhf.webviewtest.util.L;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";
    private static final String BAIDU = "http://www.baidu.com";

    private ImageView imageView;
    private ViewGroup webContainer;
    private MyWebView webView;
    private HorizontalScrollView hsv;
    private ViewGroup hsvContent;

    //private static String[] urls = {"file:///android_asset/test1.html", "http://www.baidu.com", "http://weibo.com", "file:///android_asset/test.html"};
    //private static String[] urls = {"http://www.youku.com/", "http://www.tudou.com/", "http://www.letv.com/", "http://tv.sohu.com/comic/"};
    private static String[] urls = {"http://app.heyimen.lightappbuilder.com/User/Index/login.html",
            "http://app.heyimen.lightappbuilder.com/Farm/Index/my_farm.html",
            "http://app.heyimen.lightappbuilder.com/Farm/Land/friend_land_list.html",
            "http://app.heyimen.lightappbuilder.com/Farm/Task/seed.html",
            "http://app.heyimen.lightappbuilder.com/Farm/Land/find.html"};
    private List<MyWebView> webViews;
    private ViewPager viewPager;

    private TabLayout tabLayout;

    private FrameLayout videoViewLayout;
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;

    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = getResources().getDisplayMetrics();

        Log.e(TAG, "onCreate Thread.currentThread().getId()=" + Thread.currentThread().getId());
        L.i(TAG, "onCreate density=", getResources().getDisplayMetrics().density);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        videoViewLayout = (FrameLayout) findViewById(R.id.video_view_layout);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsvContent = (ViewGroup) findViewById(R.id.hsv_content);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for(int i = 0; i < urls.length; ++i) {
            tabLayout.addTab(tabLayout.newTab().setText(Integer.toString(i)));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition(), false);
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
        //Wrong type of context, can't create fullscreen video 要全屏播放视屏 必须传入activity
//        webView = new MyWebView(this);
//        webContainer.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView = (MyWebView) webContainer.findViewById(R.id.web_view);

        //initWebView(webView, "file:///android_asset/test1.html");

        //initWebViewPager();
        //initHsv();
        //webView = webViews.get(0);

    }

    private void initWebView(WebView webView, String url) {
        L.i(TAG, "initWebView() called with webView = [", webView, "], url = [", url, "]");
        WebSettings webSettings = webView.getSettings();
        //启用支持JS
        webSettings.setJavaScriptEnabled(true);
        //设置使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.clearHistory();

        //webSettings.setBlockNetworkLoads(true);


        //        webView.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
        //        webSettings.setUseWideViewPort(true);//设定支持viewport
        //        webSettings.setLoadWithOverviewMode(true);
        //        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setSupportZoom(true);//设定支持缩放
        //        webView.setInitialScale(2);

        //WebViewClient帮助WevView处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(MainActivity.this, "shouldOverrideUrlLoading url=" + url, Toast.LENGTH_LONG).show();
                //返回则WebView内的连接在WebView打开，否则会弹出调用第三方浏览器
                Log.i(TAG, "shouldOverrideUrlLoading tid=" + Thread.currentThread().getId() + " url=" + url);
                //                view.loadUrl(url);
                //                return true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                L.d(TAG, "onPageStarted() called with url = [", url, "] tid=", Thread.currentThread().getId());
                injectionInfo(view, "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.i(TAG, "onPageFinished() called with url = [", url, "] tid=", Thread.currentThread().getId());
                injectionInfo(view, "onPageFinished");
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                L.i(TAG, "onScaleChanged() called with view = [", view, "], oldScale = [", oldScale, "], newScale = [", newScale, "]");
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //js线程调用
                //L.e(TAG, "shouldInterceptRequest url=", url, " tid=", Thread.currentThread().getId());
                //                try {
                //                    Thread.sleep(1000);
                //                } catch(InterruptedException e) {
                //                    e.printStackTrace();
                //                }
//                if(url.endsWith(".png")) {
//                    WebResourceResponse response;
//                    try {
//                        response = new WebResourceResponse(null, null, new InputStreamWrapper(getAssets().open("aaa.png"), url));
//                        return response;
//                    } catch(IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(url.endsWith("jquery.min.js")) {
//                    try {
//                        return new WebResourceResponse(null, null, getAssets().open("jquery.min.js"));
//                    } catch(IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //L.w(TAG, "onLoadResource url=", url, " tid=", Thread.currentThread().getId());
                //如果在shouldInterceptRequest 拦截了请求  则不会调用这里
                super.onLoadResource(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i(TAG, "WebChromeClient onProgressChanged newProgress=" + newProgress + " tid=" + Thread.currentThread().getId() + " url=" + view.getUrl());
                injectionInfo(view, "onProgressChanged", "p:" + newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                //Log.i(TAG, "onConsoleMessage " + consoleMessage.message() + " tid=" + Thread.currentThread().getId());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                L.i(TAG, "onJsAlert() called with view = [", view, "], url = [", url, "], message = [", message, "], result = [", result, "] tid=", Thread.currentThread().getId());
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                L.i(TAG, "onJsPrompt() called with view = [", view, "], url = [", url, "], message = [", message, "], defaultValue = [", defaultValue, "], result = [", result, "] tid=", Thread.currentThread().getId());
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                L.i(TAG, "onCreateWindow() called with view = [", view, "], isDialog = [", isDialog, "], isUserGesture = [", isUserGesture, "], resultMsg = [", resultMsg, "]");
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                L.i(TAG, "onShowCustomView view=", view, " parent=", view.getParent());
                if(view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for(int i = 0; i < viewGroup.getChildCount(); ++i) {
                        L.i(TAG, "onShowCustomView v", i, "  ", L.getRecursionViewInfo(viewGroup.getChildAt(i)));
                    }
                }
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (xCustomView != null) {
                    L.e(TAG, "onShowCustomView xCustomView != null");
                    return;
                }
                videoViewLayout.addView(view);
                xCustomView = view;
                xCustomViewCallback = callback;
                videoViewLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHideCustomView() {
                L.i(TAG, "onHideCustomView xCustomView=" + xCustomView);
                if (xCustomView == null)
                    return;
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                videoViewLayout.setVisibility(View.GONE);
                videoViewLayout.removeAllViews();
                xCustomView = null;
                xCustomViewCallback = null;
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
        });

        webView.addJavascriptInterface(new JsObject(this, webView), "test");

        injectionInfo(webView, "111");
        webView.loadUrl(url);
        injectionInfo(webView, "222");

        L.i(TAG, "initWebView webView.getScale()=", webView.getScale());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomViewShow()) {
                hideCustomView();
                return true;
            }else {
                if(webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean inCustomViewShow() {
        return (xCustomView != null);
    }

    public void hideCustomView() {
        L.i(TAG, "hideCustomView");
        xCustomViewCallback.onCustomViewHidden();
    }

    private static void injectionInfo(WebView webView, String msg) {
        injectionInfo(webView, msg, msg);
    }

    private static void injectionInfo(WebView webView, String msg, String msgPush) {
        webView.loadUrl("javascript:console.log(\"test exec " + msg + "\");window.x = window.x||[];x.push('" + msgPush + "')");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
        webView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        L.d(TAG, "onSaveInstanceState() called with outState = [", outState);
        webView.saveState(outState);
        L.d(TAG, "onSaveInstanceState() called with outState = [", outState);
    }

    private TextView tv;

    private void addViewTest() {
        tv = new TextView(this);
        tv.setText("987654321");
        tv.setGravity(Gravity.RIGHT);
        tv.setTextSize(20);
        tv.setBackgroundColor(0xffccffcc);

        WebView.LayoutParams lp = new MyWebView.LayoutParams(2300, 1600, 100, 2000, 0, 0);

        webView.addView(tv, lp);
    }

    @Override
    protected void test1() {
        webView.invalidate();
    }

    @Override
    protected void test2() {

    }

    @Override
    protected void test3() {
        OKHttpProvider.getInstance().newCall(new Request.Builder().url("http://172.18.255.142/user/index/index").get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.e(TAG, "onFailure request=", request, " e=", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                L.i(TAG, "onResponse response=", response.body().string());
            }
        });
    }

    @Override
    protected void test4() {
        webView.reload();
    }

    @Override
    protected void test5() {
        L.i(TAG, "test5 webView.getUrl=", webView.getUrl());
    }

    @Override
    protected void test6() {
        webView.clearHistory();
    }

    @Override
    protected void test7() {
        webContainer.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void test8() {
        ViewGroup p = (ViewGroup) webView.getParent();
        if(p != null) {
            p.removeView(webView);
        }
        webView.destroy();
    }

    @Override
    protected void test9() {
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
            MyWebView webView = new MyWebView(getApplication());
            webView.setId(i);
            initWebView(webView, urls[i]);
            webViews.add(webView);
        }
        //viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(8);
    }

    private void initHsv() {
        int width = getResources().getDisplayMetrics().widthPixels;
        webViews = new ArrayList<>();
        for(int i = 0; i < urls.length; ++i) {
            MyWebView webView = new MyWebView(getApplication());
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
