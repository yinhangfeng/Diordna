package com.example.yhf.webviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import com.example.lib.util.L;

/**
 * Created by yhf on 2015/6/19.
 */
public class MyWebView extends WebView {
    private String TAG = "MyWebView";

    boolean xxx = true;

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

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return super.onCreateInputConnection(outAttrs);
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
        setOverScrollMode(View.OVER_SCROLL_NEVER);
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
        L.i(TAG, "onLayout() called with changed = ", changed, ", l = ", l, ", t = ", t, ", r = ", r, ", b = ", b, "");
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
        //Log.i(TAG, "computeVerticalScrollOffset:");
        return super.computeVerticalScrollOffset();
    }

    @Override
    public int computeVerticalScrollRange() {
        //Log.i(TAG, "computeVerticalScrollRange: ");
        return super.computeVerticalScrollRange();
    }

    @Override
    public int computeVerticalScrollExtent() {
        //Log.i(TAG, "computeVerticalScrollExtent:");
        return super.computeVerticalScrollExtent();
    }

    private boolean isSizeChanged;

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        L.i(TAG, "onSizeChanged() called with w = ", w, ", h = ", h, ", ow = ", ow, ", oh = ", oh, "");
//        if(h - oh > DisplayUtils.dp2Px(100) && oh != 0 && !isSizeChanged) {
//            Log.e(TAG, "onSizeChanged xxxx");
//            isSizeChanged = true;
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "onSizeChanged xxxx run");
//                    int left = getLeft();
//                    int top = getTop();
//                    int right = getRight();
//                    int bottom = getBottom();
//                    //layout(getLeft(), top - 1, getRight(), bottom);
////                    offsetTopAndBottom(-1);
////                    layout(left, top, right, bottom);
//                    isSizeChanged = false;
//                }
//            }, 150);
//        }
        super.onSizeChanged(w, h, ow, oh);
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
        //return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, 300, 400, isTouchEvent);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, 0, 0, isTouchEvent);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        //Log.i(TAG, "onOverScrolled() called with " + "scrollX = " + scrollX + ", scrollY = " + scrollY + ", clampedX = " + clampedX + ", clampedY = " + clampedY);
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //Log.i(TAG, "onScrollChanged() called with " + "l = " + l + ", t = " + t + ", oldl = " + oldl + ", oldt = " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
//        if(isSizeChanged) {
//            isSizeChanged = false;
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "onScrollChanged SizeChanged run xxx");
//
////                    int top = getTop();
////                    int bottom = getBottom();
////                    layout(getLeft(), top - 1, getRight(), bottom - 1);
////                    layout(getLeft(), top, getRight(), bottom);
//                }
//            });
//        }
    }

    @Override
    public void requestLayout() {
        if (xxx) {
            super.requestLayout();
        }
        //Log.i(TAG, "requestLayout:");
    }

    @Override
    public void forceLayout() {
        super.forceLayout();
        //Log.i(TAG, "forceLayout:");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //L.i(TAG, "onMeasure widthMeasureSpec=", MeasureSpec.toString(widthMeasureSpec), " heightMeasureSpec=", MeasureSpec.toString(heightMeasureSpec));
//        for(int i = 0; i < getChildCount(); ++i) {
//            L.i(TAG, "onMeasure v", i, "   ", L.getRecursionViewInfo(getChildAt(i)));
//        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int count = getChildCount();
//
//        int maxHeight = 0;
//        int maxWidth = 0;
//
//        // Find out how big everyone wants to be
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//        // Find rightmost and bottom-most child
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
//                int childRight;
//                int childBottom;
//
//                AbsoluteLayout.LayoutParams lp
//                        = (AbsoluteLayout.LayoutParams) child.getLayoutParams();
//
//                childRight = lp.x + child.getMeasuredWidth();
//                childBottom = lp.y + child.getMeasuredHeight();
//
//                maxWidth = Math.max(maxWidth, childRight);
//                maxHeight = Math.max(maxHeight, childBottom);
//            }
//        }
//
//        // Account for padding too
//        maxWidth += getPaddingLeft() + getPaddingRight();
//        maxHeight += getPaddingTop() + getPaddingBottom();
//
//        // Check against minimum height and width
//        maxHeight = Math.max(maxHeight, 200);
//        maxWidth = Math.max(maxWidth, 200);
//
//        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
//                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

//    @Override
//    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
//        L.i(TAG, "generateDefaultLayoutParams");
//        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0);
//    }
//
//    @Override
//    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
//        L.i(TAG, "generateLayoutParams() called with attrs = ", attrs);
//        return new LayoutParams(getContext(), attrs);
//    }
//
//    @Override
//    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
//        L.i(TAG, "checkLayoutParams p=" + (p == null ? "null" : p.getClass()));
//        return p instanceof LayoutParams;
//    }
//
//    @Override
//    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
//        L.i(TAG, "generateLayoutParams p=" + (p == null ? "null" : p.getClass()));
//        return new LayoutParams(p);
//    }
//
//    public static class LayoutParams extends WebView.LayoutParams {
//
//        public int rightMargin;
//        public int bottomMargin;
//
//        public LayoutParams(int width, int height, int x, int y) {
//            super(width, height, x, y);
//        }
//
//        public LayoutParams(int width, int height, int x, int y, int rightMargin, int bottomMargin) {
//            super(width, height, x, y);
//            this.rightMargin = rightMargin;
//            this.bottomMargin = bottomMargin;
//        }
//
//        public LayoutParams(Context c, AttributeSet attrs) {
//            super(c, attrs);
//        }
//
//        public LayoutParams(ViewGroup.LayoutParams source) {
//            super(source);
//        }
//    }
}
