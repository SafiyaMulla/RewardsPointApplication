package com.transaction.rewardspoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.transaction.rewardspoint.exception.TransactionNotFoundException;
import com.transaction.rewardspoint.model.Transaction;
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
	public void testGetMonthlyRewards() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("101", 120, LocalDate.of(2024, 8, 15)));
		transactions.add(new Transaction("102", 80, LocalDate.of(2024, 9, 10)));
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(createTransactions());

		Map<Month, Integer> rewards = rewardService.getRewardsPerMonth("101", 2024);
		Assertions.assertEquals(90, rewards.get(Month.AUGUST));
	}

	@Test
	public void testGetTotalRewards() {
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(createTransactions());

		int totalRewards = rewardService.getTotalRewards("101", 2024);
		assertEquals(120, totalRewards);
	}

	private List<Transaction> createTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("101", 120, LocalDate.of(2024, 8, 15)));
		transactions.add(new Transaction("102", 80, LocalDate.of(2024, 9, 10)));
		return transactions;
	}

	@Test
	public void testNoTransactionsFound() {
		when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
				.thenReturn(Collections.emptyList());

		assertThrows(TransactionNotFoundException.class, () -> {
			rewardService.getRewardsPerMonth("cust1", 2024);
		});

		assertThrows(TransactionNotFoundException.class, () -> {
			rewardService.getTotalRewards("cust1", 2024);
		});

	}
}
