package com.example.directorytest;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

public class DirectoryUtils {
	private static final String TAG = "DirectoryUtils";

	private static File internalRoot;
	private static File externalRoot;

	private static Method getVolumePaths;
	private static Method getVolumeState;
	static {
		try {
			getVolumePaths = StorageManager.class.getMethod("getVolumePaths");
			getVolumeState = StorageManager.class.getMethod("getVolumeState",
					String.class);
		} catch (Exception e) {
			Log.i(TAG, "e=" + Log.getStackTraceString(e));
		}
	}

	public static String[] getVolumePaths(StorageManager storageManager) {
		try {
			return (String[]) getVolumePaths.invoke(storageManager);
		} catch (Exception e) {
			Log.e(TAG, "getVolumePaths e=" + e);
		}
		return null;
	}

	public static String[] getVolumePaths(Context context) {
		return getVolumePaths((StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE));
	}

	public static String getVolumeState(StorageManager storageManager,
			String mountPoint) {
		try {
			return (String) getVolumeState.invoke(storageManager, mountPoint);
		} catch (Exception e) {
			Log.e(TAG, "getVolumeState e=" + e);
		}
		return null;
	}

	public static String getVolumeState(Context context, String mountPoint) {
		return getVolumeState(
				(StorageManager) context
						.getSystemService(Context.STORAGE_SERVICE),
				mountPoint);
	}
	
	/**
	 * 获取当前可用存储卡的根目录File[]
	 * 如果没有可用存储则返回null
	 */
	public static File[] getMountedVolumeFiles(Context context) {
		StorageManager storageManager = (StorageManager) context
		.getSystemService(Context.STORAGE_SERVICE);
		String[] vps = getVolumePaths(storageManager);
		if(vps == null) {
			return null;
		}
		ArrayList<File> fileList = new ArrayList<File>();
		for(String path : vps) {
			if(Environment.MEDIA_MOUNTED.equals(getVolumeState(storageManager, path))) {
				fileList.add(new File(path));
			}
		}
		if(fileList.size() == 0) {
			return null;
		}
		return fileList.toArray(new File[fileList.size()]);
	}

}
