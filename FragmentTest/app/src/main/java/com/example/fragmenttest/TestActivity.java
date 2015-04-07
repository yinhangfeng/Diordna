package com.example.fragmenttest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class TestActivity extends Activity implements OnClickListener {
	private static final String TAG = "TestActivity";
	private FragmentManager mFragmentManager;
	private Fragment1 f1;
	private Fragment2 f2;
	private Fragment3 f3;
	private ViewGroup contentView;
	private FrameLayout frameLayout1;
	private LinearLayout frameLayout2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contentView = (ViewGroup) findViewById(android.R.id.content);
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btn3).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		findViewById(R.id.btn5).setOnClickListener(this);
		findViewById(R.id.btn6).setOnClickListener(this);
		findViewById(R.id.btn7).setOnClickListener(this);
		findViewById(R.id.log_view_struct).setOnClickListener(this);
		mFragmentManager = getFragmentManager();
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		Log.i(TAG, "FragmentManager: " + mFragmentManager.getClass().getName()
				+ " FragmentTransaction: " + ft.getClass().getName());
		ft.commit();
		f1 = new Fragment1();
		f2 = new Fragment2();
		f3 = new Fragment3();
		frameLayout1 = (FrameLayout) findViewById(R.id.frame_layout1);
		frameLayout2 = (LinearLayout) findViewById(R.id.frame_layout2);

		Log.i(TAG, "R.bool.ispad=" + getResources().getBoolean(R.bool.ispad));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "remove myFrameLayout");
		menu.add(0, 2, 0, "f1 add to frame2");
		menu.add(0, 3, 0, "new myFrameLayout");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			MyFrameLayout myFrameLayout = (MyFrameLayout) findViewById(R.id.myframe);
			if (myFrameLayout == null) {
				Log.w(TAG, "onMenuItemSelected 1 myFrameLayout=null");
				break;
			}
			((ViewGroup) myFrameLayout.getParent()).removeView(myFrameLayout);
			return true;
		case 2:
			mFragmentManager.beginTransaction().add(R.id.frame_layout2, f1)
					.commit();
			return true;
		case 3:
			getLayoutInflater().inflate(R.layout.my_frame_layout,
					(ViewGroup) findViewById(R.id.frame_container));
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
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
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.btn1:
			mFragmentManager
					.beginTransaction()
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit)
					.replace(R.id.frame_layout1, f1, "f1").addToBackStack(null)
					.commit();
			break;
		case R.id.btn2:
			mFragmentManager
					.beginTransaction()
					.add(R.id.frame_layout2, f3)
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit)
					.replace(R.id.frame_layout1, f2).addToBackStack(null)

					.commit();
			break;
		case R.id.btn3:
			mFragmentManager
					.beginTransaction()
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit).show(f1)
					.addToBackStack(null).commit();
			break;
		case R.id.btn4:
			mFragmentManager
					.beginTransaction()
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit).hide(f1)
					.addToBackStack(null).commit();
			break;
		case R.id.btn5:
			mFragmentManager
					.beginTransaction()
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit).attach(f1)
					.addToBackStack(null).commit();
			break;
		case R.id.btn6:
			mFragmentManager
					.beginTransaction()
					.setCustomAnimations(R.animator.fragment_slide_left_enter,
							R.animator.fragment_slide_left_exit,
							R.animator.fragment_slide_right_enter,
							R.animator.fragment_slide_right_exit).detach(f1)
					.addToBackStack(null).commit();
			break;
		case R.id.btn7:

			break;
		case R.id.log_view_struct:
			logViewStruct();
			Log.i(TAG, "mFragmentManager.getBackStackEntryCount()="
					+ mFragmentManager.getBackStackEntryCount());
			break;
		}

	}
}
