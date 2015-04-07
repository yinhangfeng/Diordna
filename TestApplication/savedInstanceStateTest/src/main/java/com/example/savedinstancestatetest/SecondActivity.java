package com.example.savedinstancestatetest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondActivity extends BaseLogActivity implements OnClickListener {
	
	private Test1Fragment frag1;
	private Test1Fragment frag2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("SecondActivity", "before super.onCreate");
		super.onCreate(savedInstanceState);
		Log.e("SecondActivity", "after super.onCreate");
		setContentView(R.layout.activity_second);
		
		Log.i(getClass().getSimpleName(), "onCreate getSupportFragmentManager().getFragments()=" + getSupportFragmentManager().getFragments());
		
//		if(savedInstanceState == null) {
//			//第一次启动
//			frag1 = new Test1Fragment();
//			Bundle args = new Bundle();
//			args.putString("SecondActivity->Test1Fragment", "SecondActivity->Test1Fragment");
//			frag1.setArguments(args);
//			getSupportFragmentManager().beginTransaction().replace(R.id.fc1, frag1).commit();
//		} else {
//			//从原状态恢复启动
//			frag1 = (Test1Fragment) getSupportFragmentManager().findFragmentById(R.id.fc1);
//		}
		Log.i(getClass().getSimpleName(), "onCreate frag1=" + frag1);
		Log.i(getClass().getSimpleName(), "onCreate getSupportFragmentManager().getFragments()=" + getSupportFragmentManager().getFragments());
		
		findViewById(R.id.logViewStruct).setOnClickListener(this);
		findViewById(R.id.replace).setOnClickListener(this);
		
		frag1 = new Test1Fragment();
		frag2 = new Test1Fragment();
		getSupportFragmentManager().beginTransaction().add(R.id.fc1, frag1).add(R.id.fc1, frag2).commit();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.logViewStruct:
			Log.i(getClass().getSimpleName(), "logViewStruct frag1.isAdded()=" + frag1.isAdded() + " frag2.isAdded()" + frag2.isAdded() + " frag1.isVisible()=" + frag1.isVisible() + " frag2.isVisible()=" + frag2.isVisible());
			logViewStruct(android.R.id.content);
			break;
		case R.id.replace:
			//getSupportFragmentManager().beginTransaction().replace(R.id.fc1, new Test1Fragment()).addToBackStack(null).commit();
			if(frag1.isVisible()) {
				frag1.getFragmentManager().beginTransaction().hide(frag1).commit();
				frag2.getFragmentManager().beginTransaction().show(frag2).commit();
			} else {
				frag1.getFragmentManager().beginTransaction().show(frag1).commit();
				frag2.getFragmentManager().beginTransaction().hide(frag2).commit();
			}
			break;
		}
		
	}
}
