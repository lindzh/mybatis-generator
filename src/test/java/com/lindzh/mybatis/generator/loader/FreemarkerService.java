package com.lindzh.mybatis.generator.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lindzh.mybatis.generator.Service;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 上午10:35:03
 */
public class FreemarkerService implements Service {

	private String location;

	private String suffix;

	private Configuration cfg = new Configuration();
	
	private String encoding = "utf-8";
	
	private Logger logger = Logger.getLogger(FreemarkerService.class);
	
	public String merge(String template, Map<String, Object> model) {
		try {
			Template tpl = cfg.getTemplate(template+"."+suffix);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(bos);
			tpl.process(model, writer);
			writer.flush();
			writer.close();
			byte[] byteArray = bos.toByteArray();
			return new String(byteArray,encoding);
		} catch (IOException e) {
			logger.error("IOException template "+template,e);
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			logger.error("TemplateException template "+template,e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startService() {
		cfg.setDefaultEncoding(encoding);
		cfg.setClassForTemplateLoading(FreemarkerService.class, "");
//		cfg.setDirectoryForTemplateLoading(new File(""));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setNumberFormat("#");
		logger.info("freemarker service started");
	}

	@Override
	public void stopService() {
		cfg.clearTemplateCache();
		logger.info("freemarker service stoped");
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
