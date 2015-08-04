package com.example.commonlibrary;

import java.text.DecimalFormat;
import java.util.Random;

public class CommonUtils {
	private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
	
	public static String getRandString(StringBuilder sb, Random r, int len) {
		sb.setLength(0);
		for(int i = 0; i < len; ++i) {
			sb.append(CHARS[r.nextInt(CHARS.length)]);
		}
		return sb.toString();
	}
	
	public static String getReadableSize(long size) {
		final int BYTES_IN_KILOBYTES = 1024;
		final DecimalFormat dec = new DecimalFormat("###.#");
		final String KILOBYTES = " KB";
		final String MEGABYTES = " MB";
		final String GIGABYTES = " GB";
		float fileSize = 0;
		String suffix = KILOBYTES;

		if (size > BYTES_IN_KILOBYTES) {
			fileSize = size / BYTES_IN_KILOBYTES;
			if (fileSize > BYTES_IN_KILOBYTES) {
				fileSize = fileSize / BYTES_IN_KILOBYTES;
				if (fileSize > BYTES_IN_KILOBYTES) {
					fileSize = fileSize / BYTES_IN_KILOBYTES;
					suffix = GIGABYTES;
				} else {
					suffix = MEGABYTES;
				}
			}
		}
		return String.valueOf(dec.format(fileSize) + suffix);
	}

}
