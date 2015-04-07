package com.kingoit.yzt.fragment;

import android.support.v4.app.Fragment;

/**
 * 继承此Fragment 可实现用YztFragmentManager管理时特殊事件的回调
 */
public class StackCallbackFragment extends Fragment {
	
	/**
	 * 当前Fragment不再是栈顶,有新的Fragment入栈
	 */
	public void onStackForward() {
		
	}
	
	/**
	 * 当前Fragment重新成为栈顶
	 */
	public void onStackBackward() {
		
	}

}
