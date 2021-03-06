package com.example.floatwindowtest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by yhf on 2015/4/29.
 * 浮动窗口
 */
public class FloatWindow {
    private static final String TAG = "FloatWindow";

    private Context mContext;
    private WindowManager mWindowManager;
    private boolean mIsShowing;
    private ViewGroup mContentViewContainer;
    private View mContentView;
    private DisplayMetrics displayMetrics;

    public FloatWindow(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mContentViewContainer = new ContentViewContainer(mContext);
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    public void show(View v) {
        show(v, 0, 0);
    }

    public void show(View v, int x, int y) {
        if(mIsShowing) {
            return;
        }
        if(mContentView == null) {
            Log.w(TAG, "show mContentView == null");
            return;
        }
        mIsShowing = true;
        prepareContentView();
        WindowManager.LayoutParams wmlp = createWindowLayoutParams(v.getWindowToken());
        wmlp.x = x;
        wmlp.y = y;
        mWindowManager.addView(mContentViewContainer, wmlp);
    }

    public void dismiss() {
        if(!mIsShowing) {
            return;
        }
        mIsShowing = false;
        try {
            mWindowManager.removeViewImmediate(mContentViewContainer);
        } finally {
            mContentViewContainer.removeAllViews();
        }
    }

    public View getContentViewContainer() {
        return mContentViewContainer;
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    public View inflateAndSetContentView(int contentViewResId) {
        View contentView = LayoutInflater.from(mContext).inflate(contentViewResId, mContentViewContainer, false);
        setContentView(contentView);
        return contentView;
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    private void prepareContentView() {
        ViewGroup.LayoutParams oriLayoutParams = mContentView.getLayoutParams();
        ContentViewContainer.LayoutParams layoutParams;
        if(oriLayoutParams == null) {
            layoutParams = new ContentViewContainer.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if(!(oriLayoutParams instanceof ContentViewContainer.LayoutParams)) {
            layoutParams = new ContentViewContainer.LayoutParams(oriLayoutParams.width, oriLayoutParams.height);
        } else {
            layoutParams = (ContentViewContainer.LayoutParams) oriLayoutParams;
        }
        mContentViewContainer.addView(mContentView, layoutParams);
    }

    private WindowManager.LayoutParams createWindowLayoutParams(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.format = PixelFormat.TRANSLUCENT;
        //作为系统窗口 需要权限
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        p.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
        //p.token = token;
        return p;
    }

    private void drag(int dx, int dy) {
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) mContentViewContainer.getLayoutParams();
        boundXY(p);
        p.x += dx;
        p.y += dy;
        boundXY(p);
        mWindowManager.updateViewLayout(mContentViewContainer, p);
    }

    private void boundXY(WindowManager.LayoutParams p) {
        if(p.x > displayMetrics.widthPixels - mContentViewContainer.getWidth()) {
            p.x = displayMetrics.widthPixels - mContentViewContainer.getWidth();
        }
        if(p.x < 0) {
            p.x = 0;
        }
        if(p.y > displayMetrics.heightPixels - mContentViewContainer.getHeight()) {
            p.y = displayMetrics.heightPixels - mContentViewContainer.getHeight();
        }
        if(p.y < 0) {
            p.y = 0;
        }
    }

    private class ContentViewContainer extends FrameLayout {

        private int mInitialTouchX;
        private int mInitialTouchY;
        private int mLastTouchX;
        private int mLastTouchY;
        private final int mTouchSlop;
        private boolean isDragging;

        public ContentViewContainer(Context context) {
            super(context);
            final ViewConfiguration vc = ViewConfiguration.get(context);
            mTouchSlop = vc.getScaledTouchSlop();
        }

//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            Log.i(TAG, "onMeasure");
//        }
//
//        @Override
//        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//            super.onLayout(changed, left, top, right, bottom);
//            Log.i(TAG, "onLayout changed=" + changed + " left=" + left + " top=" + top);
//        }

        private void checkStartDragging(MotionEvent event) {
            final int dx = (int) (event.getRawX() + 0.5f) - mInitialTouchX;
            final int dy = (int) (event.getRawY() + 0.5f) - mInitialTouchY;
            if (Math.abs(dx) > mTouchSlop) {
                mLastTouchX = mInitialTouchX + mTouchSlop * (dx < 0 ? -1 : 1);
                isDragging = true;
            }
            if (Math.abs(dy) > mTouchSlop) {
                mLastTouchY = mInitialTouchY + mTouchSlop * (dy < 0 ? -1 : 1);
                isDragging = true;
            }
        }

        @Override
        public boolean onInterceptHoverEvent(MotionEvent event) {
            //Log.i(TAG, "onInterceptHoverEvent event=" + event);
            switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouchX = mLastTouchX = (int) (event.getRawX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (event.getRawY() + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isDragging) {
                    checkStartDragging(event);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                break;
            }
            return isDragging;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //Log.i(TAG, "onTouchEvent event=" + event);
            switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouchX = mLastTouchX = (int) (event.getRawX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (event.getRawY() + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isDragging) {
                    checkStartDragging(event);
                }
                if(isDragging) {
                    final int x = (int) (event.getRawX() + 0.5f);
                    final int y = (int) (event.getRawY() + 0.5f);
                    final int dx = x - mLastTouchX;
                    final int dy = y - mLastTouchY;
                    drag(dx, dy);
                    mLastTouchX = x;
                    mLastTouchY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                break;
            }
            return true;
        }

    }
}
