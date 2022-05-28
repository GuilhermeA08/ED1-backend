package com.ed1.article.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
public class HttpErrorInfo {
    private final ZonedDateTime timestamp = ZonedDateTime.now();
    private final List<String> message;
    
    public String toString() {
    	return "{" + 
	    		"\"timestamp\":" + timestamp.toInstant().toEpochMilli() + "," +
	    		"\"message\":[\"" + message.get(0) +"\"]}";
    }
}