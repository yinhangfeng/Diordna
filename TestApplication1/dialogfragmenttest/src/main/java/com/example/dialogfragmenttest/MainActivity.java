package com.example.dialogfragmenttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View v) {
        MyDialogFragment myDialogFragment = new MyDialogFragment();

        myDialogFragment.setStyle(0, R.style.FullScreenDialogAnimatedFade);
        myDialogFragment.setCancelable(false);
        myDialogFragment.show(getFragmentManager(), "xxx1");
    }

    public void test2(View v) {

    }

    public void test3(View v) {

    }
}
