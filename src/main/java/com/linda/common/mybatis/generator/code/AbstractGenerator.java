package com.linda.common.mybatis.generator.code;

import java.util.HashMap;
import java.util.Map;

import com.linda.common.mybatis.generator.bean.MybatisPojo;
import com.linda.common.mybatis.generator.freemarker.FreemarkerService;
import com.linda.common.mybatis.generator.freemarker.FtlHashSet;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 上午11:39:00
 */
public abstract class AbstractGenerator {
	
	protected FreemarkerService freemarkerService;
	
	protected MybatisPojo mybatisPojo;
	
	protected Map<String,Object> params = new HashMap<String,Object>(); 
	
	public AbstractGenerator(FreemarkerService freemarkerService,MybatisPojo mybatisPojo){
		this.freemarkerService = freemarkerService;
		this.mybatisPojo = mybatisPojo;
		params.put("pre", "#{");
		params.put("end", "}");
		params.put("mybatis", mybatisPojo);
	}
	
	public abstract String parse();
}
