package com.transaction.rewardspoint.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.model.Transaction;
import com.transaction.rewardspoint.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service to calculate reward points for customer transactions.
 */
@Service
@Slf4j
public class RewardService {

	@Autowired
	private TransactionRepository transactionRepository;
	private static Logger logger = LoggerFactory.getLogger(RewardService.class);

	/**
	 * Calculates the reward points for each month of a given year for a customer.
	 *
	 * @param customerId the customer's unique ID
	 * @param year       the year for which to calculate rewards
	 * @return a map of months to reward points
	 */
	public Map<Month, Integer> getRewardsPerMonth(String customerId, int year) {

		Map<Month, Integer> rewardsPerMonth = new HashMap<>();
		for (Month month : Month.values()) {
			rewardsPerMonth.put(month, 0);
		}

		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);

		List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId,
				startDate, endDate);
		if (transactions.isEmpty()) {
			logger.warn("No transactions found for customer {} in year {}", customerId, year);
			throw new TransactionNotFoundException(customerId, year);
		}
		logger.info("Fetched transactions for customer: {}, year: {}", customerId, year);

		Map<Month, Integer> calculatedRewards = transactions.stream()
				.collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth(),
						Collectors.summingInt(this::calculateRewardPoints)));

		calculatedRewards.forEach(rewardsPerMonth::put);

		return rewardsPerMonth;
	}

	/**
	 * Calculates the reward points for a specific transaction.
	 *
	 * @param transaction the transaction for which to calculate points
	 * @return the calculated reward points
	 */
	public int calculateRewardPoints(Transaction transaction) {
		double amount = transaction.getAmount();
		int points = 0;
		if (amount > 100) {
			points = (int) (amount - 100) * 2 + 50;
		} else {
			points = (int) (amount - 50);
		}
		return points;
	}

	/**
	 * Calculates the total reward points for a customer in a given year.
	 *
	 * @param customerId the customer's unique ID
	 * @param year       the year for which to calculate total rewards
	 * @return the total reward points
	 */
	public int getTotalRewards(String customerId, int year) {
		logger.info("Fetched total transactions for customer: {}, year: {}", customerId, year);
		return getRewardsPerMonth(customerId, year).values().stream().mapToInt(Integer::intValue).sum();

	}

}
