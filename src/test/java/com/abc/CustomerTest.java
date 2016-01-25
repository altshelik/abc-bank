package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {

    @Test //Test customer statement generation.
    public void testApp(){

        Account checkingAccount = new Account(Account.TYPE.CHECKING);
        Account savingsAccount = new Account(Account.TYPE.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.TYPE.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.TYPE.SAVINGS));
        oscar.openAccount(new Account(Account.TYPE.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test // Test customer statement generation with new transfer transaction
    public void testAppWithTransfer(){

        Account checkingAccount = new Account(Account.TYPE.CHECKING);
        Account savingsAccount = new Account(Account.TYPE.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        henry.transfer(300, checkingAccount, savingsAccount);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $300.00  (Transferred to another Savings Account)\n" +
                "Total $700.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "  deposit $300.00  (Transferred from another Checking Account)\n" +
                "Total $4,100.00\n" +
                "\n" +
                "Total In All Accounts $4,800.00", henry.getStatement());
    }
}
