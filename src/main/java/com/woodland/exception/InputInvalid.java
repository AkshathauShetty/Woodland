package com.woodland.exception;



public class InputInvalid extends RuntimeException{

	public InputInvalid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InputInvalid(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InputInvalid(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InputInvalid(String string) {
		// TODO Auto-generated constructor stub
		//System.out.println(string);
		super(string);
	}
	
}
