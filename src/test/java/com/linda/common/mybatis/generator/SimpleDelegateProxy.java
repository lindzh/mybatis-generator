package com.linda.common.mybatis.generator;

import java.lang.reflect.Method;

import org.apache.ibatis.binding.MapperDelegateProxy;
import org.apache.ibatis.binding.MapperProxy;

public class SimpleDelegateProxy extends MapperDelegateProxy{

	@Override
	public Object invoke(MapperProxy mapper, Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("hahaha-------------------");
		return super.invoke(mapper, proxy, method, args);
	}
}
