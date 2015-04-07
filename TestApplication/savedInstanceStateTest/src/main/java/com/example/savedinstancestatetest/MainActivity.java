package com.example.savedinstancestatetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseLogActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.second).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.second:
			Intent intent = new Intent(this, SecondActivity.class);
			intent.putExtra("xxxx", "xxxxxxxxxxxxxxxxxx");
			startActivity(intent);
			break;
		}
		
	}
}
