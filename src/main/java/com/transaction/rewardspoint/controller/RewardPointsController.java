package com.transaction.rewardspoint.controller;

import java.time.Month;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.rewardspoint.model.MonthlyRewards;
import com.transaction.rewardspoint.service.RewardService;

/**
 * Controller to handle customer reward-related requests.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardPointsController {

	@Autowired
	private RewardService rewardsService;

	/**
	 * Retrieves the reward points and transaction details for a customer for each
	 * month of a specified year.
	 *
	 * @param customerId the customer's unique ID
	 * @param year       the year for which to retrieve rewards
	 * @return a ResponseEntity containing a map of months to MonthlyRewards
	 *         objects, or a 404 Not Found status if no transactions are found for
	 *         the customer in the specified year
	 */
	@GetMapping("/monthly")
	public ResponseEntity<Map<Month, MonthlyRewards>> getCustomerRewardsByMonthly(@RequestParam String customerId,
			@RequestParam int year) {

		Map<Month, MonthlyRewards> rewards = rewardsService.getRewardsPerMonth(customerId, year);
		return ResponseEntity.ok(rewards);

	}

	/**
	 * To get the total rewards points and all transaction details for a customer in
	 * a given year.
	 *
	 * @param customerId the customer's unique ID
	 * @param year       the year for which to retrieve rewards
	 * @return a ResponseEntity containing a map with total rewards points and
	 *         transaction details, or a 404 Not Found status if no transactions are
	 *         found for the customer in the specified year
	 */
	@GetMapping("/yearly")
	public ResponseEntity<Map<String, Object>> getTotalRewardsWithDetails(@RequestParam String customerId,
			@RequestParam int year) {

		Map<String, Object> totalRewards = rewardsService.getTotalRewardsWithDetails(customerId, year);
		return ResponseEntity.ok(totalRewards);

	}
}