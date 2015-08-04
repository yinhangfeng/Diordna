package com.example.commonlibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yhf on 2015/1/14.
 */
public class T {

    private static Context sContext;

    private T() {}

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void showShort(CharSequence msg) {
        Toast.makeText(sContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(CharSequence msg) {
        Toast.makeText(sContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(int msg) {
        Toast.makeText(sContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int msg) {
        Toast.makeText(sContext, msg, Toast.LENGTH_LONG).show();
    }

}
