package com.linda.common.mybatis.generator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表主键
 * @author lindezhi
 * 2015年11月14日 上午10:22:16
 * 会自动生成select,update,delete
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
	
	/**
	 * 主键对应的列
	 * @return
	 */
	String column() default "id"; 

}
