package com.kingoit.yzt.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * 对FragmentManager的封装 每个YztFragmentManager实例有自己的回退栈
 */
public class YztFragmentManager {
	static final String TAG = "YztFragmentManager";
	
	private static final String SAVE_STATE_KEY = "YztFragmentManager_SAVE_STATE_KEY";
	
	private FragmentManager mFragmentManager;
	private Handler handler = new Handler(Looper.getMainLooper());
	private ArrayList<Fragment> fragmentStack = new ArrayList<Fragment>();
	//当前栈顶
	private int stackTop = -1;
	
	public YztFragmentManager(FragmentManager fragmentManager) {
		mFragmentManager = fragmentManager;
	}

	public FragmentManager getmFragmentManager() {
		return mFragmentManager;
	}
	
	/**
	 * 向栈中添加一个Fragment成为新的栈顶(FragmentTransaction.add())，原来的栈顶隐藏(FragmentTransaction.hide())
	 * @param containerViewId Fragment添加的容器id
	 * @param fragment 要添加的Fragment
	 * @param tag Fragment的TAG
	 * @return 新入站的Fragment在栈中的编号
	 */
	public int push(int containerViewId, Fragment fragment, String tag) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		if(stackTop >= 0) {
			Fragment lastTopFrag = fragmentStack.get(stackTop);
			if(lastTopFrag instanceof StackCallbackFragment) {
				((StackCallbackFragment) lastTopFrag).onStackForward();
			}
			ft.hide(fragmentStack.get(stackTop));
		}
		fragmentStack.add(++stackTop, fragment);
		ft.add(containerViewId, fragment, tag);
		ft.commit();
		return stackTop;
	}
	
	/**
	 * 向栈中添加一个Fragment成为新的栈顶(FragmentTransaction.add())，原来的栈顶隐藏(FragmentTransaction.hide())
	 * @param containerViewId Fragment添加的容器id
	 * @param fragment 要添加的Fragment
	 * @return 新入站的Fragment在栈中的编号
	 */
	public int push(int containerViewId, Fragment fragment) {
		return push(containerViewId, fragment, null);
	}
	
	/**
	 * 将栈顶Fragment弹出(FragmentTransaction.remove())，原来的下一个成为新栈顶(FragmentTransaction.show())
	 */
	public void pop() {
		if(stackTop < 0) {
			return;
		}
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		ft.remove(fragmentStack.remove(stackTop--));
		if(stackTop >= 0) {
			final Fragment newTopFrag = fragmentStack.get(stackTop);
			ft.show(newTopFrag);
			ft.commit();
			if(newTopFrag instanceof StackCallbackFragment) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						((StackCallbackFragment) newTopFrag).onStackBackward();
					}
				});
			}
		} else {
			ft.commit();
		}
	}
	
	/**
	 * 将栈中的所有Fragment弹出(FragmentTransaction.remove())
	 */
	public void popAll() {
		if(stackTop < 0) {
			return;
		}
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		for(int i = stackTop; i >=0; --i) {
			ft.remove(fragmentStack.get(i));
		}
		stackTop = -1;
		fragmentStack.clear();
		ft.commit();
	}
	
	/**
	 * 弹出Fragment直到遇到栈中位置为id Fragment，该Fragment是否弹出取决于inclusive
	 * @param id
	 * @param inclusive false:弹出不包括id对应的Fragment true:包括
	 */
	public void pop(int id, boolean inclusive) {
		if(stackTop < 0 || id < 0 || id > stackTop) {
			return;
		}
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		int index = stackTop;
		for(; index > id; --index) {
			ft.remove(fragmentStack.remove(index));
		}
		if(inclusive) {
			ft.remove(fragmentStack.remove(index--));
		}
		stackTop = index;
		if(stackTop >= 0) {
			final Fragment newTopFrag = fragmentStack.get(stackTop);
			ft.show(newTopFrag);
			ft.commit();
			if(newTopFrag instanceof StackCallbackFragment) {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						((StackCallbackFragment) newTopFrag).onStackBackward();
					}
				});
			}
		} else {
			ft.commit();
		}
	}
	
	/**
	 * 取当前栈顶的Fragment
	 */
	public Fragment peek() {
		if(stackTop < 0) {
			return null;
		}
		return fragmentStack.get(stackTop);
	}
	
	/**
	 * 用新的Fragment替换原栈顶，原来的栈顶删除(FragmentTransaction.remove())
	 * 如果当前栈为空则效果跟pushFragment一样
	 * @param containerViewId Fragment添加的容器id
	 * @param fragment 要添加的Fragment
	 * @param tag Fragment的TAG
	 * @return 新入栈的Fragment在栈中的编号
	 */
	public int replaceTop(int containerViewId, Fragment fragment, String tag) {
		if(stackTop < 0) {
			return push(containerViewId, fragment, tag);
		}
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		ft.remove(fragmentStack.get(stackTop));
		fragmentStack.set(stackTop, fragment);
		ft.add(containerViewId, fragment, tag);
		ft.commit();
		return stackTop;
	}
	
	/**
	 * 用新的Fragment替换原栈顶，原来的栈顶删除(FragmentTransaction.remove())
	 * 如果当前栈为空则效果跟pushFragment一样
	 * @param containerViewId Fragment添加的容器id
	 * @param fragment 要添加的Fragment
	 * @return 新入栈的Fragment在栈中的编号
	 */
	public int replaceTop(int containerViewId, Fragment fragment) {
		return replaceTop(containerViewId, fragment, null);
	}
	
	/**
	 * 获取当前栈顶Fragment编号
	 * @return id
	 */
	public int getTopId() {
		return stackTop;
	}
	
	/**
	 * 栈是否为空
	 */
	public boolean isEmpty() {
		return stackTop < 0;
	}
	
	public void saveState(Bundle outState) {
		if(stackTop < 0) {
			return;
		}
		Bundle state = new Bundle();
		state.putInt("top", stackTop);
		for(int i = 0; i <= stackTop; ++i) {
			mFragmentManager.putFragment(state, "f" + i, fragmentStack.get(i));
		}
		outState.putParcelable(SAVE_STATE_KEY, state);
	}
	
	public void restoreState(Bundle savedState) {
		if(stackTop >= 0) {
			Log.w(TAG, "restoreState stackTop >= 0");
			return;
		}
		if(savedState == null) {
			return;
		}
		Bundle state = savedState.getParcelable(SAVE_STATE_KEY);
		if(state != null) {
			stackTop = state.getInt("top");
			for(int i = 0; i <= stackTop; ++i) {
				fragmentStack.add(mFragmentManager.getFragment(state, "f" + i));
			}
			//由于对于setRetainInstance(false)(默认)的Fragment自动状态恢复时hide()状态不会恢复
			//所以这里需将非栈顶Fragment重新设为hide()
			if(stackTop > 0) {
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				for(int i = 0; i < stackTop; ++i) {
					ft.hide(fragmentStack.get(i));
				}
				ft.commit();
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("YztFragmentManager [ stackTop=")
		.append(stackTop)
		.append(", Fragments [");
		for(int i = 0; i <= stackTop; ++i) {
			sb.append(fragmentStack.get(i))
			.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]]");
		return sb.toString();
	}
}
