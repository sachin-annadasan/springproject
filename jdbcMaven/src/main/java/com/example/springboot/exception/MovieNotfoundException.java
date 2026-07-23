package com.example.springboot.exception;

public class MovieNotfoundException extends RuntimeException {

	public MovieNotfoundException(String name) {
		super(name);
	}
	
    }

