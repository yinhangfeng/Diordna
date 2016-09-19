package com.example.yhf.webviewtest.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 获取设备唯一id
 */
public class DeviceIdUtils {
	
	/** IMEI 可能为null */
	public static String IMEI;
	/** 序列号 可能为null */
	public static String SERIAL;
	/** 序号 三星设备 可能为null */
	public static String RIL_SERIAL;
	/** sys.serialnumber 可能为null */
	public static String SYS_SERIAL;
	
	private DeviceIdUtils() {}

	public static void init(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = trimId(telephonyManager.getDeviceId());
		SERIAL = trimId(android.os.Build.SERIAL);
		RIL_SERIAL = trimId(getRilSerialnumber());
		SYS_SERIAL = trimId(getSysSerialnumber());
		Log.d("DeviceIdUtils", getDeviceIdText());
	}
	
	private static String trimId(String id) {
		if(id == null) {
			return null;
		}
		id = id.trim();
		if(id.length() == 0 || "null".equalsIgnoreCase(id)) {
			return null;
		}
		return id;
	}

	/**
	 * 获取序号 三星设备
	 * @return null 获取失败
	 */
	private static String getRilSerialnumber() {
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			return (String) get.invoke(c, "ril.serialnumber", null);
		} catch (Exception ignored) {
		}
		return null;
	}
	
	/**
	 * 获取sys.serialnumber
	 * @return null 获取失败
	 */
	private static String getSysSerialnumber() {
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			return (String) (get.invoke(c, "sys.serialnumber", null));
		} catch (Exception ignored) {
		}
		return null;
	}
	
	public static String getDeviceIdText() {
		StringBuilder sb = new StringBuilder();
		sb.append("IMEI:").append(IMEI)
		.append("\nSERIAL:").append(SERIAL)
		.append("\nRIL_SERIAL:").append(RIL_SERIAL)
		.append("\nSYS_SERIAL:").append(SYS_SERIAL);
		return sb.toString();
	}

}
