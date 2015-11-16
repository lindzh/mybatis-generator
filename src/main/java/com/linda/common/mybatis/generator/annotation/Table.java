package com.linda.common.mybatis.generator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成表
 * @author lindezhi
 * 2015年11月14日 上午10:20:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	
	/**
	 * 表名称
	 * @return
	 */
	String name();
	
	/**
	 * 是否自动生primary key
	 * @return
	 */
	boolean autoGeneratePrimaryKey() default true;

}
