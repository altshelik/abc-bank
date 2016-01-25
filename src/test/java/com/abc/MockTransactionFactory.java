package com.abc;

import java.util.Date;

public class MockTransactionFactory implements TransactionFactory {

	private Date currentDate;
	
	@Override
	public Transaction create(double amount, String comment) {
		return new MockTransaction(amount, comment, currentDate);
	}
	public void setDate(Date aDate) {
		this.currentDate = aDate;
	}
	
}
