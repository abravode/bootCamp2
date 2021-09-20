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
        System.out.println( "Hello World!" );
        DateTime dt = new DateTime();
		
        int day = dt.getDayOfMonth();
     		
        int month = dt.getMonthOfYear();
     		
        int year = dt.getYear();
     
        System.out.println( "Dia: " +day+ "\nMes: " +month+ "\nAño: "+year );

    }
}
