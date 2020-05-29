package com.development.myproject.creditCard.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;

import com.development.myproject.creditCard.service.IAccountService;


public class CommonUtil {
	
	
	
	public IAccountService getAccountServiceByName(@Qualifier("myFirstRepository") IAccountService accountService) {
		
	   return accountService;
        
	}
	
	
	
	public static boolean hasAvalue(List<Object> list) {
		return Objects.nonNull(list)&&!list.isEmpty();
	}
	public static boolean hasAvalue(Object object) {
		return Objects.nonNull(object);
	}
	
	public static double calculateAmountByPercentage(double amount ,double percentage) {
		
		return (percentage*amount) / 100;
	}
	
	public static boolean compareTwoDate(Date d1, Date d2) {
		return d1.after(d2) || d1.equals(d2);
	}
	
	public static Date getDateByGivenYears(int noOfYears) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, noOfYears); // to get previous year add -1
		return cal.getTime();
	}
	
	public static Date getDateByGivenMonths(int noOfMonths) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, noOfMonths); // to get previous year add -1
		return cal.getTime();
	}
	
	public static Date getDateByGivenDays(int days) {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, days);
	    return cal.getTime();
	}


}
