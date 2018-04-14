package com.lindzh.mybatis.generator.loader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class Encoder {
	
	private static String keyFile = "D:\\catalina-classx\\encode_key.txt";
	
	private static String src = "D:\\catalina-classx\\loader_ff";
	
	private static String destDir = "D:\\catalina-classx\\loader_encoded";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(keyFile))));
		String line = reader.readLine();
		while(line!=null){
			String[] split = line.split(",");
			String className = split[0];
			String key = split[1];
			String suffix = split[2];
			
			String replaceAll = className.replace(".","\\");
			String fileName = src+File.separator+replaceAll;
			File file = new File(fileName+".class");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(fis, bos);
			byte[] source = bos.toByteArray();
			SimpleCipher cipher = new SimpleCipher(key);
			byte[] dest = cipher.encodeBytes(source);
			
			String destFileName = destDir+File.separator+replaceAll+suffix;
			File destFile = new File(destFileName);
			File parentFile = destFile.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			destFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(destFile);
			ByteArrayInputStream bis = new ByteArrayInputStream(dest);
			
			IOUtils.copy(bis, fos);
			
			fis.close();
			fos.close();
			
			System.out.println(destFileName);
			line = reader.readLine();
			
		}
		
		
	}

}
