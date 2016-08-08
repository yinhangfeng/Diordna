package com.example.yhf.statusbartest;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";

    private View container;
    private View colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        container = (View) findViewById(R.id.container);
        colorView = (View) findViewById(R.id.colorView);
        //colorView = container;

        Window window = getWindow();

//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        window.setStatusBarColor(getResources().getColor(R.color.systembar1));

        //DrawLayout 在设置fitsSystemWindows之后的处理
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //魅族上FLAG_TRANSLUCENT_STATUS statusBar是全透明的，也无法更改颜色， 但获取的颜色还是配置的
        if (Build.VERSION.SDK_INT >= 21)
            Log.i(TAG, "onCreate: statusBarColor " + getWindow().getStatusBarColor());
    }

    public void test1(View v) {
            colorView.setBackgroundColor(0xFFEEFFF7);
    }

    public void test2(View v) {
        colorView.setBackgroundColor(0xFF1B6731);
    }

    public void test3(View v) {
        getWindow().setStatusBarColor(0xFFEEFFF7);
    }
}
