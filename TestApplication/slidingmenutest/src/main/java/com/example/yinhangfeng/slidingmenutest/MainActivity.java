package com.example.yinhangfeng.slidingmenutest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SlidingPaneLayout slidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Log.i(TAG, "onPanelSlide() called with " + "panel = [" + panel + "], slideOffset = [" + slideOffset + "]");
            }

            @Override
            public void onPanelOpened(View panel) {
                Log.i(TAG, "onPanelOpened() called with " + "panel = [" + panel + "]");
            }

            @Override
            public void onPanelClosed(View panel) {
                Log.i(TAG, "onPanelClosed() called with " + "panel = [" + panel + "]");
            }
        });
        slidingPaneLayout.setCoveredFadeColor(Color.GREEN);
        slidingPaneLayout.setSliderFadeColor(Color.BLUE);
        slidingPaneLayout.setShadowResourceLeft(R.drawable.drawer_shadow);
        slidingPaneLayout.setShadowResourceRight(R.drawable.drawer_shadow);
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
