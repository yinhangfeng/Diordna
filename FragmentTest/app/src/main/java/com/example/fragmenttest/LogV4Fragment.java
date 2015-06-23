package com.example.fragmenttest;

import com.kingoit.yzt.fragment.StackCallbackFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 测试用Log打印
 */
public class LogV4Fragment extends StackCallbackFragment {
	
	private static int fragNo = 0;
	protected int curFragNo = fragNo++;

	{
		log("new instance curFragNo=" + curFragNo + " fragNo=" + fragNo);
	}

	private String state() {
		return "\nisResumed=" + isResumed() + " isHidden=" + isHidden() + " isAdded=" + isAdded() + " isDetached=" + isDetached() + " isInLayout=" + isInLayout() + " isMenuVisible=" + isMenuVisible() + " isRemoving=" + isRemoving() + " isVisible=" + isVisible();
	}
	
	private void log(String msg) {
		Log.i(getClass().getSimpleName(), msg + " curFragNo=" + curFragNo + state());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		log("onActivityCreated  getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		log("onActivityResult  getArguments()=" + getArguments() + " requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		log("onAttach  getArguments()=" + getArguments() + " activity=" + activity);
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		log("onConfigurationChanged  getArguments()=" + getArguments() + " newConfig=" + newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		log("onCreate  getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log("onCreateView  getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		log("onDestroy  getArguments()=" + getArguments());
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		log("onDestroyView  getArguments()=" + getArguments());
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		log("onDetach  getArguments()=" + getArguments());
		super.onDetach();
	}

	@Override
	public void onPause() {
		log("onPause  getArguments()=" + getArguments());
		super.onPause();
	}

	@Override
	public void onResume() {
		log("onResume  getArguments()=" + getArguments());
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		log("onHiddenChanged hidden=" + hidden);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		log("onSaveInstanceState  getArguments()=" + getArguments() + " outState=" + outState);
		outState.putString("BaseLogFragmentonSaveInstanceState1", "BaseLogFragmentonSaveInstanceState1");
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		log("onViewStateRestored  savedInstanceState=" + savedInstanceState);
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onStart() {
		log("onStart  getArguments()=" + getArguments());
		super.onStart();
	}

	@Override
	public void onStop() {
		log("onStop  getArguments()=" + getArguments());
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		log("onViewCreated  getArguments()=" + getArguments() + " savedInstanceState=" + savedInstanceState);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onStackForward() {
		Log.e(getClass().getSimpleName(), "onStackForward");
	}

	@Override
	public void onStackBackward() {
		Log.e(getClass().getSimpleName(), "onStackBackward");
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [curFragNo=" + curFragNo + state() + "]";
	}
}
