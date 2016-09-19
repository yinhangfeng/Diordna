package com.example.yhf.webviewtest;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yhf.webviewtest.util.TestUtils;

import org.xwalk.core.XWalkApplication;
import org.xwalk.core.XWalkCookieManager;
import org.xwalk.core.XWalkDownloadListener;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkCookieManagerBridge;
import org.xwalk.core.internal.XWalkCookieManagerInternal;

public class CrosswalkActivity extends BaseTestActivity {
    private static final String TAG = "CrosswalkActivity";

    private CookieManager mCookieManager;
    private XWalkView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCookieManager = TestUtils.getCookieManager(this);

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

        webView = (XWalkView) findViewById(R.id.xwalkWebView);
        initWebView(webView);

        webView.load("http://1688.com", null);


//        XWalkView webView1 = (XWalkView) findViewById(R.id.xwalkWebView1);
//        webView1.load("http://1688.com", null);
//        XWalkView webView2 = (XWalkView) findViewById(R.id.xwalkWebView2);
//        webView2.load("http://fsj-test.hz.backustech.com", null);
//        XWalkView webView3 = (XWalkView) findViewById(R.id.xwalkWebView3);
//        webView3.load("http://www.baidu.com", null);


        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
    }

    private void initWebView(XWalkView webView) {
        XWalkSettings settings = webView.getSettings();

        XWalkCookieManager xWalkCookieManager = null;

        webView.setDownloadListener(new XWalkDownloadListener(this) {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Log.d(TAG, "onDownloadStart() called with: " + "s = [" + s + "], s1 = [" + s1 + "], s2 = [" + s2 + "], s3 = [" + s3 + "], l = [" + l + "]");
            }
        });

        webView.setResourceClient(new XWalkResourceClient(webView) {

        });

        webView.setUIClient(new XWalkUIClient(webView) {

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.pauseTimers();
            webView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.resumeTimers();
            webView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.onDestroy();
        }
    }

    @Override
    protected void reload() {
        webView.reload(XWalkView.RELOAD_IGNORE_CACHE);
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
}
