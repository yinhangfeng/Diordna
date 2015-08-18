package com.example.dialogtest;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            showPop();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void alert(View v) {
        showPop();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                TextView tv = new TextView(MainActivity.this);
                tv.setText("xxxxxxxxxxxx");
                tv.setTextSize(20);
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("xxx").setView(tv).show();

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams attr = window.getAttributes();
                        Log.i(TAG, "onClick window.getAttributes()=" + attr);
                        Log.i(TAG, "onClick token=" + attr.token + " " + attr.getClass());
                        Log.i(TAG, "onCreate getWindowToken=" + getWindow().getDecorView().getWindowToken() + " " + getWindow().getDecorView().getWindowToken().getClass());
                        showPop();
                    }
                });
            }
        });
    }

    private void showPop() {
        //实现全屏遮罩的加载框
        View v = getLayoutInflater().inflate(R.layout.dialog_loading, null);
        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        try {
            Method setWindowLayoutType = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", int.class);
            setWindowLayoutType.setAccessible(true);
            //让popupwindow显示于所有alert之上
            setWindowLayoutType.invoke(popupWindow, WindowManager.LayoutParams.LAST_APPLICATION_WINDOW - 1);

        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            Method showAtLocation = PopupWindow.class.getDeclaredMethod("showAtLocation", IBinder.class, int.class, int.class, int.class);
            showAtLocation.setAccessible(true);
            showAtLocation.invoke(popupWindow, null, Gravity.NO_GRAVITY, 0, 0);
            //popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, 0);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);

    }
}
