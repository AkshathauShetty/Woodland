package com.woodland.exception;


public class DataNotFound extends RuntimeException{

	public DataNotFound(String string) {
		// TODO Auto-generated constructor stub
		//System.out.println(string);
		super(string);
	}

	public DataNotFound(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataNotFound(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
