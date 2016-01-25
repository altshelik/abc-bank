package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder( "Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n");
            printStatementForAccount(a , statement);
            statement.append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    private String printStatementForAccount(Account a, StringBuilder str) {
    	 str.append(a.getAccountType().name + "\n");
         a.printStatementDetails(str);
         return str.toString();
    }

    private String toDollars(double d) {
        return Bank.toDollars(d);
    }
    
    /**
     * Transfers money from one account to another account 
     * 
     * @param amount - amount of maney to be transfered to another amount
     * @param fromAccount - Account instance to transfer money from
     * @param toAccount - Account instance to transfer money to
     */
    public void transfer(double amount, Account fromAccount, Account toAccount) {
    	// Validate that both accounts belongs to this customer
    	if(!this.accounts.contains(fromAccount)) {
    		throw new IllegalArgumentException("Source account does not belong to the customer");
    	}
    	if(!this.accounts.contains(toAccount)) {
    		throw new IllegalArgumentException("Target account does not belong to the customer");
    	}
    	// Validate transfer from and to the same account
    	if(fromAccount == toAccount) {
    		throw new IllegalArgumentException("Cannot transfer money from and to the same account");
    	}
    	fromAccount.withdraw(amount, "Transferred to another " + toAccount.getAccountType().name);
    	toAccount.deposit(amount, "Transferred from another " + fromAccount.getAccountType().name);
    }
}
