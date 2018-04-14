package com.lindzh.mybatis.generator.loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class EncodeKop {

	private static String baseDir = "D:\\catalina-classx\\koala_class";
	
	private static SimpleCipher cipher = new SimpleCipher("1234567890");
	
	public static int enCode(String folder, final List<String> fixes) {
		int lines = 0;
		File file = new File(folder);
		if (file.exists()) {
			if (file.isDirectory()) {
				String[] fileNames = file.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (dir.isFile()) {
							if (fixes != null && fixes.size() > 0) {
								for (String fix : fixes) {
									if (name.endsWith(fix)) {
										return true;
									}
								}
								return false;
							}
							return true;
						}
						return true;
					}
				});
				if (fileNames != null && fileNames.length > 0) {
					for (String fileName : fileNames) {
						lines += enCode(folder + "\\" + fileName, fixes);
					}
				}
			}
			if (file.isFile() && file.canRead()) {
				try {
					codecFile(file);
				} catch (Exception e) {

				}
			}
		} else {
			System.out.println("directory not eixst: " + folder);
		}
		return lines;
	}
	
	private static void codecFile(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(fis, bos);
		byte[] array = bos.toByteArray();
		byte[] encode = cipher.encodeBytes(array);
		File classx = new File(file.getAbsolutePath()+"x");
		FileOutputStream fos = new FileOutputStream(classx);
		IOUtils.write(encode, fos);
		fis.close();
		fos.close();
		bos.close();
		file.delete();
	}
	
	public static void main(String[] args) {
		List<String> fixes = new ArrayList<String>();
		fixes.add(".class");
		int javaFileLines = enCode(baseDir, fixes);
		System.out.println("finished");
	}
	
}
