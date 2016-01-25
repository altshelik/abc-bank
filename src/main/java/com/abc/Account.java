package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

	public enum TYPE {		
		CHECKING ("Checking Account"),
		SAVINGS("Savings Account"),
		MAXI_SAVINGS("Maxi Savings Account");
		final public String name;
		TYPE(String accountTypeName) {
			name = accountTypeName;
		}
	}
    private final TYPE accountType;
    private List<Transaction> transactions;
    
    public Account(TYPE accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add(new Transaction(-amount));
    	}
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){ // TODO enum with strategy pattern can eliminate a switch.
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount - 1000) * 0.05;
                return 70 + (amount - 2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
    	double amount = 0.0;
    	for (Transaction t: transactions)
    		amount += t.amount;
    	return amount;
    }

    public TYPE getAccountType() {
        return accountType;
    }
    
    public void printStatementDetails(StringBuilder str) {
    	double total = 0.0;
    	for (Transaction t : this.transactions) {
    		str.append("  ");
    		str.append(t.amount < 0 ? "withdrawal" : "deposit");
    		str.append(" ");
    		str.append(Bank.toDollars(t.amount));
    		str.append("\n");
            total += t.amount;           
        }
    	 str.append("Total ");
    	 str.append(Bank.toDollars(total));
    }

}
