package com.lindzh.mybatis.generator;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

/**
 * 
 * @author lindezhi
 * 2015年11月15日 上午11:14:28
 */
public abstract class AbstractTestCase {
	
	protected SqlSessionFactory factory;

	@Before
	public void init() {
		String resource = "mybatis-config.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		factory = new SqlSessionFactoryBuilder().build(reader);
	}

}
