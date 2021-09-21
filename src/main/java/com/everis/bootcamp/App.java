package com.everis.bootcamp;

import org.joda.time.DateTime;

/**
 * Hello world!
 *
 */
public class App {
	
	private static DateTime date = new DateTime();
	
	public static void main(String[] args) {	
		
		System.out.println("Hello World!");
		
		int dia = date.getDayOfMonth();
		String mes = date.monthOfYear().getAsText();
		int anyo = date.getYear();
		
		System.out.println("\n"+ dia +" de "+ mes + " de " + anyo);
	}
}
