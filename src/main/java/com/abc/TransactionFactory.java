package com.abc;

/**
 * A factory interface that  that creates Transaction instances. 
 * Needed for dependency injection to allow mocking dates in tests.
 */
public interface TransactionFactory {
	public Transaction create(double amount, String comment);	
}
