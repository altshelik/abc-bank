package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InterestCalculationTest {

	private static final double DOUBLE_DELTA = 1e-12; // need to lower because errors in the double representation accumulate quickly
	
	@Test
	public void testDailyInterestCalculation()
	{
		double amount = 1000.0; 
		int term = 5;
		double percent = 10.0; 
		// 1000.0, 1100.0, 1210, 1331.0, 1433.1, 1576.41, 1610.51
		assertEquals(1610.51, amount*Account.dailyInterestMultiplier(percent, term), DOUBLE_DELTA);
	}
	
	@Test
	public void testDailyAccrualOnMaxiSaving1000() {
		/*
		 * 3 days
		 * First 1000 
		 * rate 2%/365 daily
		 * sum = 1000.164392569127
		 * 
		 * total 0.164392569127
		 * 
		 */
		
		Bank bank = new Bank();
		MockTransactionFactory factory = new MockTransactionFactory();
		Account account = new Account(Account.TYPE.MAXI_SAVINGS, factory);
		bank.addCustomer(new Customer("Bill").openAccount(account));
		factory.setDate(DateDiffenreceTest.getDate("01/01/2012"));
		account.deposit(1000.0);
		factory.setDate(DateDiffenreceTest.getDate("01/04/2012"));
		assertEquals(0.164392569127, bank.totalInterestPaid(), DOUBLE_DELTA); 
	}
	
	@Test
	public void testDailyAccrualOnMaxiSaving2000() {
		/*
		 * 3 days
		 * First 1000 
		 * rate 2%/365 daily
		 * sum = 1000.164392569127
		 * 
		 * Second 1000
		 * rate 5%/365 daily
		 * sum = 1000.4110152024203
		 * 
		 * total = 0.5754077715473
		 */
		
		Bank bank = new Bank();
		MockTransactionFactory factory = new MockTransactionFactory();
		Account account = new Account(Account.TYPE.MAXI_SAVINGS, factory);
		bank.addCustomer(new Customer("Bill").openAccount(account));
		factory.setDate(DateDiffenreceTest.getDate("01/01/2012"));
		account.deposit(2000.0);
		factory.setDate(DateDiffenreceTest.getDate("01/04/2012"));
		assertEquals(0.5754077715473, bank.totalInterestPaid(), DOUBLE_DELTA); 
	}
	
	
	@Test
	public void testDailyAccrualOnMaxiSaving() {
		/*
		 * 3 days
		 * First 1000 
		 * rate 2%/365 daily
		 * sum = 1000.164392569127
		 * 
		 * Second 1000
		 * rate 5%/365 daily
		 * sum = 1000.4110152024203
		 * 
		 * the rest 3000
		 * rate 10%/365 daily
		 * sum = 3002.4664290352343
		 * 
		 * total 3.0418368067816
		 */
		
		Bank bank = new Bank();
		MockTransactionFactory factory = new MockTransactionFactory();
		Account account = new Account(Account.TYPE.MAXI_SAVINGS, factory);
		bank.addCustomer(new Customer("Bill").openAccount(account));
		factory.setDate(DateDiffenreceTest.getDate("01/01/2012"));
		account.deposit(5000.0);
		factory.setDate(DateDiffenreceTest.getDate("01/04/2012"));
		assertEquals(3.0418368067816, bank.totalInterestPaid(), DOUBLE_DELTA); 
	}
	
	
}
