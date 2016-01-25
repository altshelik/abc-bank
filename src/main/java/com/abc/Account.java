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
	
	private enum InterestCalculationStrategy {
		CHECKING()
		{
			@Override
			public double interestEraned(double amount) {
				return amount * 0.001;
			}

		}, 
		SAVINGS()  {
			@Override
			public double interestEraned(double amount) {
				if (amount <= 1000)
					return amount * 0.001;
				else
					return 1 + (amount-1000) * 0.002;
			}
		},
		MAXI_SAVINGS() {
			@Override
			public double interestEraned(double amount) {
				if (amount <= 1000)
					return amount * 0.02;
				if (amount <= 2000)
					return 20 + (amount-1000) * 0.05;
				return 70 + (amount-2000) * 0.1;
			}

		};
		abstract public double interestEraned(double amount);
	}
    private final TYPE accountType;
    private List<Transaction> transactions;
    private InterestCalculationStrategy interestCalculationStrategy;

    public Account(TYPE accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        switch(accountType) { //It is possible to use a map or enumMap here, but switch is more useful if in a future the strategy will depend on more then just account type (holiday discount for example) 
        	case MAXI_SAVINGS:
        		interestCalculationStrategy = InterestCalculationStrategy.MAXI_SAVINGS;
        		break;
        	case SAVINGS:
        		interestCalculationStrategy = InterestCalculationStrategy.SAVINGS;
        		break;
        	default:
        		interestCalculationStrategy = InterestCalculationStrategy.CHECKING;     			
        }
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
        return interestCalculationStrategy.interestEraned(amount);
    
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
