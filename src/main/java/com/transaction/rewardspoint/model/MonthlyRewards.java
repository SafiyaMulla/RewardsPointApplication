package com.transaction.rewardspoint.model;

import java.util.List;

public class MonthlyRewards {

	private int totalPoints;
	private List<TransactionDetails> transactions;

	public MonthlyRewards(int totalPoints, List<TransactionDetails> transactions) {

		this.totalPoints = totalPoints;
		this.transactions = transactions;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public List<TransactionDetails> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDetails> transactions) {
		this.transactions = transactions;
	}

}
