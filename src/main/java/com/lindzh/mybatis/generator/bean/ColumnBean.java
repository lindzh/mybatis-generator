package com.lindzh.mybatis.generator.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 列定义
 * @author lindezhi
 * 2015年11月14日 上午10:39:09
 */
@Data
@AllArgsConstructor
public class ColumnBean {
	
	private String property;
	
	private String column;
	
	private String type;

}
