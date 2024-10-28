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

@Service
@Slf4j
public class RewardService {

	@Autowired
	private TransactionRepository transactionRepository;
	private static Logger logger = LoggerFactory.getLogger(RewardService.class);

	/**
	 * This method is used for finding monthly rewards point by given customer for
	 * the year.
	 * 
	 * @param customerId
	 * @param year
	 * @return
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
	 * Calculate Total reward points for a given transaction.
	 * 
	 * @param transaction
	 * @return
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
	 * This method is used for finding total rewards point by given customer for
	 * year.
	 * 
	 * @param customerId
	 * @param year
	 * @return
	 */
	public int getTotalRewards(String customerId, int year) {
		logger.info("Fetched total transactions for customer: {}, year: {}", customerId, year);
		return getRewardsPerMonth(customerId, year).values().stream().mapToInt(Integer::intValue).sum();

	}

}
