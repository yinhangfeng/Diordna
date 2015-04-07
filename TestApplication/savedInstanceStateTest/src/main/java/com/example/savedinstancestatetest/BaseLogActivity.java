package com.example.savedinstancestatetest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class BaseLogActivity extends FragmentActivity {
	
	{
		Log.d(getClass().getSimpleName(), "new instance");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getClass().getSimpleName(), "onCreate savedInstanceState=" + savedInstanceState + " getIntent()=" + getIntent());
		if(getIntent() != null) {
			Log.i(getClass().getSimpleName(), "onCreate getIntent().getExtras()=" + getIntent().getExtras());
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(getClass().getSimpleName(), "onNewIntent intent=" + intent + " getIntent()=" + getIntent());
		super.onNewIntent(intent);
	}

	@Override
	protected void onStart() {
		Log.i(getClass().getSimpleName(), "onStart" + " getIntent()=" + getIntent());
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.i(getClass().getSimpleName(), "onRestart" + " getIntent()=" + getIntent());
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(getClass().getSimpleName(), "onResume" + " getIntent()=" + getIntent());
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(getClass().getSimpleName(), "onPause" + " getIntent()=" + getIntent());
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(getClass().getSimpleName(), "onStop" + " getIntent()=" + getIntent());
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(getClass().getSimpleName(), "onDestroy" + " getIntent()=" + getIntent());
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.i(getClass().getSimpleName(), "onSaveInstanceState" + " getIntent()=" + getIntent());
		outState.putString("BaseLogActivityonSaveInstanceState1", "BaseLogActivityonSaveInstanceState1");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.i(getClass().getSimpleName(), "onRestoreInstanceState savedInstanceState=" + savedInstanceState + " getIntent()=" + getIntent());
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i(getClass().getSimpleName(), "onConfigurationChanged newConfig=" + newConfig + " getIntent()=" + getIntent());
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(getClass().getSimpleName(), "onActivityResult requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data + " getIntent()=" + getIntent());
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private static void getRecursionViewInfo(StringBuilder sb, View v) {
		sb.append(v.getClass().getSimpleName());
		if(v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			sb.append('[');
			sb.append("id=");
			sb.append(v.getId());
			sb.append(" tag=");
			sb.append(v.getTag());
			sb.append(" ");
			for(int i = 0, len = vg.getChildCount(); i < len; ++i) {
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
		if(v == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		getRecursionViewInfo(sb, v);
		return sb.toString();
	}
	
	protected void logViewStruct(int id) {
		Log.i(getClass().getSimpleName(), getRecursionViewInfo(findViewById(id)));
	}

}
