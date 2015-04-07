package com.example.fragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 测试用Log打印
 */
public class LogFragment extends Fragment {
	
	private static int fragNo = 0;
	protected int curFragNo = fragNo++;

	{
		Log.d(getClass().getSimpleName(), "new instance curFragNo=" + curFragNo + " fragNo=" + fragNo);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(getClass().getSimpleName(), "onActivityCreated curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(getClass().getSimpleName(), "onActivityResult curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(getClass().getSimpleName(), "onAttach curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " activity=" + activity);
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d(getClass().getSimpleName(), "onConfigurationChanged curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " newConfig=" + newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(getClass().getSimpleName(), "onCreate curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(getClass().getSimpleName(), "onCreateView curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		Log.d(getClass().getSimpleName(), "onDestroy curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.d(getClass().getSimpleName(), "onDestroyView curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d(getClass().getSimpleName(), "onDetach curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.d(getClass().getSimpleName(), "onPause curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d(getClass().getSimpleName(), "onResume curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(getClass().getSimpleName(), "onSaveInstanceState curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " outState=" + outState);
		outState.putString("BaseLogFragmentonSaveInstanceState1", "BaseLogFragmentonSaveInstanceState1");
		super.onSaveInstanceState(outState);
	}
	
	
	
//	@Override
//	public void onViewStateRestored(Bundle savedInstanceState) {
//		Log.d(getClass().getSimpleName(), "onViewStateRestored curFragNo=" + curFragNo);
//		super.onViewStateRestored(savedInstanceState);
//	}

//	@Override
//	public void onViewStateRestored(Bundle savedInstanceState) {
//		Log.d(getClass().getSimpleName(), "onViewStateRestored curFragNo=" + curFragNo);
//		super.onViewStateRestored(savedInstanceState);
//	}

	@Override
	public void onStart() {
		Log.d(getClass().getSimpleName(), "onStart curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d(getClass().getSimpleName(), "onStop curFragNo=" + curFragNo + " getArguments()=" + getArguments());
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.d(getClass().getSimpleName(), "onViewCreated curFragNo=" + curFragNo + " getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [curFragNo=" + curFragNo + "]";
	}
}
