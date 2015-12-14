package com.example.yinhf.assetstest;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.commonlibrary.BaseTestActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 1.打包之后 assets中的空文件夹会被删除
 * 2.没法方便的判断是文件夹还是文件
 */
public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void test1() throws Exception {
        AssetManager assetManager = getAssets();
        String[] files1 = assetManager.list("");
        Log.i(TAG, "test1 files1=" + Arrays.toString(files1));
        String[] files2 = assetManager.list("aaa");
        Log.i(TAG, "test1 files2=" + Arrays.toString(files2));
        String[] files3 = assetManager.list("222");
        Log.i(TAG, "test1 files3=" + Arrays.toString(files3));
        String[] files4 = assetManager.list("xxx");
        Log.i(TAG, "test1 files4=" + Arrays.toString(files4));
        InputStream is = assetManager.open("aaa");
    }
}
