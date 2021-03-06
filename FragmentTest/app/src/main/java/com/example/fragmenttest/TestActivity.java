package com.example.fragmenttest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Fragment TEST
 * 1. Activity恢复时Fragment attach状态也会恢复 
 * 2.Fragment.setRetainInstance(true)之后恢复的Fragment与原来的Fragment为同一实例
 * 3.Fragment添加于某个View A上但Activity恢复后
 * A没有恢复,那么Fragment不会添加到该View也不会出错(setRetainInstance(true)时会出错)
 * 通过FragmentManager.findFragmentByxx也能找到该Fragment
 * 4.setRetainInstance(true)在状态恢复时不会调用Fragment.onCreate()
 * 5. 对于setRetainInstance(false)(默认) 的Fragment在状态恢复时Transaction().hide()状态不会恢复
 * 6. findFragmentById()对已一个容器中有多个Fragment时只能取到最后添加的一个
 * 7. findFragmentByTag()对于相同的tag能取到最后一个
 * 8. 在executePendingTransactions()执行时不能保证被remove()的Fragment的onDestory立即执行
 *
 * ProgressBar可在工作线程setProgress()
 */
public class TestActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private FragmentManager mFragmentManager;
	private ViewGroup contentView;
	private FrameLayout frameLayout1;
	private LinearLayout frameLayout2;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "before super.onCreate savedInstanceState=" + savedInstanceState);
		super.onCreate(savedInstanceState);// TODO xxxxxxxxxxx null
		Log.v(TAG, "after super.onCreate");

		setContentView(R.layout.activity_main);
		contentView = (ViewGroup) findViewById(android.R.id.content);
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btn3).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		findViewById(R.id.btn5).setOnClickListener(this);
		findViewById(R.id.btn6).setOnClickListener(this);
		findViewById(R.id.btn7).setOnClickListener(this);
		findViewById(R.id.log_view_struct).setOnClickListener(this);
		mFragmentManager = getFragmentManager();
		frameLayout1 = (FrameLayout) findViewById(R.id.frame_layout1);
		frameLayout2 = (LinearLayout) findViewById(R.id.frame_layout2);
		inflater = getLayoutInflater();
	}

	@Override
	protected void onStart() {
		Log.v(TAG, "before super.onStart");
		super.onStart();
		Log.v(TAG, "after super.onStart");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		Log.v(TAG, "before super.onPostCreate");
		super.onPostCreate(savedInstanceState);
		Log.v(TAG, "after super.onPostCreate");
	}

	@Override
	protected void onResume() {
		Log.v(TAG, "before super.onResume");
		super.onResume();
		Log.v(TAG, "after super.onResume");
	}

	@Override
	protected void onPostResume() {
		Log.v(TAG, "before super.onPostResume");
		super.onPostResume();
		Log.v(TAG, "after super.onPostResume");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.v(TAG, "onRestoreInstanceState before super savedInstanceState="
				+ savedInstanceState);
		super.onRestoreInstanceState(savedInstanceState);
		Log.v(TAG, "onRestoreInstanceState after super");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.v(TAG, "onSaveInstanceState before super");
		super.onSaveInstanceState(outState);
		Log.v(TAG, "onSaveInstanceState after super");
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy before super");
		super.onDestroy();
		Log.v(TAG, "onDestroy after super");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "test1");
		menu.add(0, 2, 0, "test2");
		menu.add(0, 3, 0, "test3");
		menu.add(0, 4, 0, "test4");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		R a;
		switch (item.getItemId()) {
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private static void getRecursionViewInfo(StringBuilder sb, View v) {
		sb.append(v.getClass().getSimpleName());
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			sb.append('[');
			sb.append("id=");
			sb.append(v.getId());
			sb.append(" tag=");
			sb.append(v.getTag());
			sb.append(" visible=");
			sb.append(vg.getVisibility());
			sb.append(" ");
			for (int i = 0, len = vg.getChildCount(); i < len; ++i) {
				getRecursionViewInfo(sb, vg.getChildAt(i));
			}
			sb.append("] ");
		} else {
			sb.append('{');
			sb.append("id=");
			sb.append(v.getId());
			sb.append(" tag=");
			sb.append(v.getTag());
			sb.append(" visible=");
			sb.append(v.getVisibility());
			sb.append("} ");
		}
	}

	private static String getRecursionViewInfo(View v) {
		StringBuilder sb = new StringBuilder();
		getRecursionViewInfo(sb, v);
		return sb.toString();
	}

	private void logViewStruct() {
		Log.i(TAG, getRecursionViewInfo(frameLayout1));
		//Log.i(TAG, getRecursionViewInfo(frameLayout2));
	}

	private void findFragmentView() {
		View v = findViewById(R.id.fragment1_root);
		Log.i(TAG, "findFragmentView v=" + v);
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.next:
			startActivity(new Intent(this, TestActivity.class));
			break;
		case R.id.btn1:
			mFragmentManager.beginTransaction().replace(R.id.frame_layout1, new Fragment1(), "111").commit();
			break;
		case R.id.btn2:
			mFragmentManager.beginTransaction().replace(R.id.frame_layout1, new Fragment2(), "222").addToBackStack(null).commit();
			break;
		case R.id.btn3:
			mFragmentManager.beginTransaction().hide(mFragmentManager.findFragmentByTag("111")).add(R.id.frame_layout1, new Fragment3(), "333").addToBackStack(null).commit();
			break;
		case R.id.btn4:
			mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentByTag("111")).remove(mFragmentManager.findFragmentByTag("222")).commit();
			break;
		case R.id.btn5:
			break;
		case R.id.btn6:
			break;
		case R.id.btn7:
			break;
		case R.id.log_view_struct:
			Log.i(TAG, "=====================================================");
			logViewStruct();
			Log.i(TAG, "mFragmentManager.getBackStackEntryCount()="
					+ mFragmentManager.getBackStackEntryCount());
			//Log.i(TAG, "" + mFragmentManager.getFragments());
			Log.i(TAG, "=====================================================");
			break;
		}

	}
}
