package com.example.fkwebview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by lwg on 15/5/15.
 */
public class FKWebView extends WebView {
    private static final String TAG = "LABWebView";

    private static boolean globalSettingsFlag = false;

    public static void globalSettings(WebSettings settings, Context context) {
        if(globalSettingsFlag) {
            return;
        }
        settings.setDatabaseEnabled(true);
        String databasePath = context.getDir("web_database_storage", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);
        globalSettingsFlag = true;
    }

    public interface OnVerticalScrollListener {

        void onVerticalScroll(int y, int oldY);
    }

    private boolean isDestroyed;
    private int id;

    public FKWebView(Context context, int id) {
        super(context);
        this.id = id;
    }

    public FKWebView(Context context) {
        super(context);
    }

    public FKWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FKWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void destroy() {
        super.destroy();
        isDestroyed = true;
        onVerticalScrollListener = null;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onVerticalScrollListener != null && t != oldt) {
            onVerticalScrollListener.onVerticalScroll(t, oldt);
        }
    }

    private OnVerticalScrollListener onVerticalScrollListener;

    public void setOnVerticalScrollListener(OnVerticalScrollListener onVerticalScrollListener) {
        this.onVerticalScrollListener = onVerticalScrollListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        View child;
        for (int i = 0; i < count; ++i) {
            child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int rightMargin = 0;
                int bottomMargin = 0;
                WebView.LayoutParams lp = (WebView.LayoutParams) child.getLayoutParams();
                if(lp instanceof LayoutParams) {
                    rightMargin = ((LayoutParams) lp).rightMargin;
                    bottomMargin = ((LayoutParams) lp).bottomMargin;
                }
//                else {
//                    L.w(TAG, "onMeasure !lp instanceof LayoutParams");
//                }

                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight() + lp.x + rightMargin, lp.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                        getPaddingTop() + getPaddingBottom() + lp.y + bottomMargin, lp.height);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                int childRight = lp.x + child.getMeasuredWidth() + rightMargin;
                int childBottom = lp.y + child.getMeasuredHeight() + bottomMargin;

                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }

        maxWidth += getPaddingLeft() + getPaddingRight();
        maxHeight += getPaddingTop() + getPaddingBottom();

        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if(p instanceof WebView.LayoutParams) {
            return new LayoutParams(p);
        }
        return new LayoutParams(p);
    }

    public static class LayoutParams extends WebView.LayoutParams {

        public int rightMargin;
        public int bottomMargin;

        public LayoutParams(int width, int height, int x, int y) {
            super(width, height, x, y);
        }

        public LayoutParams(int width, int height, int x, int y, int rightMargin, int bottomMargin) {
            super(width, height, x, y);
            this.rightMargin = rightMargin;
            this.bottomMargin = bottomMargin;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(WebView.LayoutParams source) {
            super(source.width, source.height, source.x, source.y);
        }
    }

    @Override
    public String toString() {
        return "WebView:" + id;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e(TAG, toString() + " finalize tid=" + Thread.currentThread().getId());
    }
}
