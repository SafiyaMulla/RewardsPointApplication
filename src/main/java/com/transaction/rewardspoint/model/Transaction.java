package com.transaction.rewardspoint.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a transaction made by a customer.
 */
@Document(collection = "Transaction")
public class Transaction {

	/** Unique identifier for the transaction. */
	@Id
	private String id;

	/** ID of the customer associated with the transaction. */
	private String customerId;

	/** Amount spent in the transaction. */
	private double amount;

	/** Date and time when the transaction was made. */
	private LocalDate transactionDate;

	public String getCustomerId() {
		return customerId;
	}

	public Transaction(String customerId, double amount, LocalDate transactionDate) {
		super();
		this.customerId = customerId;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

}
