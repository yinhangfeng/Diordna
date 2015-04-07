package com.kingoit.actionbartest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by yhf on 2015/1/26.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onCreate savedInstanceState=" + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onRestoreInstanceState savedInstanceState=" + savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onPostCreate savedInstanceState=" + savedInstanceState);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getSimpleName(), "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(getClass().getSimpleName(), "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        Log.i(getClass().getSimpleName(), "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(getClass().getSimpleName(), "onNewIntent intent=" + intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(getClass().getSimpleName(), "onSaveInstanceState outState=" + outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        Log.i(getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(getClass().getSimpleName(), "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(getClass().getSimpleName(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(getClass().getSimpleName(), "onConfigurationChanged newConfig=" + newConfig);
        super.onConfigurationChanged(newConfig);
    }
}
