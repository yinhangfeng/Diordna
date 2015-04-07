package com.example.misc;

/**
 * Created by yhf on 2015/4/7.
 */
public class Base64Test {

    private static final char[] CHARS = new char[] { 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '+', '/' };
    private static final int MASK = 0x3F;

    private static void f(int i, StringBuilder sb) {
        boolean b;
        int offset = (i >>> 30) & MASK;
        if ((b = offset != 0)) {
            sb.append(CHARS[offset]);
        }
        offset = (i >>> 24) & MASK;
        if (b || (b = offset != 0)) {
            sb.append(CHARS[offset]);
        }
        offset = (i >>> 18) & MASK;
        if (b || (b = offset != 0)) {
            sb.append(CHARS[offset]);
        }
        offset = (i >>> 12) & MASK;
        if (b || (b = offset != 0)) {
            sb.append(CHARS[offset]);
        }
        offset = (i >>> 6) & MASK;
        if (b || offset != 0) {
            sb.append(CHARS[offset]);
        }
        sb.append(CHARS[i & MASK]);
    }
}
