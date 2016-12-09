package com.example.yhf.webviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import org.xwalk.core.XWalkView;

/**
 * Created by yinhf on 2016/12/9.
 */

public class XWalkWrapper extends FrameLayout {
    private static final String TAG = "XWalkWrapper";

    XWalkView webView;

    public XWalkWrapper(Context context) {
        super(context);
        init(context);
    }

    public XWalkWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XWalkWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        webView = new XWalkView(context);
        addView(webView, -1, -1);
    }


    @Override
    protected void onAttachedToWindow() {
        Log.i(TAG, "onAttachedToWindow:");
        super.onAttachedToWindow();
//        if (webView != null) {
//            resumeWebView();
//        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG, "onDetachedFromWindow:");
        if (webView != null) {
            pauseWebView();
        }
        super.onDetachedFromWindow();
    }

    private void resumeWebView() {
        webView.resumeTimers();
        webView.onShow();
    }

    private void pauseWebView() {
        webView.pauseTimers();
        webView.onHide();
    }
}
