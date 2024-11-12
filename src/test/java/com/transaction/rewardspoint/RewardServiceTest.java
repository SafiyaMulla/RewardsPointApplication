package com.transaction.rewardspoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.rewardspoint.exception.CustomerNotFoundException;
import com.transaction.rewardspoint.exception.InvalidYearException;
import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.model.MonthlyRewards;
import com.transaction.rewardspoint.model.Transaction;
import com.transaction.rewardspoint.model.TransactionDetails;
import com.transaction.rewardspoint.repository.TransactionRepository;
import com.transaction.rewardspoint.service.RewardService;

@SpringBootTest
public class RewardServiceTest {

	@Autowired
	private RewardService rewardService;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	public void testCalculatePoints() {
		Transaction transaction = new Transaction("101", 120, LocalDate.of(2024, 1, 2));
		assertEquals(90, rewardService.calculateRewardPoints(transaction));

	}

	@Test
	public void testGetMonthlyRewards() throws JsonProcessingException {
		when(transactionRepository.existByCustomerId(anyString())).thenReturn(true);
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(createTransactions());
		ObjectMapper objectMapper = new ObjectMapper();
		Map<Month, MonthlyRewards> expectedRewards = new HashMap<>();
		expectedRewards.put(Month.AUGUST, new MonthlyRewards(150, Arrays.asList(new TransactionDetails(150.0, 150))));
		expectedRewards.put(Month.SEPTEMBER, new MonthlyRewards(90, Arrays.asList(new TransactionDetails(120.0, 90))));

		Map<Month, MonthlyRewards> actualRewards = rewardService.getRewardsPerMonth("customer1", 2024);
		String expectedJson = objectMapper.writeValueAsString(expectedRewards);
		String actualJson = objectMapper.writeValueAsString(actualRewards);

		assertEquals(expectedJson, actualJson);

	}

	@Test
	public void testGetTotalRewards() {
		when(transactionRepository.existByCustomerId(anyString())).thenReturn(true);
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(createTransactions());
		Map<String, Object> totalRewards = rewardService.getTotalRewardsWithDetails("customer1", 2024);
		assertEquals(240, totalRewards.get("TotalRewardsPoints"));

	}

	private List<Transaction> createTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("customer1", 150, LocalDate.of(2024, 8, 15)));
		transactions.add(new Transaction("customer1", 120, LocalDate.of(2024, 9, 10)));
		return transactions;
	}

	@Test
	public void testGetRewardsPerMonthNoTransactionsFound() {
		String customerId = "123";
		int year = 2023;

		when(transactionRepository.existByCustomerId(anyString())).thenReturn(true);
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(Collections.emptyList());

		assertThrows(TransactionNotFoundException.class, () -> rewardService.getRewardsPerMonth(customerId, year));
	}

	@Test
	public void testTransactionYearChecksInvalidYear() {
		int futureYear = 3000;
		assertThrows(InvalidYearException.class, () -> rewardService.transactionYearChecks(futureYear));
	}

	@Test
	public void testGetRewardsPerMonthCustomerNotFound() {
		String customerId = "invalid";
		int year = 2023;

		when(transactionRepository.existByCustomerId(customerId)).thenReturn(false);

		assertThrows(CustomerNotFoundException.class, () -> rewardService.getRewardsPerMonth(customerId, year));
	}
}
