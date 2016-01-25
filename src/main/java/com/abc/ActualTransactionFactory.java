package com.abc;

public class ActualTransactionFactory implements TransactionFactory {

	static private ActualTransactionFactory instance;
	
	static public ActualTransactionFactory getInstence() {
		if(instance == null) {
			instance = new ActualTransactionFactory();
		}
		return instance;
	}
	
	private ActualTransactionFactory() {		
	}
	
	@Override
	public Transaction create(double amount, String comment) {
		return new Transaction(amount, comment);
	}

}
