package com.example.proxy;

import com.example.utils.U;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class OperateInvocationHandler implements InvocationHandler {

	/**
	 * proxy 委托接口的子类
	 * method 调用的委托接口中的方法
	 * args 参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		U.println("proxy=" + proxy.getClass() + " method=" + method + " args=" + Arrays.toString(args));
		return null;
	}

}
