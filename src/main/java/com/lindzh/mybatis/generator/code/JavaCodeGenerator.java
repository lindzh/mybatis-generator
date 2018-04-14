package com.lindzh.mybatis.generator.code;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 下午5:13:59
 */
public class JavaCodeGenerator extends AbstractGenerator{
	
	private static final String JAVA_TEMPLATE = "java_press";

	public JavaCodeGenerator(FreemarkerService freemarkerService,MybatisPojo mybatisPojo) {
		super(freemarkerService, mybatisPojo);
	}

	@Override
	public String parse() {
		return freemarkerService.merge(JAVA_TEMPLATE, params);
	}
}
