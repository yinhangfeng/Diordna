package com.example.dialogtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.commonlibrary.BaseTestActivity;

import java.lang.reflect.Method;

public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate getApplication() == getApplicationContext(): " + (getApplication() == getApplicationContext()));

        FrameLayout viewContainer = (FrameLayout) findViewById(R.id.view_container);
        //LayoutInflater的Context跟Theme有关
        View testLayout = LayoutInflater.from(this).inflate(R.layout.layout_test, null);
        editText = (EditText) testLayout.findViewById(R.id.edit_text);
        Log.i(TAG, "test1 editText.getContext=" + editText.getContext());
        viewContainer.addView(testLayout, -1, -1);
        Log.i(TAG, "test1 editText.getContext=" + editText.getContext());
    }

    @Override
    protected void test1() {
        Log.i(TAG, "test1 editText.getContext=" + editText.getContext());
    }

    private AlertDialog alertDialog;

    @Override
    protected void test2() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("xxxxx");
        dlg.setCancelable(false);
        dlg.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "PositiveButton onClick");
            }
        });
        dlg.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "NegativeButton onClick");
            }
        });
//        dlg.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                Log.i(TAG, "onKey event=" + event);
//                return true;
//            }
//        });
        dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.i(TAG, "onCancel");
            }
        });
        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i(TAG, "onDismiss");
            }
        });
        alertDialog = dlg.show();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                alertDialog.dismiss();
//            }
//        }, 3000);
    }

    @Override
    protected void test3() {
        if(alertDialog != null) {
            alertDialog.dismiss();
        }
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
