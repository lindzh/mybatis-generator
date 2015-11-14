package com.linda.common.mybatis.generator.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 上午10:37:00
 */
@Data
public class MybatisPojo {
	
	private String daoPackage;
	
	private String beanPackage;
	
	private String xmlLocation;
	
	private String daoClassName;
	
	private String classSimpleName;
	
	private String namespace;
	
	private String className;
	
	private String table;
	
	private String beanMapper;
	
	private boolean autoGenerate;
	
	private ColumnBean primary;
	
	private List<ColumnBean> columns = new ArrayList<ColumnBean>();
	
	private List<UniqueBean> uniques = new ArrayList<UniqueBean>();
	
	private List<IndexBean> indexes = new ArrayList<IndexBean>();
	
}
