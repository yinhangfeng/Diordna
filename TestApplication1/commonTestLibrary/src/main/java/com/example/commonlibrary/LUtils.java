package com.example.commonlibrary;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class LUtils {

	public static String getTimeStr(long start, long end, int number) {
		return "总时间= " + (end - start) + "MS " + (end - start) / 1000f + "S"
				+ " 次数=" + number + " 平均=" + (((end - start) / (float) number))
				+ "MS";
	}

	public static void logTime(String tag, String msg, long start, long end,
			int number) {
		Log.i(tag, msg + " " + getTimeStr(start, end, number));
	}

	public static void logTime(String tag, String msg, long start, long end) {
		Log.i(tag, msg + " " + getTimeStr(start, end, 1));
	}

	private static void getRecursionViewInfo(StringBuilder sb, String spaces, View v) {
		sb.append(spaces);
		sb.append(v.toString());
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			int childCount = vg.getChildCount();
			sb.append('[');
			if(childCount > 0) {
				sb.append('\n');
			}
			for (int i = 0; i < childCount; ++i) {
				getRecursionViewInfo(sb, spaces + "  ", vg.getChildAt(i));
				sb.append('\n');
			}
			if(childCount > 0) {
				sb.append(spaces);
			}
			sb.append(']');
		}
	}

	public static String getRecursionViewInfo(View v) {
		StringBuilder sb = new StringBuilder();
		getRecursionViewInfo(sb, "", v);
		return sb.toString();
	}
	
	public static void LogViewInfo(String tag, View v) {
		Log.i(tag, getRecursionViewInfo(v));
	}

}
