package com.transaction.rewardspoint.model;

public class TransactionDetails {

	double amount;
	int rewardsPoints;

	public TransactionDetails(double amount, int rewardsPoints) {

		this.amount = amount;
		this.rewardsPoints = rewardsPoints;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRewardsPoints() {
		return rewardsPoints;
	}

	public void setRewardsPoints(int rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}

}
