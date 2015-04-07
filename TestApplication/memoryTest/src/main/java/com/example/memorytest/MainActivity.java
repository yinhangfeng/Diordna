package com.example.memorytest;

import java.util.ArrayList;

import com.example.commonlibrary.BaseTestActivity;
import com.example.commonlibrary.CommonUtils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends BaseTestActivity {

	private static final String TAG = null;
	private static final ArrayList<Block> BLOCKS = new ArrayList<MainActivity.Block>();
	static class Block {
		byte[] data;
		
		public Block() {
			data = new byte[1024 * 1024 * 32];
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void test1() {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("memory info:");
		Runtime runtime = Runtime.getRuntime();
		sb.append("\nRuntime==================");
		sb.append("\nRuntime.maxMemory()=").append(runtime.maxMemory())
		.append("  ").append(CommonUtils.getReadableSize(runtime.maxMemory()))
		.append("\nRuntime.freeMemory()=").append(runtime.freeMemory())
		.append("  ").append(CommonUtils.getReadableSize(runtime.freeMemory()))
		.append("\nRuntime.totalMemory()=").append(runtime.totalMemory())
		.append("  ").append(CommonUtils.getReadableSize(runtime.totalMemory()));
		
		sb.append("\nActivityManager====================");
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		sb.append("\nActivityManager.getMemoryClass()=").append(activityManager.getMemoryClass());
		MemoryInfo memoryInfo = new MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		sb.append("\nMemoryInfo: availMem=").append(memoryInfo.availMem).append("  ").append(CommonUtils.getReadableSize(memoryInfo.availMem))
		.append("\nMemoryInfo threshold=").append(memoryInfo.threshold).append("  ").append(CommonUtils.getReadableSize(memoryInfo.threshold))
		.append("\nMemoryInfo lowMemory=").append(memoryInfo.lowMemory);
		
		sb.append("\nandroid.os.Debug================");
		sb.append("\nDebug.getNativeHeapFreeSize()=").append(Debug.getNativeHeapFreeSize())
		.append("  ").append(CommonUtils.getReadableSize(Debug.getNativeHeapFreeSize()))
		.append("\nDebug.getNativeHeapAllocatedSize()=").append(Debug.getNativeHeapAllocatedSize())
		.append("  ").append(CommonUtils.getReadableSize(Debug.getNativeHeapAllocatedSize()))
		.append("\nDebug.getNativeHeapSize()=").append(Debug.getNativeHeapSize())
		.append("  ").append(CommonUtils.getReadableSize(Debug.getNativeHeapSize()));
		android.os.Debug.MemoryInfo memoryInfo1 = new android.os.Debug.MemoryInfo();
		Debug.getMemoryInfo(memoryInfo1);
		Log.i(TAG, sb.toString());
		((TextView) findViewById(R.id.info)).setText(sb.toString());
	}
	
	@Override
	protected void test2() {
		try {
			BLOCKS.add(new Block());
		} catch(Throwable e) {
			Log.wtf(TAG, Log.getStackTraceString(e));
		}
		Log.i(TAG, "BLOCKS.size()=" + BLOCKS.size());
	}
	
	
}
