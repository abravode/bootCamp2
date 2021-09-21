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
        System.out.print("La fecha actual es: " );
        DateTime date = DateTime.now();
        System.out.println(date.getDayOfMonth()+"/"+date.getMonthOfYear()+"/"+date.getYear());
    }
}
