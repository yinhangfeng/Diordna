package com.example.yinhf.fullscreentest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

public class StartingWindow {
    private static final String TAG = "LoadingWindow";

    private static final int MIN_LOADING_DURATION = 2333;
    private static final int MAX_LOADING_DURATION = 8000;

    private static final Handler handler = new Handler();

    public interface OnHideListener {
        void onHide();
    }

    private Activity context;
    private OnHideListener mOnHideListener;
    private PopupWindow popupWindow;
    private long showStartTime;

    /**
     * @param onHideListener 隐藏回调
     * @param imgRes 载入界面图片
     */
    public StartingWindow(Activity context, OnHideListener onHideListener, int imgRes) {
        mOnHideListener = onHideListener;

        this.context = context;

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imgRes);
        //imageView.setBackgroundColor(Color.WHITE);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(0xffee7777);
        popupWindow = new PopupWindow(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
//        try {
//            Method setWindowLayoutType = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", int.class);
//            setWindowLayoutType.setAccessible(true);
//            //设置WindowManager.LayoutParams.type为LAST_APPLICATION_WINDOW 使得本窗口在所有alert之上(只能在popupWindow之上)
//            setWindowLayoutType.invoke(popupWindow, android.view.WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mOnHideListener != null) {
                    mOnHideListener.onHide();
                }
            }
        });
    }

    public void show() {
        show(MAX_LOADING_DURATION);
    }

    /**
     * 显示载入页面
     * @param maxDuration 最大界面显示时间 <=0则需手动hide，如果<MIN_LOADING_DURATION 则会使用MIN_LOADING_DURATION
     */
    public void show(int maxDuration) {
        if(popupWindow.isShowing()) {
            return;
        }
//        try {
//            Method showAtLocation = PopupWindow.class.getDeclaredMethod("showAtLocation", IBinder.class, int.class, int.class, int.class);
//            showAtLocation.setAccessible(true);
//            showAtLocation.invoke(popupWindow, null, Gravity.NO_GRAVITY, 0, 0);
//        } catch(Exception e) {
//            e.printStackTrace();
//            return;
//        }
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);
        showStartTime = SystemClock.elapsedRealtime();
        if(maxDuration > 0) {
            if(maxDuration < MIN_LOADING_DURATION) {
                maxDuration = MIN_LOADING_DURATION;
            }
            handler.postDelayed(dismissWindowRunnable, maxDuration);
        }
        setFullScreen(true);
    }

    /**
     * 隐藏载入界面
     * @param checkDuration 隐藏时是否检测MIN_LOADING_DURATION
     */
    public void dismiss(boolean checkDuration) {
        if(!popupWindow.isShowing()) {
            return;
        }
        //UiThreadHelper.removeCallbacks(dismissWindowRunnable);
        if(checkDuration) {
            long dt = SystemClock.elapsedRealtime() - showStartTime;
            long remainTime = MIN_LOADING_DURATION - dt;
            if(remainTime > 0) {
                handler.postDelayed(dismissWindowRunnable, remainTime);
            } else {
                popupWindow.dismiss();
            }
        } else {
            popupWindow.dismiss();
        }

        setFullScreen(false);
    }

    private Runnable dismissWindowRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss(false);
        }
    };

    private void setFullScreen(boolean b) {
        View v;
        v = popupWindow.getContentView();
        //v = context.getWindow().getDecorView();

        if(b) {
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                }
            }, 20);

        }
    }
}
