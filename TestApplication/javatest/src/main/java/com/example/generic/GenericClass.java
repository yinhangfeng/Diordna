package com.example.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class GenericClass<T, R, S> extends ClassTest {

	public Class<T> getClassT() {
		Class<?> cls = getClass();
		Type genericSuperclass = cls.getGenericSuperclass();
		Type[] types = null;
		for(;;) {
			if(genericSuperclass instanceof ParameterizedType) {
				types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
				break;
			}
			if(genericSuperclass == null) {
				break;
			}
			cls = cls.getSuperclass();
			if(cls == null) {
				break;
			}
			genericSuperclass = cls.getGenericSuperclass();
		}
		if(!(genericSuperclass instanceof ParameterizedType)) {
			throw new RuntimeException("xxx");
		}
		types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
		printTypes(types);
		return null;
	}
	
	public static void printTypes(Type[] types) {
		System.out.println(Arrays.toString(types));
	}

}
