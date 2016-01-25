package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    static public String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {// TODO eliminate += and + usage with String in a loop. Use String builder instead.
            summary.append("\n - ");
            summary.append(c.getName());
            summary.append(" (");
            summary.append(format(c.getNumberOfAccounts(), "account"));
            summary.append(')');
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
}
