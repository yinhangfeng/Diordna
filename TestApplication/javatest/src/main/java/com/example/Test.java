package com.example;

import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) throws Exception {
		System.out.println("xxxxxxxxxxxx");
		StackTraceElement caller = getCallerStackTraceElement();
		System.out.println(generateTag(caller));
		xxx();
		new Test().aaa();
	}

	private static void xxx() {
		StackTraceElement caller = getCallerStackTraceElement();
		System.out.println(generateTag(caller));
	}

	private void aaa() {
		StackTraceElement caller = getCallerStackTraceElement();
		System.out.println(generateTag(caller));
	}

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

	private static String generateTag(StackTraceElement caller) {
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
		return tag;
	}

}
