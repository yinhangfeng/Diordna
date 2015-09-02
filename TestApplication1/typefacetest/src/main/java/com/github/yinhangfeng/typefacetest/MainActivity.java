package com.github.yinhangfeng.typefacetest;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate Typeface.DEFAULT=" + Typeface.DEFAULT + " Typeface.DEFAULT_BOLD=" + Typeface.DEFAULT_BOLD + " Typeface.SANS_SERIF=" + Typeface.SANS_SERIF + " Typeface.SERIF=" + Typeface.SERIF + " Typeface.MONOSPACE=" + Typeface.MONOSPACE );

        TextView tv = (TextView) findViewById(R.id.text1);

        Log.i(TAG, "onCreate tv.getTypeface=" + tv.getTypeface());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
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
}
