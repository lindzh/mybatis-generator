package com.lindzh.mybatis.generator.bean;

import com.lindzh.mybatis.generator.annotation.Index;
import com.lindzh.mybatis.generator.annotation.PrimaryKey;
import lombok.Data;

import com.lindzh.mybatis.generator.annotation.Column;
import com.lindzh.mybatis.generator.annotation.Table;
import com.lindzh.mybatis.generator.annotation.UniqueKey;

@Data
@Table(name="user_info",autoGeneratePrimaryKey=true)
public class UserInfo {
	
	@PrimaryKey
	private long id;
	
	@UniqueKey(name="Mobile")
	@Column(column="mobile")
	private String mobile;
	
	@Index(name="Sex",count=true,limitOffset=true,selectOne=true)
	@Column(column="sex")
	private String sex;
	
	@Column(column="add_time")
	@UniqueKey(name="Mobile")
	private long addTime;
	
	private String other;

}
