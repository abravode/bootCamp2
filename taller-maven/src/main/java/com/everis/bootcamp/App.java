package com.everis.bootcamp;

import org.joda.time.DateTime;

public class App 
{
    public static void main( String[] args )
    {
    	
    	DateTime dt = new DateTime();
    	String day = dt.dayOfMonth().getAsText();
    	String month = dt.monthOfYear().getAsText();
    	String year = dt.year().getAsText();
    	
        System.out.println( "[Reto]: Hoy es el dia "+ day + " de "+ month +" del año "+ year +".");
    }
}
