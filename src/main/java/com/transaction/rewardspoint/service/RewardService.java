package com.transaction.rewardspoint.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.rewardspoint.exception.CustomerNotFoundException;
import com.transaction.rewardspoint.exception.InvalidYearException;
import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.model.MonthlyRewards;
import com.transaction.rewardspoint.model.Transaction;
import com.transaction.rewardspoint.model.TransactionDetails;
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
	 * @return a map of months to reward points and transaction details
	 */

	public Map<Month, MonthlyRewards> getRewardsPerMonth(String customerId, int year) {

		customerIdChecks(customerId);
		transactionYearChecks(year);

		Map<Month, MonthlyRewards> rewardsPerMonth = new HashMap<>();
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);

		List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId,
				startDate, endDate);
		if (transactions.isEmpty()) {
			logger.warn("No transactions found for customer {} in year {}", customerId, year);
			throw new TransactionNotFoundException(customerId, year);
		}
		logger.info("Fetched transactions for customer: {}, year: {}", customerId, year);

		for (Transaction transaction : transactions) {
			Month month = transaction.getTransactionDate().getMonth();
			int rewardPoints = calculateRewardPoints(transaction);

			rewardsPerMonth.computeIfAbsent(month, k -> new MonthlyRewards(0, new ArrayList<>()));

			TransactionDetails transactionDetails = new TransactionDetails(transaction.getAmount(), rewardPoints);

			MonthlyRewards monthlyRewards = rewardsPerMonth.get(month);
			monthlyRewards.getTransactions().add(transactionDetails);
			monthlyRewards.setTotalPoints(monthlyRewards.getTotalPoints() + rewardPoints);
		}

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
	 * Gets the total rewards points and all transaction details for a customer in a
	 * given year.
	 *
	 * @param customerId the customer's unique ID
	 * @param year       the year for which to calculate total rewards
	 * @return a map containing total rewards points and all transaction details
	 */
	public Map<String, Object> getTotalRewardsWithDetails(String customerId, int year) {
		Map<Month, MonthlyRewards> rewardsPerMonth = getRewardsPerMonth(customerId, year);
		int totalPoints = rewardsPerMonth.values().stream().mapToInt(MonthlyRewards::getTotalPoints).sum();

		Map<String, Object> result = new HashMap<>();
		result.put("TotalRewardsPoints", totalPoints);
		result.put("MonthlyRewards", rewardsPerMonth);

		return result;
	}

	/**
	 * Validates the existence of a customer by their unique identifier. This method
	 * checks if the specified customer ID exists in the database, and if not, it
	 * logs a warning and throws a {@link CustomerNotFoundException}.
	 *
	 * @param customerId the unique identifier of the customer to check
	 * @throws CustomerNotFoundException if the specified customer ID does not exist
	 *                                   in the database
	 */
	public void customerIdChecks(String customerId) {
		if (!(transactionRepository.existByCustomerId(customerId))) {
			logger.warn("Customer ID not found:" + customerId);
			throw new CustomerNotFoundException(customerId);
		}
	}

	/**
	 * Validates the specified year for transaction processing. This method checks
	 * if the provided year is within a valid range (from 1900 up to the current
	 * year). If the year is out of range, it logs a warning and throws an
	 * {@link InvalidYearException}.
	 *
	 * @param year the year to be validated for transaction processing
	 * @throws InvalidYearException if the specified year is not within the valid
	 *                              range (1900 to the current year)
	 * 
	 */
	public void transactionYearChecks(int year) {
		int currentYear = Year.now().getValue();
		if (!(year >= 1900 && year <= currentYear)) {
			logger.warn("Invalid Year:" + year);
			throw new InvalidYearException(year);
		}

	}

}
