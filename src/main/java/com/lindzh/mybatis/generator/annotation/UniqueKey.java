package com.lindzh.mybatis.generator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单一索引
 * @author lindezhi
 * 2015年11月14日 上午10:19:02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueKey {

	/**
	 * 单列unique不需要，多列请在每列添加，name相同为同一个unique
	 * @return
	 */
	String name() default "";
	
	/**
	 * 自动生成select
	 * @return
	 */
	boolean select() default true;
	
	/**
	 * 自动生成update
	 * @return
	 */
	boolean update() default true;
	
	/**
	 * 自动生成delete
	 * @return
	 */
	boolean delete() default true;
	
}
