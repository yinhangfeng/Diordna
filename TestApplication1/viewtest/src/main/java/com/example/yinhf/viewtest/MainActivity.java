package com.example.yinhf.viewtest;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = (MyView) findViewById(R.id.my_view);
    }

    public void test1OnClick(View v) {
        Log.i(TAG, "test1OnClick: myView.invalidate");
        myView.invalidate();
        myView.postInvalidate();
        ViewCompat.postInvalidateOnAnimation(myView);
    }
}
