package com.transaction.rewardspoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application. This class intercepts
 * exceptions thrown in the application and provides meaningful HTTP responses
 * for specific exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles {@link TransactionNotFoundException} and returns a 404 NOT FOUND
	 * response.
	 *
	 * @param ex the {@code TransactionNotFoundException} thrown when a transaction
	 *           is not found
	 * @return a {@link ResponseEntity} with a 404 status and the exception's
	 *         message as the response body
	 */
	@ExceptionHandler
	public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Handles {@link CustomerNotFoundException} and returns a 404 NOT FOUND
	 * response.
	 *
	 * @param ex the {@code CustomerNotFoundException} thrown when a customer is not
	 *           found in database
	 * @return a {@link ResponseEntity} with a 404 status and the exception's
	 *         message as the response body
	 */
	@ExceptionHandler
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Handles {@link InvalidYearException} and returns a 400 BAD REQUEST response.
	 *
	 * @param ex the {@code InvalidYearException} thrown when an invalid year is
	 *           provided
	 * @return a {@link ResponseEntity} with a 400 status and the exception's
	 *         message as the response body
	 */
	@ExceptionHandler
	public ResponseEntity<String> handleInvalidYearException(InvalidYearException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
