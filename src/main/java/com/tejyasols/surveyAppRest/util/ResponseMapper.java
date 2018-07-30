package com.tejyasols.surveyAppRest.util;

import org.springframework.http.HttpStatus;

public class ResponseMapper {
	
	public HttpStatus responseCode;
	public Object object;
	public String message;
	
	public ResponseMapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ResponseMapper(HttpStatus responseCode, Object object, String message) {
		super();
		this.responseCode = responseCode;
		this.object = object;
		this.message = message;
	}


	public HttpStatus getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResponseMapper [responseCode=" + responseCode + ", object=" + object + ", message=" + message + "]";
	}

	
}
