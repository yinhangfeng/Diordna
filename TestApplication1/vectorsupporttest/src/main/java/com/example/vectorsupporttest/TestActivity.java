package com.example.vectorsupporttest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.ViewGroup;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SwitchCompat switchCompat = new SwitchCompat(this);

        ((ViewGroup) findViewById(R.id.container)).addView(switchCompat);
    }
}
