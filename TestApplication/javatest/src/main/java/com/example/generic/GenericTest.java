package com.example.generic;

/**
 * Created by yhf on 2015/4/7.
 */
public class GenericTest {
    public static void genericTest1() {
        GenericClassA a = new GenericClassA();
        GenericClassB<Double> b = new GenericClassB<Double>();
        GenericClassC<String, Integer, String> c = new GenericClassC<String, Integer, String>();
        GenericClassD d = new GenericClassD();

        a.getClassT();
        b.getClassT();
        c.getClassT();
        d.getClassT();
    }

}
