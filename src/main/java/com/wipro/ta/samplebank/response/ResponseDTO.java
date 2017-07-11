package com.wipro.ta.samplebank.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	private T data;

	public ResponseDTO() {
	}

	public ResponseDTO(ResponseMessage responseMessage) {
		this.code = responseMessage.getCode();
		this.message = responseMessage.getMessage();
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage responseMessage) {
		this.code = responseMessage.getCode();
		this.message =  responseMessage.getMessage();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	@JsonIgnore
	public boolean isSuccessful(){
		return code.startsWith("INF");
	}
	
	@JsonIgnore
	public String getMessageStyle(){
		if(isSuccessful()){
			return "sb-success-message";
		}else{
			return "sb-error-message";
		}
	}
}