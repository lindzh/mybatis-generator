package com.lindzh.mybatis.generator;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 上午10:34:55
 */
public class GeneratorException extends RuntimeException{

	private static final long serialVersionUID = -1832371381972592054L;

	public GeneratorException() {
		super();
	}

	public GeneratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneratorException(String message) {
		super(message);
	}

	public GeneratorException(Throwable cause) {
		super(cause);
	}

}
