package com.example.misc;

/**
 * Created by yhf on 2015/4/7.
 */
public class ExecptionLogStackTraceTest {

    public static void test() throws Exception {
        testExStackTrace();
    }

    private static void testExStackTrace() throws Exception {
        int i = 1;
        try {
            ex();
        } catch(Exception e) {
            throw new Exception("bbb", e);
        }
    }

    private static void ex() throws Exception {
        try {

            throw new Exception("xxx");
        } catch(Exception e) {
            throw new Exception("aaa", e);
        }
    }
}
