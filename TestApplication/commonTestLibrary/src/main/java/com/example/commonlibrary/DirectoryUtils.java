package com.example.commonlibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

/**
 * 测试文件存储目录管理
 */
public class DirectoryUtils {
	private static final String TAG = "DirectoryUtils";
	/** 外部存储根目录 */
	public static final String EXTERNAL_ROOT_FILE_NAME = "TEST";

	private static File internalRoot;
	private static File externalRoot;
	
	static {
		File eRoot = new File(Environment.getExternalStorageDirectory(), EXTERNAL_ROOT_FILE_NAME);
		if(!eRoot.exists()) {
			eRoot.mkdirs();
			File nomedia = new File(eRoot, ".nomedia");
			try {
				nomedia.createNewFile();
			} catch(Exception ig) {}
		}
		externalRoot = eRoot;
	}
	
	public static void init(Context context) {
		File iRoot = context.getFilesDir();
		if(!iRoot.exists()) {
			iRoot.mkdirs();
		}
		internalRoot = iRoot;
	}
	
	public static File getInternalRoot() {
		return internalRoot;
	}
	
	public static File getExternalRoot() {
		return externalRoot;
	}
	
	/**
	 * 获取内部存储目录
	 */
	public static File getInternalDirectory(String filename) {
		File file = new File(internalRoot, filename);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 获取外部存储目录
	 */
	public static File getExternalDirectory(String filename) {
		File file = new File(externalRoot, filename);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
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

	/**
	 * Returns list of paths for all mountable volumes.
	 */
	public static String[] getVolumePaths(StorageManager storageManager) {
		try {
			return (String[]) getVolumePaths.invoke(storageManager);
		} catch (Exception e) {
			Log.e(TAG, "getVolumePaths e=" + e);
		}
		return null;
	}

	/**
	 * Returns list of paths for all mountable volumes.
	 */
	public static String[] getVolumePaths(Context context) {
		return getVolumePaths((StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE));
	}

	/**
	 * Gets the state of a volume via its mountpoint.
	 */
	public static String getVolumeState(StorageManager storageManager,
			String mountPoint) {
		try {
			return (String) getVolumeState.invoke(storageManager, mountPoint);
		} catch (Exception e) {
			Log.e(TAG, "getVolumeState e=" + e);
		}
		return null;
	}

	/**
	 * Gets the state of a volume via its mountpoint.
	 */
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
	
	public static File getDirectoryOnMountedVolume(Context context, String path) {
		File directory;
		for(File file : getMountedVolumeFiles(context)) {
			directory = new File(file, path);
			if(directory.exists()) {
				return directory;
			}
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static String readExtSdcard_Cid() {
		String str1 = null;
		Object localOb;
		try {
			localOb = new FileReader("/sys/block/mmcblk1/device/type");
			localOb = new BufferedReader((Reader) localOb).readLine()
					.toLowerCase().contentEquals("sd");
			if (localOb != null) {
				str1 = "/sys/block/mmcblk1/device/";
			}
			localOb = new FileReader(str1 + "cid"); // SD Card ID
			str1 = new BufferedReader((Reader) localOb).readLine();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return str1;
	}

	/**
	 * 读取内置SD卡的C_ID
	 */
	@SuppressWarnings("resource")
	public static  String readSdCard_Cid() {
		String str1 = null;
		Object localOb;
		try {
			localOb = new FileReader("/sys/block/mmcblk0/device/type");
			localOb = new BufferedReader((Reader) localOb).readLine()
					.toLowerCase().contentEquals("mmc");
			if (localOb != null) {
				str1 = "/sys/block/mmcblk0/device/";
			}
			localOb = new FileReader(str1 + "cid"); // nand ID
			str1 = new BufferedReader((Reader) localOb).readLine();
			Log.v(TAG, "cid: " + str1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return str1;
	}
}
