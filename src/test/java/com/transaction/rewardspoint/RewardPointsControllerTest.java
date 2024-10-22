package com.transaction.rewardspoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.transaction.rewardspoint.controller.RewardPointsController;
import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.service.RewardService;

@SpringBootTest
class RewardPointsControllerTest {

	@MockBean
	private RewardService rewardService;

	@Autowired
	private RewardPointsController rewardsController;

	@Test
	public void testGetCustomerRewards() throws TransactionNotFoundException {
		Map<Month, Integer> rewards = new HashMap<>();
		rewards.put(Month.AUGUST, 90);
		rewards.put(Month.SEPTEMBER, 30);

		when(rewardService.getRewardsPerMonth(anyString(), anyInt())).thenReturn(rewards);

		Map<Month, Integer> response = rewardsController.getCustomerRewardsByMonthly("cust1", 2024);

		assertNotNull(response);
		assertEquals(90, response.get(Month.AUGUST));
		assertEquals(30, response.get(Month.SEPTEMBER));
	}

	@Test
	public void testGetTotalRewards() throws TransactionNotFoundException {
		when(rewardService.getTotalRewards(anyString(), anyInt())).thenReturn(120);

		Object totalRewards = rewardsController.getTotalRewards("cust1", 2024);
		Object total = "Total Rewards: " + 120;

		assertEquals(totalRewards, total);
	}

}