package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    public final Date transactionDate;
    public final String comment;

    public Transaction(double amount, String comment) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.comment = comment;
    }

}
