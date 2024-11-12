package com.transaction.rewardspoint.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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

	/**
	 * Checks for the existence of a customer by their unique identifier. This
	 * method uses a MongoDB query to determine if a document with the specified
	 * customer ID exists in the database.
	 *
	 * @param customerId the unique identifier of the customer to check
	 * @return {@code true} if a customer with the specified ID exists,
	 *         {@code false} otherwise
	 */
	@Query(value = "{ 'customerId' : ?0 }", exists = true)
	boolean existByCustomerId(String customerId);
}
