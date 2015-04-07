package com.example.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by yhf on 2015/4/7.
 */
public class ProxyTest {

    public static void test() {
        OperateInvocationHandler operateInvocationHandler = new OperateInvocationHandler();
        Object proxyObject = Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[] {Operate.class, Operate2.class}, operateInvocationHandler);
        Operate operate = (Operate) proxyObject;
        operate.operateMethod1();

        Operate2 operate2 = (Operate2) proxyObject;
        operate2.operate2Method1();
    }
}
