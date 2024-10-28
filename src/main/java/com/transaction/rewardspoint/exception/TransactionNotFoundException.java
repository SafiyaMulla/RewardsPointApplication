package com.transaction.rewardspoint.exception;

//Custom exception for no transactions
public class TransactionNotFoundException extends RuntimeException {
	public TransactionNotFoundException(String customerId, int year) {
		super("No transactions found for customer " + customerId + " in year " + year);
	}
}
