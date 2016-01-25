package com.abc;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.fail;

public class DateDiffenreceTest {
	
	@Test
	public void testResetingDateToMidnight() {
			Date dateWithTime = getDateWithTime("01/01/2001 22:30:08.666");
			Date expectedDateAtMidnight = getDateWithTime("01/01/2001 00:00:00.000");
			Date dateCaculated = DateProvider.resetToMidnight(dateWithTime);
			assertEquals(dateCaculated, expectedDateAtMidnight);
		
	}
	
	@Test
	public void testSimpleDateDifference()  {
			Date startDate = getDate("01/01/2001");
			Date endDate = getDate("01/25/2001");
			int diff = DateProvider.daysDiff(startDate, endDate);
			assertEquals(24,diff);
	}
	
	@Test
	public void testSimpleEndOfDateDifference() {
			Date startDate = getDate("05/20/2001 23:59:59.999");
			Date endDate = getDate("05/25/2001");
			int diff = DateProvider.daysDiff(startDate, endDate);
			assertEquals(5,diff);
	}
	
	@Test
	public void testCrossingMonthDateDifference() {
			Date startDate = getDate("01/25/2001");
			Date endDate = getDate("02/05/2001");
			int diff = DateProvider.daysDiff(startDate, endDate);
			assertEquals(11,diff);
	}
	
	@Test
	public void testOneYearDateDifference() {
			Date startDate = getDate("01/1/2001");
			Date endDate = getDate("01/1/2002");
			int diff = DateProvider.daysDiff(startDate, endDate);
			assertEquals(365,diff);
	}
	
	@Test
	public void testOneLeapYearDateDifference() {
			Date startDate = getDate("01/01/2004");
			Date endDate = getDate("01/01/2005");
			int diff = DateProvider.daysDiff(startDate, endDate);
			assertEquals(366,diff);
	}
	
	@Test
	public void testBasicDateDifference() {
			Date startDate = getDate("02/20/2004");
			Date endDate = getDate("03/05/2004");
			assertEquals(14, DateProvider.daysDiff(startDate, endDate));
	}
	
	static public Date getDate(String dateString) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
		} catch (ParseException e) {
			fail("Date Formatting error: " + e.getMessage());
		}
		return null;
	}
	
	static public Date getDateWithTime(String dateTimeString) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS").parse(dateTimeString);
		} catch (ParseException e) {
			fail("Date Formatting error: " + e.getMessage());
		}
		return null;
	}
	
	
}
