package com.example.dialogtest;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	
	private View activityContent;
	private Dialog dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activityContent = findViewById(android.R.id.content);
		findViewById(R.id.alert_dlg).setOnClickListener(this);
		findViewById(R.id.tpd).setOnClickListener(this);
		findViewById(R.id.dpd).setOnClickListener(this);
		findViewById(R.id.set).setOnClickListener(this);
		
		findViewById(R.id.test1).setOnClickListener(this);
		findViewById(R.id.test2).setOnClickListener(this);
		findViewById(R.id.test3).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert_dlg:
			
			break;
		case R.id.tpd:
			timePickerDialog();
			break;
		case R.id.dpd:
			datePickerDialog();
			break;
		case R.id.set:
			set();
			break;
		case R.id.test1:
			test1();
			break;
		case R.id.test2:
			test2();
			break;
		case R.id.test3:
			test3();
			break;
		}
		
	}
	
	private void timePickerDialog() {
		dlg = new TimePickerDialog(this, null, 1, 1, true);
		showDlgInViewCenter(dlg, findViewById(R.id.test_frame));
    	
	}
	
	private void datePickerDialog() {
		dlg = new DatePickerDialog(this, null, 2014, 10, 1);
		Window window = dlg.getWindow();
    	WindowManager.LayoutParams layoutParams = window.getAttributes();
    	layoutParams.gravity = Gravity.CENTER;
    	layoutParams.x = 0;
    	layoutParams.y = 0;
    	dlg.show();
	}
	
	private void set() {
		if(dlg != null) {
			Window window = dlg.getWindow();
	    	WindowManager.LayoutParams layoutParams = window.getAttributes();
	    	Log.i(TAG, "timePickerDialog layoutParams=" + layoutParams);
	    	layoutParams.x = 100;
	    	layoutParams.y = 130;
	    	window.setAttributes(layoutParams);
		}
	}
	
	private void test1() {
		View contentView = getLayoutInflater().inflate(R.layout.popup1, null);
		//PopupWindow pop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		//PopupWindow pop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(600, 500);
		mlp.topMargin = 50;
		contentView.setLayoutParams(mlp);
		PopupWindow pop = new PopupWindow(contentView, 700, 550);
		pop.setBackgroundDrawable(new ColorDrawable(0x88AEEEEE));
		pop.setAnimationStyle(R.style.pop_anim1);
		pop.setFocusable(true);
		pop.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				Log.i(TAG, "test1 pop onDismiss");
			}
		});
		pop.setOutsideTouchable(true);
		pop.setTouchable(true);//为false时点击位于pop里面是也会触发dismiss
		
		pop.showAtLocation(activityContent, Gravity.CENTER, 0, 0);
	}
	
	private void test2() {
		View contentView = getLayoutInflater().inflate(R.layout.popup1, null);
		//PopupWindow pop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		//PopupWindow pop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		MyPopupWindow pop = new MyPopupWindow(contentView, 700, 550);
		pop.setBackgroundDrawable(new ColorDrawable(0x88AEEEEE));
		pop.setAnimationStyle(R.style.pop_anim1);
		pop.setFocusable(true);
		pop.setOnDismissListener(new MyPopupWindow.OnDismissListener() {
			
			@Override
			public void onDismiss() {
				Log.i(TAG, "test2 mypop onDismiss");
			}
		});
		pop.setOutsideTouchable(true);
		pop.setTouchable(true);//为false时点击位于pop里面是也会触发dismiss
		
		pop.showAtLocation(activityContent, Gravity.CENTER, 0, 0);
	}
	
	private void test3() {
		View contentView = getLayoutInflater().inflate(R.layout.dlg1, null);
		AlertDialog dlg = new AlertDialog.Builder(this)
		.setView(contentView)
		.setCancelable(true)
		.create();
		dlg.setCanceledOnTouchOutside(true);
		Window window = dlg.getWindow();
    	WindowManager.LayoutParams layoutParams = window.getAttributes();
    	layoutParams.verticalMargin = 300;
    	dlg.show();
	}
	
	public static void showDlgInViewCenter(Dialog dlg, View v) {
		DisplayMetrics dm = v.getResources().getDisplayMetrics();
		int[] viewLoaction = new int[2];
		v.getLocationOnScreen(viewLoaction);
		Window window = dlg.getWindow();
    	WindowManager.LayoutParams layoutParams = window.getAttributes();
    	Log.i(TAG, "showDlgInViewCenter dm=" + dm + " viewLoaction=" + Arrays.toString(viewLoaction) + " layoutParams=" + layoutParams + " v.getWidth()=" + v.getWidth() + " v.getHeight()=" + v.getHeight());
    	layoutParams.gravity = Gravity.CENTER;
    	layoutParams.x = viewLoaction[0] + (v.getWidth() - dm.widthPixels) / 2;
    	layoutParams.y = viewLoaction[1] + (v.getHeight() - dm.heightPixels) / 2;
    	Log.i(TAG, "showDlgInViewCenter layoutParams.x=" + layoutParams.x + " layoutParams.y=" + layoutParams.y);
    	dlg.show();
	}
}
