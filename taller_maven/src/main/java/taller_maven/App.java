package taller_maven;

import org.joda.time.DateTime;

/**
 * Hello world!
 *
 */
public class App 
{
	public static DateTime date = new DateTime();
	
    public static void main( String[] args )
    {
    	
        System.out.println( "Hello World!" );
        System.out.println(date.getDayOfMonth() + "/"+ date.getMonthOfYear() + "/" + date.getYear());
        
    }
}