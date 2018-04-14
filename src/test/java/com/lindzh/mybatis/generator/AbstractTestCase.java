package com.lindzh.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * 
 * @author lindezhi
 * 2015年11月15日 上午11:14:28
 */
public abstract class AbstractTestCase {

	private static Server h2Server;

	@BeforeClass
	public static void createH2Server() throws SQLException, ClassNotFoundException {
		String[] args = new String[]{};
		h2Server = Server.createTcpServer(args).start();
		Class.forName("org.h2.Driver");
		String path = new File("src/test/resources/test_table.sql").getAbsolutePath();
		String url = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '"+path+"'";
//		String url = "jdbc:h2:mem:test";
		try{
			Connection connection = DriverManager.getConnection(url, "sa", "");
			connection.setAutoCommit(true);
		}catch (Exception e){

		}
	}

	protected SqlSessionFactory factory;

	@Before
	public void init() throws SQLException, ClassNotFoundException {
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

	@AfterClass
	public static void stopH2Server(){
		if(h2Server!=null){
			h2Server.stop();
		}
	}
}
