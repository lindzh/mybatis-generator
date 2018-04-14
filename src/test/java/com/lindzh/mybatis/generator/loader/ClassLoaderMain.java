package com.lindzh.mybatis.generator.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;


public class ClassLoaderMain {
	
	private static String baseDir = "D:\\work\\frameworks\\tomcat7\\test";
	
	private static String keyFile = "D:\\catalina-classx\\encode_key.txt";
	
	public class LoaderConfig{
	
	private String package_suffix;
	
	private String class_suffix;
	
	private String method_suffix;
	
	private String key;
	
	private String support_file;
	
	private String subclass;
	
	}
	
	
	private String genClassName(int index){
		String hex = this.toHex(index);
		return "org.apache.catalina.loader."+this.genPackage(hex)+".CatalinaClassLoader_"+this.genClass(hex);
	}
	
	
	private String genMethodSuffix(){
		return RandomStringUtils.randomAlphanumeric(10);
	}
	
	private String toHex(int index){
		if(index>65535){
			index = 65535;
		}
		if(index<0){
			index = 0;
		}
		String hex = Integer.toHexString(index);
		if(hex.length()==1){
			return "000"+hex;
		}else if(hex.length()==2){
			return "00"+hex;
		}else if(hex.length()==3){
			return "0"+hex;
		}else{
			return hex;
		}
	}
	
	private String genPackage(String hex){
		return "_"+hex.substring(0, 2);
	}
	
	private String genClass(String hex){
		return hex;
	}
	
	private String genSubClass(int index){
		if(index>=65535){
			return this.genClassName(index+1)+"_ext";
		}else{
			return this.genClassName(index+1);
		}
	}
	
	private static String randomString(int length){
		return UUID.randomUUID().toString().substring(0, length).toLowerCase();
	}
	
	private static int randomInt(){
		Random random = new Random();
		int nextInt = random.nextInt(5);
		if(nextInt<1){
			nextInt = 1;
		}
		return nextInt*2;
	}
	
	private String genSuportedFile(int index){
		if(index>=65535){
			return ".classx";
		}else{
			return ".class_"+this.toHex(index+1);
		}
	}
	
	private String genKey(){
		return new SimpleCipher().getKey();
	}
	
	private void genAndSave(FreemarkerService service,Map<String,Object> model,String className) throws IOException{
		
		String result = service.merge("classloader", model);
		String replaceAll = className.replace(".","\\");
		String fileName = baseDir+File.separator+replaceAll;
		File file = new File(fileName+".java");
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdir();
		}
		file.createNewFile();
		FileUtils.write(file, result, "utf-8");
	}
	
	
	
	public static void main(String[] args) throws IOException {
		FreemarkerService service = new FreemarkerService();
		service.setLocation("");
		service.setSuffix("ftl");
		service.startService();
		
		ClassLoaderMain main = new ClassLoaderMain();
		int start = 60000;
		int end = 65536;
		while(start<end){
			HashMap<String,Object> model = new HashMap<String,Object>();
			
			String hex = main.toHex(start);
			String genPackage = main.genPackage(hex);
			String className = main.genClassName(start);
			String genClass = main.genClass(hex);
			String genMethodSuffix = main.genMethodSuffix();
			String genKey = main.genKey();
			String genSubClass = main.genSubClass(start);
			String genSuportedFile = main.genSuportedFile(start);
			
			model.put("package_suffix", genPackage);
			model.put("class_suffix", genClass);
			model.put("method_suffix", genMethodSuffix);
			model.put("key", genKey);
			model.put("support_file", genSuportedFile);
			model.put("subclass", genSubClass);
			
			int length = randomInt();
			String rand1_str = randomString(length);
			String rand2_str = randomString(10-randomInt());
			model.put("rand2_str", rand2_str);
			model.put("rand1_length", length);
			model.put("rand1_str", rand1_str);
			
			main.genAndSave(service, model,className);
			
			
			System.out.println(className);
			
			String key = genSubClass+","+genKey+","+genSuportedFile+"\r\n";
			FileUtils.write(new File(keyFile), key, true);
			
			start++;
		}
		
	}
	
}
