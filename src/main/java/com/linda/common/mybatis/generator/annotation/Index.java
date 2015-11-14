package com.linda.common.mybatis.generator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记索引，自动生成查询
 * @author lindezhi
 * 2015年11月14日 上午10:14:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
	
	/**
	 * 如果是单列索引，则不需要填写，如果是联合索引，每列填写
	 * @return
	 */
	String name() default "";
	
	/**
	 * 查询是否分页，返回单个结果不需要分页，请填写false
	 * @return
	 */
	boolean limitOffset() default true;

	/**
	 * 数量查询，当limitOffset为true时才会自动生成
	 * @return
	 */
	boolean count() default true;
	
	/**
	 * 只返回第一个结果
	 * @return
	 */
	boolean selectOne() default false;
}
