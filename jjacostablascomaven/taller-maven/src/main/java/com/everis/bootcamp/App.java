package com.everis.bootcamp;

import org.joda.time.DateTime;

public class App 
{
    public static void main( String[] args )
    {
    	DateTime dt = new DateTime();
		
    	// Obtener el día 
    	int dayOfWeek = dt.getDayOfMonth();
    			
    	// Obtener el mes
    	int monthOfYear = dt.getMonthOfYear();
    			
    	// Obtener el año
    	int year = dt.getYear();
    	
        System.out.print("Hoy estamos a : " + dayOfWeek + "/" + monthOfYear + "/" + year);
    }
}
