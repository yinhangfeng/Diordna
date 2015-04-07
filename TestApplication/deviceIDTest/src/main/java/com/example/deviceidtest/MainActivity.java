package com.example.deviceidtest;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView textView1 = (TextView) findViewById(R.id.text_view1);
		StringBuilder sb = new StringBuilder();
		
		sb.append("android.os.Build.ID=").append(android.os.Build.ID);
		sb.append("\nandroid.os.Build.DISPLAY=").append(android.os.Build.DISPLAY);
		sb.append("\nandroid.os.Build.PRODUCT=").append(android.os.Build.PRODUCT);
		sb.append("\nandroid.os.Build.DEVICE=").append(android.os.Build.DEVICE);
		sb.append("\nandroid.os.Build.BOARD=").append(android.os.Build.BOARD);
		sb.append("\nandroid.os.Build.CPU_ABI=").append(android.os.Build.CPU_ABI);
		sb.append("\nandroid.os.Build.CPU_ABI2=").append(android.os.Build.CPU_ABI2);
		sb.append("\nandroid.os.Build.MANUFACTURER=").append(android.os.Build.MANUFACTURER);
		sb.append("\nandroid.os.Build.BRAND=").append(android.os.Build.BRAND);
		sb.append("\nandroid.os.Build.MODEL=").append(android.os.Build.MODEL);
		sb.append("\nandroid.os.Build.BOOTLOADER=").append(android.os.Build.BOOTLOADER);
		sb.append("\nandroid.os.Build.HARDWARE=").append(android.os.Build.HARDWARE);
		sb.append("\nandroid.os.Build.SERIAL=").append(android.os.Build.SERIAL);
		
		TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		sb.append("\ntelephonyManager.getDeviceId()=").append(telephonyManager.getDeviceId());
		sb.append("\ntelephonyManager.getDeviceSoftwareVersion()=").append(telephonyManager.getDeviceSoftwareVersion());
		//sb.append("telephonyManager.getGroupIdLevel1()=").append(telephonyManager.getGroupIdLevel1());
		sb.append("\ntelephonyManager.getLine1Number()=").append(telephonyManager.getLine1Number());
		//sb.append("telephonyManager.getMmsUAProfUrl()=").append(telephonyManager.getMmsUAProfUrl());
		//sb.append("telephonyManager.getMmsUserAgent()=").append(telephonyManager.getMmsUserAgent());
		sb.append("\ntelephonyManager.getNetworkCountryIso()=").append(telephonyManager.getNetworkCountryIso());
		sb.append("\ntelephonyManager.getSimSerialNumber()=").append(telephonyManager.getSimSerialNumber());
		
		sb.append("\ngetRilSerialnumber()=").append(getRilSerialnumber());
		sb.append("\ngetgetSysSerialnumber()=").append(getgetSysSerialnumber());
		
		sb.append("\nSecure.getString(getContentResolver(), Secure.ANDROID_ID)=")
			.append(Secure.getString(getContentResolver(), Secure.ANDROID_ID));
		
		Log.i(TAG, sb.toString());
		textView1.setText(sb.toString());
	}
	
	public static String getRilSerialnumber() {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			serial = (String) get.invoke(c, "ril.serialnumber", "unknown");
		} catch (Exception ignored) {
		}
		return serial;
	}
	
	public static String getgetSysSerialnumber() {
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			return (String) (get.invoke(c, "sys.serialnumber", null));
		} catch (Exception ignored) {

		}
		return null;
	}
}
