package com.lindzh.mybatis.generator.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 唯一建，主键也是唯一键
 * @author lindezhi
 * 2015年11月14日 上午10:43:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniqueBean {
	
	private String name;
	
	private List<ColumnBean> columns = new ArrayList<ColumnBean>();
	
	private boolean update;
	
	private boolean select;
	
	private boolean delete;

	private List<ColumnBean> updateColumns = new ArrayList<ColumnBean>();
}
