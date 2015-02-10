package com.yinhangfeng.diordna;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private MyCanvasView myCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvasView = (MyCanvasView) findViewById(R.id.my_canvas_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.test1:
            test1();
            break;
        case R.id.test2:
            test2();
            break;
        case R.id.test3:
            test3();
            break;
        case R.id.test4:
            break;
        case R.id.test5:
            break;
        case R.id.test6:
            break;
        case R.id.test7:
            break;
        case R.id.test8:
            break;
        case R.id.test9:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void test1() {
        myCanvasView.invalidate();
    }

    private void test2() {
        myCanvasView.requestLayout();
    }

    private void test3() {}

}