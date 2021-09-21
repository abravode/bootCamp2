package com.everis.bootcamp;

import org.joda.time.DateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	DateTime dt = new DateTime();
    	
        System.out.println( dt.getDayOfMonth() + "-" + dt.getMonthOfYear() + "-" +  dt.getYear());
    }
}
