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
        
        DateTime date = new DateTime();
        int day = date.getDayOfMonth();
        int month = date.getMonthOfYear();
        int year = date.getYear();
        
        System.out.println("Día:"+day+" Mes:"+month+" Año:"+year);
    }
}
