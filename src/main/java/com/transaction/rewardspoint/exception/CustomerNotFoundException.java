package com.transaction.rewardspoint.exception;

//Custom exception if the specified customer ID does not exist in the database
public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException(String customerId) {
		super("Customer ID not found: " + customerId);
	}
}
