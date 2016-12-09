package cn.yinhf.android.example.tbs;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lib.BaseTestActivity;
import com.example.lib.util.L;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.CookieManager;

public class TBSActivity extends BaseTestActivity {
    private static final String TAG = "TBSActivity";
    
    private WebView webView;
    private CookieManager cookieManager;
    private EditText uriEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tbs);

        webView = (WebView) findViewById(R.id.web_view);
        cookieManager = CookieManager.getInstance();

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

        initWebView(webView);

        webView.loadUrl("http://baidu.com");
    }

    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2840.50 Safari/537.36");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading tid=" + Thread.currentThread().getId() + " url=" + url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                L.i(TAG, "onPageStarted() called with url = [", url, "] tid=", Thread.currentThread().getId());
                uriEdit.setText(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.i(TAG, "onPageFinished() called with url = [", url, "] tid=", Thread.currentThread().getId() + " title:" + view.getTitle());
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "onReceivedError() called with " + "view = " + view + ", errorCode = " + errorCode + ", description = " + description + ", failingUrl = " + failingUrl + "");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i(TAG, "shouldInterceptRequest url=" + url);
                return null;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
                Log.d(TAG, "shouldInterceptRequest() called with: webView = [" + webView + "], webResourceRequest = [" + webResourceRequest + "], bundle = [" + bundle + "]");
                return null;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i(TAG, "WebChromeClient onProgressChanged newProgress=" + newProgress + " tid=" + Thread.currentThread().getId() + " url=" + view.getUrl());
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
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
                L.i(TAG, "onShowCustomView view=", view, " parent=", view.getParent(), ", callback=", callback);
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                Log.d(TAG, "onHideCustomView() called");
                super.onHideCustomView();
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                Log.d(TAG, "openFileChooser() called with: valueCallback = [" + valueCallback + "], s = [" + s + "], s1 = [" + s1 + "]");
                super.openFileChooser(valueCallback, s, s1);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                L.d(TAG, "onReceivedTitle() called with view = ", view, ", title = ", title, "");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.pauseTimers();
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.resumeTimers();
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    protected void reload() {
        webView.reload();
    }

    protected void test1() {

    }

    protected void test2() {
    }

    protected void test3() {

    }

    protected void test4() {
    }

    protected void test5() {

    }

    protected void test6() {

    }

    protected void test7() {
    }


    protected void test8() {
    }

    protected void test9() {
        L.i(TAG,
                "ApkFileSize:", QbSdk.getApkFileSize(this),
                "\nMiniQBVersion:", QbSdk.getMiniQBVersion(),
                "\nQQBuildNumber:", QbSdk.getQQBuildNumber(),
                "\nTbsVersion:", QbSdk.getTbsVersion(this),
                "\nTID:", QbSdk.getTID(),
                "\ncanLoadVideo:", QbSdk.canLoadVideo(this),
                "\ncanLoadX5:", QbSdk.canLoadX5(this),
                "\ncanLoadX5FirstTimeThirdApp:", QbSdk.canLoadX5FirstTimeThirdApp(this),
                "\ngetTbsSDKVersion:", WebView.getTbsSDKVersion(this),
                "\ngetTbsCoreVersion:", WebView.getTbsCoreVersion(this));
    }
}
