package com.transaction.rewardspoint.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.transaction.rewardspoint.model.Transaction;

/**
 * Repository for managing {@link Transaction} data.
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {

	/**
	 * Finds transactions for a customer within a specified date range.
	 *
	 * @param customerId the customer's unique ID
	 * @param startDate  the start of the date range
	 * @param endDate    the end of the date range
	 * @return a list of transactions within the date range
	 */
	List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate startDate,
			LocalDate endDate);
}
