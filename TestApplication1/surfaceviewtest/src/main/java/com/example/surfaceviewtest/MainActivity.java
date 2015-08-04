package com.example.surfaceviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.commonlibrary.BaseTestActivity;
import com.example.commonlibrary.L;

/**
 * SurfaceView设为INVISIBLE GONE 或者从布局移除都会触发SurfaceHolder 的surfaceDestroyed回调
 * 如果是SurfaceView的父View设置INVISIBLE GONE不会触发 但从布局移除会触发
 */
public class MainActivity extends BaseTestActivity implements SurfaceHolder.Callback {
    private static final String TAG = "MainActivity";

    private FrameLayout surfaceViewContainer;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceViewContainer = (FrameLayout) findViewById(R.id.surface_view_container);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        surfaceView.getHolder().addCallback(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.i(TAG, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.i(TAG, "onDestroy ");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        L.i(TAG, "surfaceCreated() called with holder = ", holder, "");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        L.i(TAG, "surfaceChanged() called with holder = ", holder, ", format = ", format, ", width = ", width, ", height = ", height, "");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        L.i(TAG, "surfaceDestroyed() called with holder = ", holder, "");
    }

    @Override
    protected void test1() {
        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    protected void test2() {
        surfaceView.setVisibility(View.GONE);
    }

    @Override
    protected void test3() {
        surfaceView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void test4() {
        surfaceViewContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void test5() {
        surfaceViewContainer.setVisibility(View.GONE);
    }

    @Override
    protected void test6() {
        surfaceViewContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void test7() {
        ((ViewGroup) surfaceViewContainer.getParent()).removeAllViews();
    }

    @Override
    protected void test8() {
        surfaceViewContainer.removeAllViews();
    }

    @Override
    protected void test9() {
        if(surfaceView.getParent() == null) {
            surfaceViewContainer.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
}
