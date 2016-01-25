package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void savings_account_LT1000() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(700.0);

        assertEquals(0.7, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void maxi_savings_account_GT1000_LT2000() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1700.0);

        assertEquals(55.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_LT1000() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(600.0);

        assertEquals(12.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test 
    public void totalInterestPaidByAllAccounts() {
    	  Bank bank = new Bank();
         
          Customer bill = new Customer("Bill");
          Account checkingAccount = new Account(Account.CHECKING);
          bank.addCustomer(bill);
          bill.openAccount(checkingAccount);
          checkingAccount.deposit(2000.0); //2.0
          Account maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
          bill.openAccount(maxiSavingAccount);
          maxiSavingAccount.deposit(3000.0); //+170.0

          Customer john = new Customer("John");
          bank.addCustomer(john);
          checkingAccount = new Account(Account.CHECKING);
          john.openAccount(checkingAccount);
          checkingAccount.deposit(600.0); //+0.6
          Account savingAccount = new Account(Account.SAVINGS);
          john.openAccount(savingAccount);
          savingAccount.deposit(1500.0); //+2.0
          
          Customer marry = new Customer("Marry");
          checkingAccount = new Account(Account.CHECKING);
          bank.addCustomer(marry);
          marry.openAccount(checkingAccount);
          checkingAccount.deposit(1600.0);  //+1.6
          maxiSavingAccount = new Account(Account.MAXI_SAVINGS);
          marry.openAccount(maxiSavingAccount);
          maxiSavingAccount.deposit(1700.0); //+55
          savingAccount = new Account(Account.SAVINGS);
          marry.openAccount(savingAccount);
          savingAccount.deposit(700.0); //0.7
          
          assertEquals(231.9, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
	public void invalidDepositOrWithdrawalAmount() {
		Account checkingAccount = new Account(Account.CHECKING);
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
}
