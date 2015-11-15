package com.linda.common.mybatis.generator;

import com.linda.common.mybatis.generator.bean.MybatisPojo;
import com.linda.common.mybatis.generator.bean.UserInfo;
import com.linda.common.mybatis.generator.processor.DefaultMybatisGenerator;
import com.linda.framework.log.util.JsonUtil;

public class GenDaoTest {
	
	public static void main(String[] args) {
		DefaultMybatisGenerator generator = new DefaultMybatisGenerator();
		generator.startService();
		MybatisPojo code = generator.genCode(UserInfo.class, "com.linda.common.mybatis.generator.dao", "D:\\work\\frameworks\\mybatis-generator\\src\\test\\resources\\sqlmap\\", "D:\\work\\frameworks\\mybatis-generator\\src\\test\\java\\com\\linda\\common\\mybatis\\generator\\dao\\");
		String json = JsonUtil.toJson(code);
		System.out.println(json);
	}

}
