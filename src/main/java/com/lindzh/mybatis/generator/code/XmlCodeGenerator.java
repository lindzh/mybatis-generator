package com.lindzh.mybatis.generator.code;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 下午5:13:53
 */
public class XmlCodeGenerator extends AbstractGenerator{
	
	private static final String XML_TEMPLATE = "xml_press";

	public XmlCodeGenerator(FreemarkerService freemarkerService,MybatisPojo mybatisPojo) {
		super(freemarkerService, mybatisPojo);
	}

	@Override
	public String parse() {
		return freemarkerService.merge(XML_TEMPLATE, params);
	}

}
