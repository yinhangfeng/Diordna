package com.yinhangfeng.popuptest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


public class MainActivity extends ActionBarActivity {
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1OnClick(View v) {
        final PopupWindow popupWindow = new PopupWindow(this);
        final View contentView = getLayoutInflater().inflate(R.layout.popup1, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Log.i(TAG, "btn1OnClick contentView.getLayoutParams()=" + layoutParamToString(contentView.getLayoutParams()));
        popupWindow.setContentView(contentView);
        Log.i(TAG, "btn1OnClick contentView.getLayoutParams()111=" + layoutParamToString(contentView.getLayoutParams()));
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(800);
        Log.i(TAG, "btn1OnClick popupWindow.getBackground()=" + popupWindow.getBackground());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "TouchInterceptor onTouch " + event);
                return false;
            }
        });
        //popupWindow.showAsDropDown(v);
        //popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.i(TAG, "onDismiss");
            }
        });
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "btn1OnClick contentView.getLayoutParams()xxx=" + layoutParamToString(contentView.getLayoutParams()));
                Log.i(TAG, "btn1OnClick popupWindow.getBackground()xxx=" + popupWindow.getBackground());
            }
        }, 2000);
    }

    private static String layoutParamToString(ViewGroup.LayoutParams layoutParams) {
        if(layoutParams == null) {
            return "NULL";
        }
        return layoutParams.getClass() + " width=" + layoutParams.width + " height=" + layoutParams.height;
    }

    public void btnxOnClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
