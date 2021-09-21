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
        int dia = dt.getDayOfMonth();
        System.out.println( "Día: " + dia);
        int mes = dt.getMonthOfYear();
        System.out.println( "Mes: " +mes);
        int año = dt.getYear();
        System.out.println( "Año: " +año);
        
    }
}
