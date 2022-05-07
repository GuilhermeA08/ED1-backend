package com.ed1.article.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "An unexpected internal error has occurred. Please try again and if the problem persists, contact your system administrator";

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpErrorInfo handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> msg = new ArrayList<>();
        BindingResult cause = ex.getBindingResult();
        List<ObjectError> firstProblem = cause.getAllErrors();

        if (firstProblem != null) {
            firstProblem.stream().forEach(error -> {
            	msg.add(error.getDefaultMessage());
            });
        }

        return createHttpErrorInfo(msg);
    }
    
    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public HttpErrorInfo handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
    	List<String> msg = new ArrayList<>();
    	msg.add("Método não habilitado. Verifique se o método e/ou a URL está correta");
    	
    	return createHttpErrorInfo(msg);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public HttpErrorInfo handleTransactionSystemException(TransactionSystemException ex) {
        Throwable cause = ex.getRootCause();
        String msg = "";
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            msg = constraintViolations.stream().findFirst().map(f -> f.getMessage()).orElse("");
        }
        
        List<String> msgList = new ArrayList<>();
        msgList.add(msg);
        
        return createHttpErrorInfo(msgList);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpErrorInfo handleExceptions(Exception ex) {
        log.info("Message: {}", ex);
        List<String> msg = new ArrayList<>();
        msg.add(GENERIC_ERROR_MESSAGE);
        
        return createHttpErrorInfo(msg);
    }
    
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public HttpErrorInfo handleEntityNotFoundException(EntityNotFoundException ex) {
    	List<String> errors = new ArrayList<>();
    	errors.add(ex.getMessage());
    	return createHttpErrorInfo(errors);
    }
    
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public HttpErrorInfo handleRuntimeExceptions(RuntimeException ex) {
        log.info("Message: {}", ex.getMessage());
        
        Throwable t = ex.getCause();
        if(t instanceof org.hibernate.exception.ConstraintViolationException) {
        	List<String> error = new ArrayList<>();
        	
        	String message = t.getCause().getMessage();
        	
			// Pegar qual campo que está duplicado e qual o valor dele
			String field = message.substring(message.indexOf("(") + 1, message.indexOf(")"));
			String value = message.substring(message.lastIndexOf("(") + 1 , message.lastIndexOf(")"));
			
			// Pegar a causa da excessão
			String cause = message.substring(message.indexOf(")"), message.indexOf(".") + 1);
			cause = cause.substring(cause.indexOf(" ") + 1, cause.indexOf(".") + 1);
			
			if(cause.startsWith("is not present in table")) {
				error.add(field.toUpperCase() + " not found, try again with another " + field.toUpperCase());
			}else {
				error.add(field.toUpperCase() + " " + value + " already registered, try again with another " + field.toUpperCase());
			}
			
			return createHttpErrorInfo(error);
		}
        
        List<String> msg = new ArrayList<>();
        msg.add(ex.getMessage());
        
        return createHttpErrorInfo(msg);
    }
    

    private HttpErrorInfo createHttpErrorInfo(List<String> msg) {
        return HttpErrorInfo.builder()
                .message(msg)
                .build();
    }

}
