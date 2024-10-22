package com.transaction.rewardspoint.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.transaction.rewardspoint.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate startDate,
			LocalDate endDate);
}
