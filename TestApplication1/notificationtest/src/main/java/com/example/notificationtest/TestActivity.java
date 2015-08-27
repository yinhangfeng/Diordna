package com.example.notificationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by yhf on 2015/8/4.
 */
public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        String s = "intent=null";
        if(intent != null) {
            s = "xxx: " + intent.getStringExtra("xxx");
        }
        Log.i(TAG, "onCreate " + s + " savedInstanceState=" + savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String s = "intent=null";
        if(intent != null) {
            s = "xxx: " + intent.getStringExtra("xxx");
        }
        Log.i(TAG, "onNewIntent " + s);
    }
}
