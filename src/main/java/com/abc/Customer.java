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
}
