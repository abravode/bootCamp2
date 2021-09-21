package com.everis.bootcamp;

import com.everis.bootcamp.util.ActualTimeDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        ActualTimeDate actualTimeDate = new ActualTimeDate();
        System.out.println("LocalTime : " + actualTimeDate.getTime());
        System.out.println("localDate : " + actualTimeDate.getDate());
    }

}
