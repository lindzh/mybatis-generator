package com.lindzh.mybatis.generator.bean;

import lombok.Data;

import com.lindzh.mybatis.generator.annotation.Column;
import com.lindzh.mybatis.generator.annotation.Index;
import com.lindzh.mybatis.generator.annotation.PrimaryKey;
import com.lindzh.mybatis.generator.annotation.Table;
import com.lindzh.mybatis.generator.annotation.UniqueKey;

@Data
@Table(name="stu_course",autoGeneratePrimaryKey=true)
public class StuCourse {
	
	@PrimaryKey
	private long id;
	
	@UniqueKey(name="UserAndCourse")
	@Column(column="stu_id")
	private long stuId;
	
	@Index(name="CourseAndTime")
	@UniqueKey(name="UserAndCourse")
	@Column(column="course_id")
	private long courseId;
	
	@Index(name="CourseAndTime")
	@Column(column="add_time")
	private long addTime;
	
	@Column(column="score")
	private int score;
}
