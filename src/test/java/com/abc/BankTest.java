package com.abc;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;


public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.TYPE.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account_GT1000() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account_LT1000() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(700.0);

		assertEquals(0.7, bank.totalInterestPaid(), DOUBLE_DELTA);
	}


	@Ignore  //this is now obsolete as behavior has changed
	public void maxi_savings_account_GT2000() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(5000.0);

		assertEquals(3.0418368067816, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Ignore//this is now obsolete as behavior has changed
	public void maxi_savings_account_GT1000_LT2000() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1700.0);

		assertEquals(55.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Ignore //this is now obsolete as the behavior has changed
	public void maxi_savings_account_LT1000() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.TYPE.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(600.0);

		assertEquals(12.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void invalidDepositOrWithdrawalAmount() {
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		try {
			checkingAccount.deposit(0.0);
			fail("Expected an IllegalArgumentException to be thrown");        	 
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "amount must be greater than zero");
		}
		try {
			checkingAccount.withdraw(0.0);
			fail("Expected an IllegalArgumentException to be thrown");        	 
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "amount must be greater than zero");
		}
		try {
			checkingAccount.deposit(-1);
			fail("Expected an IllegalArgumentException to be thrown");        	 
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "amount must be greater than zero");
		}
		try {
			checkingAccount.withdraw(-1);
			fail("Expected an IllegalArgumentException to be thrown");        	 
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "amount must be greater than zero");
		}          
	}


	@Test
	public void totalInterestPaidByAllAccounts() {
		Bank bank = new Bank();
		
		MockTransactionFactory factory = new MockTransactionFactory();
		factory.setDate(DateDiffenreceTest.getDate("01/01/2001"));
		Customer bill = new Customer("Bill");
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		bank.addCustomer(bill);
		bill.openAccount(checkingAccount);
		checkingAccount.deposit(2000.0); //2.0
		Account maxiSavingAccount = new Account(Account.TYPE.MAXI_SAVINGS, factory);
		bill.openAccount(maxiSavingAccount);
		maxiSavingAccount.deposit(5000.0); //3.0418368067816

		Customer john = new Customer("John");
		bank.addCustomer(john);
		checkingAccount = new Account(Account.TYPE.CHECKING);
		john.openAccount(checkingAccount);
		checkingAccount.deposit(600.0); //+0.6
		Account savingAccount = new Account(Account.TYPE.SAVINGS);
		john.openAccount(savingAccount);
		savingAccount.deposit(1500.0); //+2.0

		Customer marry = new Customer("Marry");
		checkingAccount = new Account(Account.TYPE.CHECKING);
		bank.addCustomer(marry);
		marry.openAccount(checkingAccount);
		checkingAccount.deposit(1600.0);  //+1.6
		maxiSavingAccount = new Account(Account.TYPE.MAXI_SAVINGS, factory);
		marry.openAccount(maxiSavingAccount);
		maxiSavingAccount.deposit(1000.0); //0.164392569127
		savingAccount = new Account(Account.TYPE.SAVINGS);
		marry.openAccount(savingAccount);
		savingAccount.deposit(700.0); //0.7
		factory.setDate(DateDiffenreceTest.getDate("01/04/2001"));
		assertEquals(10.1062293759086, bank.totalInterestPaid(), 1e-12); //need to lower precision. Can be fixed if use BigDecimal instead of double
	}

	@Test
	public void transferToAnotherCustomer() {
		Customer henry = new Customer("Henry");
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		henry.openAccount(checkingAccount);
		checkingAccount.deposit(1000.0);
		Customer john = new Customer("John");
		Account savingsAccount = new Account(Account.TYPE.SAVINGS);
		john.openAccount(savingsAccount);
		try {
			henry.transfer(500.0, checkingAccount, savingsAccount);
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "Target account does not belong to the customer");
		}
	}

	@Test
	public void transferFromAnotherCustomer() {
		Customer henry = new Customer("Henry");
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		henry.openAccount(checkingAccount);
		checkingAccount.deposit(1000.0);
		Customer john = new Customer("John");
		Account savingsAccount = new Account(Account.TYPE.SAVINGS);
		john.openAccount(savingsAccount);
		try {
			henry.transfer(500.0, savingsAccount, checkingAccount);
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "Source account does not belong to the customer");
		}
	}

	@Test
	public void transferToTheSameAccount() {
		Customer henry = new Customer("Henry");
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		henry.openAccount(checkingAccount);
		checkingAccount.deposit(1000.0);
		try {
			henry.transfer(500.0, checkingAccount, checkingAccount);
		} catch (IllegalArgumentException ex){
			assertEquals(ex.getMessage(), "Cannot transfer money from and to the same account");
		}
	}

	@Test
	public void transferAmount() {
		Customer henry = new Customer("Henry");
		Account checkingAccount = new Account(Account.TYPE.CHECKING);
		henry.openAccount(checkingAccount);
		checkingAccount.deposit(1000.0);
		Account savingsAccount = new Account(Account.TYPE.SAVINGS);
		henry.openAccount(savingsAccount);
		henry.transfer(400.0, checkingAccount, savingsAccount);
		assertEquals(600.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals(400.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);

	}

}
