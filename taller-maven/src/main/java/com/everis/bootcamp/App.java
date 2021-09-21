package com.everis.bootcamp;

//Joda-time
import org.joda.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		// Joda-time
		//Dia mes y año
		LocalDate lDate = new LocalDate();
		System.out.println("Dia: " + lDate.getDayOfMonth() + 
				", Mes: " + lDate.getMonthOfYear() + 
				", Año: " + lDate.getYear());
	}
}
