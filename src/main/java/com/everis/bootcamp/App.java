package com.everis.bootcamp;

import org.joda.time.*; 

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        int day, month, year;
        
        DateTime date = new DateTime();
        
        day = date.getDayOfMonth();
        month = date.getMonthOfYear();
        year = date.getYear();
        
        System.out.println("DATE: "+day+"/"+month+"/"+year);
    }
}
