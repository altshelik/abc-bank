package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

public class TransactionTest {
    
    @Test
    public void realTransactionDate() {
    	Date before = DateProvider.getInstance().now();
        Transaction tran = ActualTransactionFactory.getInstence().create(5, "Test");
        Date after = DateProvider.getInstance().now();        
        assertTrue((tran.transactionDate.before(after) || tran.transactionDate.equals(after))  &&  (tran.transactionDate.after(before) || tran.transactionDate.equals(before)));
    }
    
    @Test
    public void mockTransactionDate() {
    	Date newDate = DateDiffenreceTest.getDate("02/03/2006");
    	MockTransactionFactory tranFactory =  new MockTransactionFactory();
    	tranFactory.setDate(newDate);
    	Transaction tran = tranFactory.create(5, "Test");        
        assertEquals(tran.transactionDate, newDate);    
    }
    

}
