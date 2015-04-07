package com.example.string;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by yhf on 2015/4/7.
 */
public class StringTest {

    private static void internTest() throws Exception {
        String s0 = "abc";
        String s = "abc";
        System.out.println("s=" + s);
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);
        char[] value = (char[]) valueField.get(s);
        System.out.println("value=" + Arrays.toString(value));
        value[0] = 'x';
        System.out.println("s0=" + s0);
        System.out.println("s=" + s);
        String s1 = "abc";
        System.out.println("s1=" + s1);

        String s2 = new String("ab") + "c";
        System.out.println("s2=" + s2);
        System.out.println("s2.intern()=" + s2.intern());

        System.out.println("s=" + s);
        System.out.println("s1=" + s1);

        String s3 = "abc";
        System.out.println("s3=" + s3);
        System.out.println("s3.intern()=" + s3.intern());
    }
}
