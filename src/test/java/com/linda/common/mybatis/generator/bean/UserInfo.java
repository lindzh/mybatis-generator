package com.linda.common.mybatis.generator.bean;

import lombok.Data;

import com.linda.common.mybatis.generator.annotation.Column;
import com.linda.common.mybatis.generator.annotation.Index;
import com.linda.common.mybatis.generator.annotation.PrimaryKey;
import com.linda.common.mybatis.generator.annotation.Table;
import com.linda.common.mybatis.generator.annotation.UniqueKey;

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
