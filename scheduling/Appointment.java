package scheduling;

import java.sql.*;
import java.util.*;

import application.DBConnection;

public class Appointment 
{
	private ArrayList<Integer> intervalid_list;
	private ArrayList<Integer> intervalid_list_raw;
	private ArrayList<Integer> doctorid_list;
	private ArrayList<Integer> appointmentid_list;

	public Appointment()
	{
		doctorid_list = new ArrayList<Integer>();
		intervalid_list =  new ArrayList<Integer>();
		intervalid_list_raw =  new ArrayList<Integer>() ;
		appointmentid_list =  new ArrayList<Integer>();
	}
	
	public ArrayList<String> getSpecialization()
	{
		ArrayList<String> s_list = new ArrayList<String>();
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(
    				"select distinct specialization from user where user.type = 'doctor'"
    				+ "order by specialization;");
            
            
            while (resultSet.next()) 
            {          	           	 	
                s_list.add(resultSet.getString("specialization").trim());
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
		
		return s_list;
	}

	public ArrayList<String> getDoctor(String specialization)
	{	
		ArrayList<String> doctor_list = new ArrayList<String>();
		
		doctorid_list.clear();
		
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(
            		"select * from user where user.type = 'doctor' and specialization like '%" 
    				+ specialization + "%' order by specialization;");
            
            String str;
            int i = 1;
            while (resultSet.next()) 
            {
            	str = "";
            	doctorid_list.add(resultSet.getInt("user_id"));
            	str += Integer.toString(i);
            	str += ' ';
            	str += resultSet.getString("first_name").trim();
            	str += ' ';
            	str += resultSet.getString("last_name").trim();
            	           	           	 	
                doctor_list.add(str);
                i++;
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
		
		return doctor_list;
		
	}
	
	public ArrayList<String> RequestScheduleinfo(String Date, String Day, String Doctor)
	{
		ArrayList<String> intervals = new ArrayList<String>();
		
		String doctorid_str = "";
		
		for(int i = 0; i < Doctor.length(); i++)
		{
			if(Doctor.charAt(i) != ' ')
			{
				doctorid_str += Doctor.charAt(i);
			}
			else
			{
				break;
			}
		}
		
		String doctor_id = Integer.toString(doctorid_list.get(Integer.parseInt(doctorid_str) - 1));
		
		
		String str =  "select timeinterval.interval_id, timeinterval.start_time, timeinterval.duration,"
				+ "timeinterval.day"
				+ " from availability "
				+ " Inner Join timeinterval "
				+ " on timeinterval.interval_id = availability.interval_id "
				+ " where availability.doctor_id = " + doctor_id 
				+ " and availability.interval_id NOT IN ("
				+ "select interval_id from appointment"
				+ " where doctor_id = " + doctor_id
				+ " and appointment.date like '" + Date + "')"
				+ " and timeinterval.day like '%" + Day + "%';";
		
		
		
		intervalid_list.clear();
		
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(str);
            
            String resstr;
            int i = 1;
            while (resultSet.next()) 
            {
            	resstr = "";
            	
            	intervalid_list.add(resultSet.getInt("interval_id"));
            	resstr += Integer.toString(i);
            	resstr += ' ';
            	resstr += resultSet.getString("Day").trim();
            	resstr += ' ';
            	resstr += resultSet.getString("start_time").trim();
            	resstr += ' ';           	           	 	
            	resstr += resultSet.getString("duration").trim();
            	
                intervals.add(resstr);
                
                i++;
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
		
		
		return intervals;	
		
	}
	
	public boolean BookSlot(String interval,int user_id, String Doctor, String Date)
	{
		String str = "";
		
		for(int i = 0; i < interval.length(); i++)
		{
			if(interval.charAt(i) != ' ')
			{
				str += interval.charAt(i);
			}
			else
			{
				break;
			}
		}
		
		String interval_id = Integer.toString(intervalid_list.get(Integer.parseInt(str) - 1));
		
		str = "";
		
		for(int i = 0; i < Doctor.length(); i++)
		{
			if(Doctor.charAt(i) != ' ')
			{
				str += Doctor.charAt(i);
			}
			else
			{
				break;
			}
		}
		
		String doctor_id = Integer.toString(doctorid_list.get(Integer.parseInt(str) - 1));
				
		String patient_id = Integer.toString(user_id);
		
		
		String[] cols = {"doctor_id", "patient_id", "interval_id", "date", "status"};
		String[] vals = {doctor_id, patient_id, interval_id, Date, "pending"};
		String table = "appointment";
		
		return DBWriter.insertData(table, cols, vals);
		
	}
	
	public ArrayList<String> GetScheduledAppointments(int User)
	{
		ArrayList<String> appointments_list = new ArrayList<String>();
		
		String user_id = Integer.toString(User);
		
		String query = "select appointment_id, timeinterval.start_time, timeinterval.day, appointment.date, "
				+ "CONCAT(user.first_name, ' ', user.last_name) "
				+ "from appointment inner join timeinterval "
				+ "on appointment.interval_id = timeinterval.interval_id "
				+ "inner join user on appointment.doctor_id = user.user_id "
				+ "where patient_id = " + user_id + ";";
		
		appointmentid_list.clear();
		
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(query);
            
            String str;
            int i = 1;
            // str += ' ' modified 
            while (resultSet.next()) 
            {
            	str = "";
            	appointmentid_list.add(resultSet.getInt("appointment_id"));
            	str += Integer.toString(i);
            	str += "  ";
            	str += resultSet.getString("start_time").trim();
            	str += "  ";
            	str += resultSet.getString("day").trim();
            	str += "  ";
            	str += resultSet.getString("date").trim();
            	str += "  ";
            	str += resultSet.getString("CONCAT(user.first_name, ' ', user.last_name)").trim();
            	
                appointments_list.add(str);
                i++;
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
		
		return appointments_list;
		
	}
	
	public boolean cancelAppointment(String tocancel)
	{
		String appointment_str = "";
		
		for(int i = 0; i < tocancel.length(); i++)
		{
			if(tocancel.charAt(i) != ' ')
			{
				appointment_str += tocancel.charAt(i);
			}
			else
			{
				break;
			}
		}
		
		
		String appointment_id = Integer.toString(appointmentid_list.get(Integer.parseInt(appointment_str) - 1));
		
		return DBWriter.deleteData("appointment", "appointment_id", appointment_id);
				
	}
	
	public String getDoctorinfo(String selected_appointment)
	{
		//for(int i = 0; i < appointmentid_list.size(); i++)
		//{
		//System.out.println(i + ": " + appointmentid_list.get(i));
		//}
		
		String actualappointmentid = Integer.toString(appointmentid_list.get(Integer.parseInt(selected_appointment) - 1));
		
		// write query to get doctor id
		
		String query = "select doctor_id from appointment where appointment_id = "
				+ actualappointmentid + ";";
		
		String doctorid = "";
		
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(query);
            
            
            while (resultSet.next()) 
            {
            	doctorid += resultSet.getString("doctor_id");
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
	
		return doctorid;
	
	}
	
	public ArrayList<String> RequestScheduleinfoRaw(String Date, String Day, String Doctor)
	{
		ArrayList<String> intervals = new ArrayList<String>();
		
		String doctor_id = Doctor;
		
		
		String str =  "select timeinterval.interval_id, timeinterval.start_time, timeinterval.duration,"
				+ "timeinterval.day"
				+ " from availability "
				+ " Inner Join timeinterval "
				+ " on timeinterval.interval_id = availability.interval_id "
				+ " where availability.doctor_id = " + doctor_id 
				+ " and availability.interval_id NOT IN ("
				+ "select interval_id from appointment"
				+ " where doctor_id = " + doctor_id
				+ " and appointment.date like '" + Date + "')"
				+ " and timeinterval.day like '%" + Day + "%';";
		
		intervalid_list_raw.clear();
			
		try 
        {    
            ResultSet resultSet = DBConnection.getMultiSet(str);
            
            String resstr;
            int i = 1;
            while (resultSet.next()) 
            {
            	resstr = "";
            	
            	intervalid_list_raw.add(resultSet.getInt("interval_id"));
            	resstr += Integer.toString(i);
            	resstr += ' ';
            	resstr += resultSet.getString("Day").trim();
            	resstr += ' ';
            	resstr += resultSet.getString("start_time").trim();
            	resstr += ' ';           	           	 	
            	resstr += resultSet.getString("duration").trim();
            	
                intervals.add(resstr);
                
                i++;
            }
            resultSet.close();
                 
            
        }
        catch (Exception exception) 
		{
            System.out.println(exception);
        }
		
		
		return intervals;	
		
	}
	
	public boolean BookSlotRaw(String interval,int user_id, String Doctor, String Date)
	{
		String str = "";
		
		for(int i = 0; i < interval.length(); i++)
		{
			if(interval.charAt(i) != ' ')
			{
				str += interval.charAt(i);
			}
			else
			{
				break;
			}
		}
		
		String interval_id = Integer.toString(intervalid_list_raw.get(Integer.parseInt(str) - 1));
	
		
		String doctor_id = Doctor;
				
		String patient_id = Integer.toString(user_id);
		
		
		String[] cols = {"doctor_id", "patient_id", "interval_id", "date", "status"};
		String[] vals = {doctor_id, patient_id, interval_id, Date, "pending"};
		String table = "appointment";
		
		return DBWriter.insertData(table, cols, vals);
		
	}
	
}
