package com.example.fragmenttest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

public class FragmentV41 extends LogV4Fragment implements OnClickListener {
	private static final String TAG = "FragmentV41";
	
	private TextView v1;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate savedInstanceState=" + savedInstanceState);
		if(savedInstanceState != null) {
			Bundle bundle = savedInstanceState.getParcelable("xxx");
			Log.i(TAG, "onCreate bundle=" + bundle + " bundle.getString(\"aaa\")=" + bundle.getString("aaa"));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.btn1).setOnClickListener(this);
		v1 = (TextView) view.findViewById(R.id.v1);
		v1.append(" curFragNo=" + curFragNo);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState putParcelable");
		Bundle bundle = new Bundle();
		bundle.putString("aaa", "aaaaaaaaaa");
		bundle.putInt("bbb", 1111111111);
		outState.putParcelable("xxx", bundle);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		Log.i(TAG, "onCreateAnimation enter=" + enter);
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			if(v1.getVisibility() == View.VISIBLE) {
				v1.setVisibility(View.GONE);
			} else {
				v1.setVisibility(View.VISIBLE);
			}
			break;

		default:
			break;
		}
		
	}

}
