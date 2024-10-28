package com.transaction.rewardspoint.controller;

import java.time.Month;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.service.RewardService;

@RestController
@RequestMapping("/api/rewards")
public class RewardPointsController {

	@Autowired
	private RewardService rewardsService;

	@GetMapping("/monthly")
	public ResponseEntity<Map<Month, Integer>> getCustomerRewardsByMonthly(@RequestParam String customerId,
			@RequestParam int year) {

		try {
			Map<Month, Integer> rewards = rewardsService.getRewardsPerMonth(customerId, year);
			return ResponseEntity.ok(rewards);
		} catch (TransactionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/yearly")
	public ResponseEntity<String> getTotalRewards(@RequestParam String customerId, @RequestParam int year) {
		try {
			String totalRewards = "Total Rewards: " + rewardsService.getTotalRewards(customerId, year);
			return ResponseEntity.ok(totalRewards);

		} catch (TransactionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}