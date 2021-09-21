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
        // from Joda to JDK
        DateTime dt = new DateTime();
        
        System.out.println("DÍA "+ dt.getDayOfMonth() + " MES "+dt.getMonthOfYear() + 
        		" AÑO "+ dt.getYear());
    }
}
