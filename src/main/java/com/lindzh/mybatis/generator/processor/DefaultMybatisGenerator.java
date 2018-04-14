package com.lindzh.mybatis.generator.processor;

import com.lindzh.mybatis.generator.GeneratorException;
import com.lindzh.mybatis.generator.code.SqlCodeGenerator;
import com.lindzh.mybatis.generator.code.XmlCodeGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.lindzh.mybatis.generator.Service;
import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.code.JavaCodeGenerator;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;
import com.lindzh.mybatis.generator.utils.MybatisPojoGenerator;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 下午3:36:45
 */
public class DefaultMybatisGenerator implements Service{
	
	private FreemarkerService freemarkerService;
	
	private Logger logger = Logger.getLogger(DefaultMybatisGenerator.class);

	public MybatisPojo genCode(Class clazz,String daoPackage,String xmlLocation,String javaLocation){
		MybatisPojo mybatis = MybatisPojoGenerator.genPojo(clazz, daoPackage);
		XmlCodeGenerator xmlCodeGenerator = new XmlCodeGenerator(freemarkerService,mybatis);
		JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(freemarkerService,mybatis);
		SqlCodeGenerator sqlCodeGenerator = new SqlCodeGenerator(freemarkerService,mybatis);
		String javaCode = javaCodeGenerator.parse();
		String xmlCode = xmlCodeGenerator.parse();
        String sqlCode = sqlCodeGenerator.parse();
        String javaFileName = mybatis.getDaoClassName()+".java";
		String xmlFileName = mybatis.getDaoClassName()+".xml";
		this.writeFile(javaCode, javaLocation+javaFileName);
		this.writeFile(xmlCode, xmlLocation+xmlFileName);
		logger.info("file generate success :"+javaFileName);
		logger.info("file generate success :"+xmlFileName);
		logger.info("gen success,please check mybatis config and add xml to list");
        System.out.println(sqlCode);
        return mybatis;
	}
	
	private void writeFile(String content,String file){
		File ff = new File(file);
		if(!ff.exists()){
			try {
				ff.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				IOUtils.write(content, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				throw new GeneratorException(e);
			} catch (IOException e) {
				throw new GeneratorException(e);
			}
		}else{
			throw new GeneratorException(file+" already exist");
		}
	}
	
	@Override
	public void startService() {
		freemarkerService = new FreemarkerService();
		freemarkerService.setLocation("");
		freemarkerService.setSuffix("ftl");
		freemarkerService.startService();
	}
	
	@Override
	public void stopService() {
		freemarkerService.stopService();
	}
}
