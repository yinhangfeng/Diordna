package com.example.darkwanderer.themetest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int position = getIntent().getIntExtra("position", 0);
        setTheme(MainActivity.THEME_RESS[position]);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle(MainActivity.THEME_NAMES[position]);
        ((TextView) findViewById(R.id.title)).setText(MainActivity.THEME_NAMES[position]);
    }

}
