package com.ed1.article.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private List<String> message;
	
	public BadRequestException() {
	}

	public BadRequestException(List<String> message) {
		super(message.get(0));
		this.setMessage(message);
	}

	public List<String> getMessagePersonalized() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

}
