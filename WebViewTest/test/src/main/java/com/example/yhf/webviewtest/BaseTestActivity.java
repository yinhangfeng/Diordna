package com.example.yhf.webviewtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class BaseTestActivity extends AppCompatActivity {
	
	protected File testRootFile = new File(Environment.getExternalStorageDirectory(), "TEST");

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "test1");
		menu.add(0, 2, 0, "test2");
		menu.add(0, 3, 0, "test3");
		menu.add(0, 4, 0, "test4");
		menu.add(0, 5, 0, "test5");
		menu.add(0, 6, 0, "test6");
		menu.add(0, 7, 0, "test7");
		menu.add(0, 8, 0, "test8");
		menu.add(0, 9, 0, "test9");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(getClass().getSimpleName(), "test" + item.getItemId() + " start");
		switch (item.getItemId()) {
		case 1:
			test1();
			break;
		case 2:
			test2();
			break;
		case 3:
			test3();
			break;
		case 4:
			test4();
			break;
		case 5:
			test5();
			break;
		case 6:
			test6();
			break;
		case 7:
			test7();
			break;
		case 8:
			test8();
			break;
		case 9:
			test9();
			break;
		}
		return true;
	}

	protected void test9() {

	}

	protected void test8() {

	}

	protected void test7() {

	}

	protected void test6() {

	}

	protected void test5() {

	}

	protected void test3() {

	}

	protected void test4() {

	}

	protected void test2() {

	}

	protected void test1() {

	}

}
