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
        fecha();
         
    }
    
    public static void fecha()
    {
    	DateTime dt = new DateTime();
        int y=dt.getYear();
        int m=dt.getMonthOfYear();
        int d=dt.getDayOfMonth();
        System.out.println(d+"-"+m+"-"+y);
        
    }
    
}
