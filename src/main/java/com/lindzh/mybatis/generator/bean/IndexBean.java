package com.lindzh.mybatis.generator.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 上午10:52:38
 */
@Data
public class IndexBean {
	
	private String name;
	
	private List<ColumnBean> columns = new ArrayList<ColumnBean>();
	
	private boolean limitOffset;
	
	private boolean count;
	
	private boolean selectOne;

}
