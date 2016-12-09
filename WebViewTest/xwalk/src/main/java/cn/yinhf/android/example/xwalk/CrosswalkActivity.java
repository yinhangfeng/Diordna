package cn.yinhf.android.example.xwalk;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.TextView;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkApplication;
import org.xwalk.core.XWalkCookieManager;
import org.xwalk.core.XWalkDownloadListener;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

import java.io.InputStream;
import java.util.Map;

public class CrosswalkActivity extends XWalkActivity {
    private static final String TAG = "CrosswalkActivity";

    private CookieManager mCookieManager;
    private XWalkView webView;
    private XWalkCookieManager xWalkCookieManager;
    private XWalkWrapper xWalkWrapper;
    private ViewGroup xWalkWrapperFrameLayout;

    @Override
    protected void onXWalkReady() {
        initWebView(webView);
        webView.load("http://baidu.com", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCookieManager = TestUtils.getCookieManager(this);
        xWalkCookieManager = new XWalkCookieManager();

        setContentView(R.layout.activity_crosswalk);

        EditText uriEdit = (EditText) findViewById(R.id.uri_edit);
        uriEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_GO == actionId) {
                    webView.load(v.getText().toString(), null);
                    return true;
                }
                return false;
            }
        });

        xWalkWrapperFrameLayout = (ViewGroup) findViewById(R.id.xwalkWebViewWrapperFrameLayout);
        xWalkWrapper = (XWalkWrapper) findViewById(R.id.xwalkWebViewWrapper);

        webView = xWalkWrapper.webView;

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
    }

    private void initWebView(XWalkView webView) {
        XWalkSettings settings = webView.getSettings();

        settings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2840.50 Safari/537.36");

        XWalkCookieManager xWalkCookieManager = null;

        webView.setDownloadListener(new XWalkDownloadListener(this) {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Log.d(TAG, "onDownloadStart() called with: " + "s = [" + s + "], s1 = [" + s1 + "], s2 = [" + s2 + "], s3 = [" + s3 + "], l = [" + l + "]");
            }
        });

        webView.setResourceClient(new XWalkResourceClient(webView) {

            @Override
            public void onDocumentLoadedInFrame(XWalkView view, long frameId) {
                Log.d(TAG, "onDocumentLoadedInFrame() called with: view = [" + view + "], frameId = [" + frameId + "]");
                super.onDocumentLoadedInFrame(view, frameId);
            }

//            @Override
//            public void onProgressChanged(XWalkView view, int progressInPercent) {
//                Log.d(TAG, "onProgressChanged() called with: view = [" + view + "], progressInPercent = [" + progressInPercent + "]");
//                super.onProgressChanged(view, progressInPercent);
//            }

            @Override
            public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view, XWalkWebResourceRequest request) {
                Log.d(TAG, "shouldInterceptLoadRequest() called with: view = [" + view + "], request = [" + request.getUrl() + "] tid=" + Thread.currentThread().getId());
                return super.shouldInterceptLoadRequest(view, request);
            }

            @Override
            public XWalkWebResourceResponse createXWalkWebResourceResponse(String mimeType, String encoding, InputStream data) {
                Log.d(TAG, "createXWalkWebResourceResponse() called with: mimeType = [" + mimeType + "], encoding = [" + encoding + "], data = [" + data + "]");
                return super.createXWalkWebResourceResponse(mimeType, encoding, data);
            }

            @Override
            public XWalkWebResourceResponse createXWalkWebResourceResponse(String mimeType, String encoding, InputStream data, int statusCode, String reasonPhrase, Map<String, String> responseHeaders) {
                Log.d(TAG, "createXWalkWebResourceResponse() called with: mimeType = [" + mimeType + "], encoding = [" + encoding + "], data = [" + data + "], statusCode = [" + statusCode + "], reasonPhrase = [" + reasonPhrase + "], responseHeaders = [" + responseHeaders + "]");
                return super.createXWalkWebResourceResponse(mimeType, encoding, data, statusCode, reasonPhrase, responseHeaders);
            }

            @Override
            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setUIClient(new XWalkUIClient(webView) {
            @Override
            public void onPageLoadStarted(XWalkView view, String url) {
                super.onPageLoadStarted(view, url);
                Log.d(TAG, "onPageLoadStarted() called with: view = [" + view + "], url = [" + url + "]");
            }

            @Override
            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
                super.onPageLoadStopped(view, url, status);
                Log.d(TAG, "onPageLoadStopped() called with: view = [" + view + "], url = [" + url + "], status = [" + status + "]");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (webView != null) {
//            webView.pauseTimers();
//            webView.onHide();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (webView != null) {
//            webView.resumeTimers();
//            webView.onShow();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.onDestroy();
        }
    }

    protected void reload() {
        webView.reload(XWalkView.RELOAD_IGNORE_CACHE);
    }

    protected void test1() {
        xWalkWrapperFrameLayout.animate().alpha(0).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewGroup) xWalkWrapperFrameLayout.getParent()).removeView(xWalkWrapperFrameLayout);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "test1");
        menu.add(0, 2, 0, "test2");
        menu.add(0, 3, 0, "test3");
        menu.add(0, 4, 0, "test4");
        menu.add(0, 5, 0, "test5");
        menu.add(0, 6, 0, "test6");
        menu.add(0, 7, 0, "test7");
        menu.add(0, 8, 0, "test8");
        menu.add(0, 9, 0, "test9");
        menu.add(0, 10, 0, "reload");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(getClass().getSimpleName(), "test" + item.getItemId() + " start");
        switch (item.getItemId()) {
            case 1:
                test1();
                break;
            case 2:
                test2();
                break;
            case 3:
                test3();
                break;
            case 4:
                test4();
                break;
            case 5:
                test5();
                break;
            case 6:
                test6();
                break;
            case 7:
                test7();
                break;
            case 8:
                test8();
                break;
            case 9:
                test9();
                break;
            case 10:
                reload();
                break;
        }
        return true;
    }

    private void test9() {

    }

    private void test6() {

    }

    private void test5() {

    }

    private void test4() {
    }

    private void test3() {

    }

    protected void test2() {
    }


    protected void test7() {
        xWalkCookieManager.removeAllCookie();
    }

    protected void test8() {
        Log.i(TAG, "cookie 1688.com:\n" + xWalkCookieManager.getCookie("https://1688.com"));
        Log.i(TAG, "cookie taobao.com:\n" + xWalkCookieManager.getCookie("https://taobao.com"));
    }
}
