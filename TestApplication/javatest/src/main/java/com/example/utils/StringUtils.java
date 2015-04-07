package com.example.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Arrays;

public class StringUtils {
	static final String TAG = "StringUtils";

	public static String getFirstLetter(String str) {
		if(str == null || str.isEmpty()) {
			return "#";
		}
		char firstChar = str.charAt(0);
		if(firstChar < 128) {//ASCII
			return String.valueOf(getUpperCaseChar(firstChar));
		}
		String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(firstChar);
		if(pinyinArr == null || pinyinArr.length == 0) {
			return "#";
		}
        System.out.println(Arrays.toString(pinyinArr));
		return String.valueOf(getUpperCaseChar(pinyinArr[0].charAt(0)));
	}
	
	public static char getUpperCaseChar(char ascii) {
		if(ascii >= 'A' && ascii <= 'Z') {
			return ascii;
		}
		if(ascii >= 'a' && ascii <= 'z') {
			return (char) (ascii - 32);
		}
		return '#';
	}
}
