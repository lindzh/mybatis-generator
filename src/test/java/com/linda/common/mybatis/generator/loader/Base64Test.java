package com.linda.common.mybatis.generator.loader;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {
	
	private static Base64 base64 = new Base64();
	
	public static void main(String[] args) {
		
		byte[] encode = base64.encode("12345678".getBytes());
		System.out.println(new String(encode));
	}

}
