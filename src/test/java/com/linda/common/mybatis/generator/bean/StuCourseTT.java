package com.linda.common.mybatis.generator.bean;

import lombok.Data;

import com.linda.common.mybatis.generator.annotation.Column;
import com.linda.common.mybatis.generator.annotation.Index;
import com.linda.common.mybatis.generator.annotation.PrimaryKey;
import com.linda.common.mybatis.generator.annotation.Table;
import com.linda.common.mybatis.generator.annotation.UniqueKey;

@Data
@Table(name="stu_course",autoGeneratePrimaryKey=true)
public class StuCourseTT {
	
	@PrimaryKey
	private long id;
	
	@UniqueKey(name="UserAndCourse")
	@Column(column="stu_id")
	private long stuId;
	
	@Index(name="CourseAndTime")
	@UniqueKey(name="UserAndCourse")
	@Column(column="course_id")
	private long courseId;
	
}
