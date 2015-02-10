package com.yinhangfeng.diordna;

import android.test.ActivityUnitTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by yhf on 2015/2/10.
 */
public class ExampleTest extends InstrumentationTestCase {

    public ExampleTest() {
    }

    public void testA() {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
        assertTrue(false);
    }

    public void testB() {
        String s = U.getOnMeasureStr("aaa", 123, 444);
        System.out.println(s);
        Log.i("xxx", s);
        assertEquals(3, 4);
    }

}
