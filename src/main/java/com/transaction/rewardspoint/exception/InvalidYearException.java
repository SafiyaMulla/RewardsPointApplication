package com.transaction.rewardspoint.exception;

//Custom exception if the specified year is not within the valid range
public class InvalidYearException extends RuntimeException {

	public InvalidYearException(int year) {
		super("Invalid Year: " + year);
	}
}
