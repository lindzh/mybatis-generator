package com.linda.common.mybatis.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 下午5:13:38
 */
public class ThreadLocalCache {
	
	private static ThreadLocal<Map<String,Object>> localCache = new ThreadLocal<Map<String,Object>>();
	
	public static void put(String key,Object value){
		Map<String,Object> cache = localCache.get();
		if(cache==null){
			cache = new HashMap<String,Object>();
			localCache.set(cache);
		}
		cache.put(key, value);
	}
	
	public static Object get(String key){
		Map<String,Object> cache = localCache.get();
		if(cache!=null){
			return cache.get(key);
		}
		return null;
	}
	
	public static void clear(){
		localCache.remove();
	}

}
