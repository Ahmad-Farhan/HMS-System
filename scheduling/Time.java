package scheduling;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Time 
{
	private static Time time;
	
	private Time()
	{
		
	}
	
	public static Time getInstance()
	{
		if(time == null)
		{
			time = new Time();
		}
		
		return time;
	}
	
	public ArrayList<String> getNextDates()
	{
		ArrayList<String> date_list = new ArrayList<String>();
		
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        
        for (int i = 0; i < 7; i++) {
            currentDate = currentDate.plusDays(1); // Move to the next day

            // Check if it's one of the possible days (Mon, Tue, Wed, Thurs)
            
//            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY ||
//                currentDate.getDayOfWeek() == DayOfWeek.TUESDAY ||
//                currentDate.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
//                currentDate.getDayOfWeek() == DayOfWeek.THURSDAY) {
//
//                String formattedDate = currentDate.format(dateFormatter);
//                date_list.add(formattedDate + " (" + currentDate.getDayOfWeek() + ")");
//                
//            }
            
            //Check if it's one of the possible days (Mon, Tue, Wed)
            
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.TUESDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) {

                    String formattedDate = currentDate.format(dateFormatter);
                    date_list.add(formattedDate + " (" + currentDate.getDayOfWeek() + ")");
                    
                }
        }
        
        return date_list;
	}
}
