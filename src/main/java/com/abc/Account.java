package com.abc;

import java.util.ArrayList;
import java.util.Date;
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
			public double interestEarned(Account account) {
				double amount = account.sumTransactions();
				return amount * 0.001;
			}
		}, 
		SAVINGS()  {
			@Override
			public double interestEarned(Account account) {
				double amount = account.sumTransactions();
				if (amount <= 1000)
					return amount * 0.001;
				else
					return 1 + (amount-1000) * 0.002;
			}
		},
		MAXI_SAVINGS() {
			// TODO Handle leap year
			private final double RATE1 = 2.0/365;
			private final double RATE2 = 5.0/365;
			private final double RATE3 = 10.0/365;
			
			@Override
			public double interestEarned(Account account) {
				Transaction tran = account.getFirstTransaction();
				if(tran == null) {
					return 0; // if no transaction, then there no deposits and no money on account
				}
				double amount = account.sumTransactions();
				Date now = account.transactionFactory.create(0.0, "DUMMY").transactionDate; // HACK!
				int currentTerm =  DateProvider.daysDiff(tran.transactionDate, now);
				if (amount <= 1000)
					return (amount*dailyInterestMultiplier(RATE1, currentTerm)) - amount;
				if (amount <= 2000)	{				
					return ((1000*dailyInterestMultiplier(RATE1, currentTerm)) + ((amount - 1000)*dailyInterestMultiplier(RATE2, currentTerm))) - amount;
				}
				return ((1000*dailyInterestMultiplier(RATE1, currentTerm)) + (1000*dailyInterestMultiplier(RATE2, currentTerm)) +((amount - 2000)*dailyInterestMultiplier(RATE3, currentTerm))) - amount;
			}
		};
		abstract public double interestEarned(Account amount);
	}
	
    private final TYPE accountType;
    private List<Transaction> transactions;
    private InterestCalculationStrategy interestCalculationStrategy;
    private TransactionFactory transactionFactory;

    /**
	 *  Calculates the multiplier representing the accrued interest over the given term.
	 * 
	 * @param percent percent to be applied per cycle
	 * @param term the number of cycles to accrue over
	 * @return the multiplier representing the accrued interest
	 */
    /* Package Access - to allow easier unit testing */
    static double dailyInterestMultiplier(double percent, int term) {    
    	/* 
    	 * TODO change double to BigDecimal.
    	 * e.g. compare
    	 *  new java.math.BigDecimal(11).divide(new java.math.BigDecimal(10)).pow(5) 
         *       vs.
         *  Math.pow(1.1, 5)
    	 */
        return Math.pow(1.0 + 0.01*percent, term);
     }
    
    public Account(TYPE accountType) {
    	this(accountType, ActualTransactionFactory.getInstence());
    }

    public Account(TYPE accountType, TransactionFactory tranFactory) {
    	this.transactionFactory = tranFactory;
    	this.accountType = accountType;
    	this.transactions = new ArrayList<Transaction>();
    	switch(accountType) { //It is possible to use a Map, but switch allows more complex logic 
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
    	this.deposit(amount, "");
    }
    
    public void deposit(double amount, String comment) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(this.transactionFactory.create(amount, comment));
        }
    }

    public void withdraw(double amount) {
    	this.withdraw(amount, "");
    }
    
    public void withdraw(double amount, String comment) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add(this.transactionFactory.create(-amount, comment));
    	}
    }

    public double interestEarned() {
        return interestCalculationStrategy.interestEarned(this);
    
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
    		if(!t.comment.isEmpty()) {
     			str.append("  ");
     			str.append('(');
     			str.append(t.comment);
     			str.append(')');
            }
    		str.append("\n");
            total += t.amount;
        }
    	 str.append("Total ");
    	 str.append(Bank.toDollars(total));
    }
    
    protected Transaction getFirstTransaction() {
    	if(this.transactions.size() == 0) {
    		return null;
    	} else {
    		return this.transactions.get(0);
    	}    		
	}

}
