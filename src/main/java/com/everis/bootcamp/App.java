package com.everis.bootcamp;

import org.joda.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        LocalDate localDate = LocalDate.now();
        
        int ano = localDate.getYear();
        int mes = localDate.getMonthOfYear();
        int diaMes = localDate.getDayOfMonth();
        
        System.out.println("DIA: "+diaMes);
        System.out.println("MES:"+mes);
        System.out.println("AÑO: "+ano);
    }
}
