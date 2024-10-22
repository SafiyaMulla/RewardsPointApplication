package com.transaction.rewardspoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RewardsApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}
