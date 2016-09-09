package com.linda.common.mybatis.generator.loader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class FixKey {
	
	private static String keyFile = "D:\\work\\frameworks\\tomcat7\\encode_key.txt";
	
	private static String keyFile_2 = "D:\\work\\frameworks\\tomcat7\\encode_key_real.txt";
	
	public static void main(String[] args) throws IOException {
		
		String file = FileUtils.readFileToString(new File(keyFile), "utf-8");
		String[] split = file.split(",");
		if(split.length%3!=0){
			System.out.println("invalid file");
		}
		int index = 0;
		while(index<split.length-1){
			String clazz = split[index];
			String key = split[index+1];
			String ext = split[index+2];
			
			String line = clazz+","+key+","+ext;
			
			FileUtils.writeLines(new File(keyFile_2), Arrays.asList(line), true);
			System.out.println(line);
			index+=3;
		}
		
		
		
		
	}

}
