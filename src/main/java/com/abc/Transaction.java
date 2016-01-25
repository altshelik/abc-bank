package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    public final Date transactionDate;
    public final String comment;
    
    public Transaction(double amount, String comment) {
    	this(amount, comment, DateProvider.getInstance().now());
    }
    
    protected Transaction(double amount, String comment, Date date) {
    	this.amount = amount;
        this.transactionDate = date;
    	this.comment = comment;
    }
}
