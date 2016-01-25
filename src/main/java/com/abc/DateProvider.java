package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static int daysDiff(Date d1, Date d2) {
    	Date startDate = resetToMidnight(d1);
    	Date endDate = resetToMidnight(d2);
    		
        long diff = Math.abs(endDate.getTime() - startDate.getTime());
        return (int)(diff * 1.0 / (24 * 60 * 60 * 1000));
   }
    
    public static Date resetToMidnight(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
}