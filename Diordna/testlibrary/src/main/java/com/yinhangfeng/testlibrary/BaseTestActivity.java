package com.yinhangfeng.testlibrary;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public abstract class BaseTestActivity extends ActionBarActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.test1) {
            test1();
        } else if(id == R.id.test2) {
            test2();
        } else if(id == R.id.test3) {
            test3();
        } else if(id == R.id.test4) {
            test4();
        } else if(id == R.id.test5) {
            test5();
        } else if(id == R.id.test6) {
            test6();
        } else if(id == R.id.test7) {
            test7();
        } else if(id == R.id.test8) {
            test8();
        } else if(id == R.id.test9) {
            test9();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void test1() {
    }

    protected void test2() {
    }

    protected void test3() {
    }

    protected void test4() {
    }

    protected void test5() {
    }

    protected void test6() {
    }

    protected void test7() {
    }

    protected void test8() {
    }

    protected void test9() {
    }

}
