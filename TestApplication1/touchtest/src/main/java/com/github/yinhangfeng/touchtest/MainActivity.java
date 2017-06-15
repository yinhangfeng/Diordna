package com.github.yinhangfeng.touchtest;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch(ev.getAction()) {
//        case MotionEvent.ACTION_DOWN:
//            Log.i(TAG, "dispatchKeyEvent ACTION_DOWN");
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "ACTION_DOWN before sleep post");
//                }
//            });
//            SystemClock.sleep(2000);
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "ACTION_DOWN after sleep post");
//                }
//            });
//            Log.i(TAG, "dispatchKeyEvent ACTION_DOWN END");
//            break;
//        case MotionEvent.ACTION_MOVE:
//            Log.i(TAG, "dispatchKeyEvent ACTION_MOVE");
//            break;
//        case MotionEvent.ACTION_UP:
//            Log.i(TAG, "dispatchKeyEvent ACTION_UP");
//            break;
//        }
//        return true;
//    }
}
