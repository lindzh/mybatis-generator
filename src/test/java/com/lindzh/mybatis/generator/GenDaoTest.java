package com.lindzh.mybatis.generator;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.bean.StuCourse;
import com.lindzh.mybatis.generator.processor.DefaultMybatisGenerator;

public class GenDaoTest {
	
	public static void main(String[] args) {
		DefaultMybatisGenerator generator = new DefaultMybatisGenerator();
		generator.startService();
		MybatisPojo code = generator.genCode(StuCourse.class, "com.lindzh.mybatis.generator.dao", "D:\\work\\frameworks\\mybatis-generator\\src\\test\\resources\\sqlmap\\", "D:\\work\\frameworks\\mybatis-generator\\src\\test\\java\\com\\linda\\common\\mybatis\\generator\\dao\\");
		String json = JSONUtils.toJSON(code);
		System.out.println(json);
	}

}
