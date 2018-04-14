package com.lindzh.mybatis.generator.code;

import java.util.HashMap;
import java.util.Map;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;
import com.lindzh.mybatis.generator.freemarker.FtlSqlType;

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
		params.put("sqlTypeFunc",new FtlSqlType());
		params.put("pre", "#{");
		params.put("end", "}");
		params.put("mybatis", mybatisPojo);
	}
	
	public abstract String parse();
}
