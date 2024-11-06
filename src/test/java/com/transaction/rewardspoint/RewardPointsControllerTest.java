package com.transaction.rewardspoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.rewardspoint.controller.RewardPointsController;
import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.model.MonthlyRewards;
import com.transaction.rewardspoint.model.TransactionDetails;
import com.transaction.rewardspoint.service.RewardService;

@SpringBootTest
class RewardPointsControllerTest {

	@MockBean
	private RewardService rewardService;

	@Autowired
	private RewardPointsController rewardsController;

	@Test
	public void testGetCustomerRewards() throws TransactionNotFoundException, JsonProcessingException {
		Map<Month, MonthlyRewards> expectedMonthlyRewards = new HashMap<>();
		expectedMonthlyRewards.put(Month.AUGUST,
				new MonthlyRewards(150, Arrays.asList(new TransactionDetails(150.0, 150))));
		expectedMonthlyRewards.put(Month.SEPTEMBER,
				new MonthlyRewards(90, Arrays.asList(new TransactionDetails(120.0, 90))));

		when(rewardService.getRewardsPerMonth(anyString(), anyInt())).thenReturn(expectedMonthlyRewards);

		ResponseEntity<Map<Month, MonthlyRewards>> actualMonthlyRewards = rewardsController
				.getCustomerRewardsByMonthly("cust1", 2024);
		ObjectMapper objectMapper = new ObjectMapper();
		String expectedJson = objectMapper.writeValueAsString(expectedMonthlyRewards);
		String actualJson = objectMapper.writeValueAsString(actualMonthlyRewards.getBody());
		assertNotNull(actualMonthlyRewards);
		assertEquals(200, actualMonthlyRewards.getStatusCode().value());
		assertEquals(expectedJson, actualJson);

	}

	@Test
	public void testNoTransactionForCustomer() throws TransactionNotFoundException {

		when(rewardService.getRewardsPerMonth(anyString(), anyInt()))
				.thenThrow(new TransactionNotFoundException("cust1", 2024));

		ResponseEntity<Map<Month, MonthlyRewards>> response = rewardsController.getCustomerRewardsByMonthly("cust1",
				2024);
		assertEquals(404, response.getStatusCode().value());
		assertEquals(null, response.getBody());
	}

	@Test
	public void testGetTotalRewards() throws TransactionNotFoundException {
		Map<String, Object> totalRewards = new HashMap<>();
		totalRewards.put("TotalRewardsPoints", 240);

		when(rewardService.getTotalRewardsWithDetails(anyString(), anyInt())).thenReturn(totalRewards);

		ResponseEntity<Map<String, Object>> response = rewardsController.getTotalRewardsWithDetails("cust1", 2024);
		String total = "Total Rewards: " + 120;
		assertEquals(200, response.getStatusCode().value());
		assertEquals(240, response.getBody().get("TotalRewardsPoints"));
	}

	@Test
	public void testNoTransactionTotalRewards() throws TransactionNotFoundException {
		when(rewardService.getTotalRewardsWithDetails(anyString(), anyInt()))
				.thenThrow(new TransactionNotFoundException("cust1", 2024));

		ResponseEntity<Map<String, Object>> response = rewardsController.getTotalRewardsWithDetails("cust1", 2024);
		assertEquals(404, response.getStatusCode().value());
		assertEquals(null, response.getBody());
	}

}