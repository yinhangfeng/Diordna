package com.example.yhf.webviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.EdgeEffect;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yhf.webviewtest.util.L;

/**
 * Created by yhf on 2015/6/19.
 */
public class MyWebView extends WebView {
    private String TAG = "MyWebView";

    public void setId(int id) {
        TAG = "MyWebView_" + id;
    }

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        TextView tv = new TextView(getContext());
//        tv.setText("xxxxxxxxxx");
//        tv.setTextSize(16);
//        WebView.LayoutParams layoutParams = new WebView.LayoutParams(WebView.LayoutParams.MATCH_PARENT, 300, 0, 600);
//        addView(tv, layoutParams);
        Class<?> cls = getClass();
        while(cls != null) {
            Log.i(TAG, "init cls=" + cls);
            cls = cls.getSuperclass();
        }
        //setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        //setWillNotDraw(true);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        Log.i(TAG, "addView() called with " + "child = [" + child.getClass() + "], index = [" + index + "], params = [" + layoutParamsToString(params) + "]");
        super.addView(child, index, params);
    }

    protected void onSetLayoutParams(View child, LayoutParams layoutParams) {
        L.d(TAG, "onSetLayoutParams() called with child = [", child, "], layoutParams = [", layoutParamsToString(layoutParams), "]");
    }

    private static String layoutParamsToString(ViewGroup.LayoutParams params) {
        if(params == null) {
            return "null";
        }
        if(params instanceof AbsoluteLayout.LayoutParams) {
            AbsoluteLayout.LayoutParams lp = (LayoutParams) params;
            return params.getClass() + " | x:" + lp.x + " y:" + lp.y + " w:" + lp.width + " h:" + lp.height;
        } else {
            return params.getClass() + "w:" + params.width + " h:" + params.height;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(getChildCount() > 0) {
            L.LogViewInfo(TAG + "onLayout", this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //L.e(TAG, "onTouchEvent event=", event);
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        //L.e(TAG, "draw tid=", Thread.currentThread().getId());
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Log.i(TAG, "onDraw()");
        //L.e(TAG, "onDraw tid=", Thread.currentThread().getId());
        super.onDraw(canvas);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return super.canScrollHorizontally(direction);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollVertically(direction);
    }

    @Override
    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public void scrollTo(int x, int y) {
        //Log.i(TAG, "scrollTo() called with " + "x = [" + x + "], y = [" + y + "]");
        super.scrollTo(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        //Log.i(TAG, "scrollBy() called with " + "x = [" + x + "], y = [" + y + "]");
        super.scrollBy(x, y);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //Log.i(TAG, "overScrollBy() called with " + "deltaX = [" + deltaX + "], deltaY = [" + deltaY + "], scrollX = [" + scrollX + "], scrollY = [" + scrollY + "], scrollRangeX = [" + scrollRangeX + "], scrollRangeY = [" + scrollRangeY + "], maxOverScrollX = [" + maxOverScrollX + "], maxOverScrollY = [" + maxOverScrollY + "], isTouchEvent = [" + isTouchEvent + "]");
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, 300, 400, isTouchEvent);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        //Log.i(TAG, "onOverScrolled() called with " + "scrollX = [" + scrollX + "], scrollY = [" + scrollY + "], clampedX = [" + clampedX + "], clampedY = [" + clampedY + "]");
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //Log.i(TAG, "onScrollChanged() called with " + "l = [" + l + "], t = [" + t + "], oldl = [" + oldl + "], oldt = [" + oldt + "]");
        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        L.i(TAG, "onMeasure childCount=" + getChildCount());
//        for(int i = 0; i < getChildCount(); ++i) {
//            L.i(TAG, "onMeasure v", i, "   ", L.getRecursionViewInfo(getChildAt(i)));
//        }


        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int rightMargin = 0;
                int bottomMargin = 0;
                WebView.LayoutParams lp = (WebView.LayoutParams) child.getLayoutParams();
                if(lp instanceof LayoutParams) {
                    rightMargin = ((LayoutParams) lp).rightMargin;
                    bottomMargin = ((LayoutParams) lp).bottomMargin;
                } else {
                    L.w(TAG, "onMeasure xxxxxxxxxxxxx");
                    Toast.makeText(getContext(), "xxxx", Toast.LENGTH_SHORT).show();
                }

                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight() + lp.x + rightMargin, lp.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                        getPaddingTop() + getPaddingBottom() + lp.y + bottomMargin, lp.height);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                int childRight;
                int childBottom;
                childRight = lp.x + child.getMeasuredWidth() + rightMargin;
                childBottom = lp.y + child.getMeasuredHeight() + bottomMargin;

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
        L.i(TAG, "generateDefaultLayoutParams");
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        L.i(TAG, "generateLayoutParams() called with attrs = ", attrs);
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        L.i(TAG, "checkLayoutParams p=" + (p == null ? "null" : p.getClass()));
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        L.i(TAG, "generateLayoutParams p=" + (p == null ? "null" : p.getClass()));
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
    }
}
