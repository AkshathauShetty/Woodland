package com.woodland.exception;


public class ConversionFailed  extends RuntimeException{

	public ConversionFailed(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ConversionFailed(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ConversionFailed(String string) {
		// TODO Auto-generated constructor stub
		//System.out.println(string);
		super(string);
	}
	

}