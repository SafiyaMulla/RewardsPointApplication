//package com.transaction.rewardspoint;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import java.time.Month;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//
//import com.transaction.rewardspoint.controller.RewardPointsController;
//import com.transaction.rewardspoint.exception.TransactionNotFoundException;
//import com.transaction.rewardspoint.service.RewardService;
//
//@SpringBootTest
//class RewardPointsControllerTest {
//
//	@MockBean
//	private RewardService rewardService;
//
//	@Autowired
//	private RewardPointsController rewardsController;
//
//	@Test
//	public void testGetCustomerRewards() throws TransactionNotFoundException {
//		Map<Month, Integer> rewards = new HashMap<>();
//		rewards.put(Month.AUGUST, 90);
//		rewards.put(Month.SEPTEMBER, 30);
//
//		when(rewardService.getRewardsPerMonth(anyString(), anyInt())).thenReturn(rewards);
//
//		ResponseEntity<Map<Month, Integer>> response = rewardsController.getCustomerRewardsByMonthly("cust1", 2024);
//		assertNotNull(response);
//		assertEquals(200, response.getStatusCode().value());
//		assertEquals(90, response.getBody().get(Month.AUGUST));
//		assertEquals(30, response.getBody().get(Month.SEPTEMBER));
//	}
//
//	@Test
//	public void testNoTransactionForCustomer() throws TransactionNotFoundException {
//
//		when(rewardService.getRewardsPerMonth(anyString(), anyInt()))
//				.thenThrow(new TransactionNotFoundException("cust1", 2024));
//
//		ResponseEntity<Map<Month, Integer>> response = rewardsController.getCustomerRewardsByMonthly("cust1", 2024);
//		assertEquals(404, response.getStatusCode().value());
//		assertEquals(null, response.getBody());
//	}
//
//	@Test
//	public void testGetTotalRewards() throws TransactionNotFoundException {
//		when(rewardService.getTotalRewards(anyString(), anyInt())).thenReturn(120);
//
//		ResponseEntity<String> response = rewardsController.getTotalRewards("cust1", 2024);
//		String total = "Total Rewards: " + 120;
//		assertEquals(200, response.getStatusCode().value());
//		assertEquals(total, response.getBody());
//	}
//
//	@Test
//	public void testNoTransactionTotalRewards() throws TransactionNotFoundException {
//		when(rewardService.getTotalRewards(anyString(), anyInt()))
//				.thenThrow(new TransactionNotFoundException("cust1", 2024));
//
//		ResponseEntity<String> response = rewardsController.getTotalRewards("cust1", 2024);
//		assertEquals(404, response.getStatusCode().value());
//		assertEquals(null, response.getBody());
//	}
//
//}