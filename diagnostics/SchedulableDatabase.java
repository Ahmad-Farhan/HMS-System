package diagnostics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.DBConnection;
import diagnostics.appointment.Appointment;


public class SchedulableDatabase {
	public static ArrayList<Appointment> retrieveTodaysAppointments(final int doc_id){
		ArrayList<Appointment> appointments = new ArrayList<>();
		String query = "SELECT " + Appointment.getAppSelectFields() + " FROM APPOINTMENT "
				+ "INNER JOIN USER ON USER.user_id = APPOINTMENT.patient_id "
				+ "INNER JOIN TIMEINTERVAL ON APPOINTMENT.interval_id = TIMEINTERVAL.interval_id "
				+ "INNER JOIN USER DOCTOR ON APPOINTMENT.doctor_id = DOCTOR.user_id "
				+ "WHERE APPOINTMENT.doctor_id = " + doc_id //+ " AND DATE(APPOINTMENT.date) = CURDATE()
				+ " ORDER BY start_time ASC ";
		
		ResultSet rt = DBConnection.getMultiSet(query);
		try{ while(rt.next()) { appointments.add(new Appointment(rt)); } }
		catch(SQLException e) { e.printStackTrace(); }
		return appointments;
	}
	public static ArrayList<Appointment> retrieveConductedAppointments(){
		ArrayList<Appointment> appointments = new ArrayList<>();
		String query = "SELECT " + Appointment.getAppSelectFields() + " FROM APPOINTMENT "
				+ "INNER JOIN USER ON USER.user_id = APPOINTMENT.patient_id "
				+ "INNER JOIN TIMEINTERVAL ON APPOINTMENT.interval_id = TIMEINTERVAL.interval_id "
				+ "INNER JOIN USER DOCTOR ON APPOINTMENT.doctor_id = DOCTOR.user_id "
				+ "INNER JOIN BILL ON BILL.appointment_id = APPOINTMENT.appointment_id "
				+ "WHERE APPOINTMENT.status = 'Completed' AND BILL.status != 'Paid' ORDER BY generated_on, start_time DESC"; 
				
		ResultSet rt = DBConnection.getMultiSet(query);
		try{ while(rt.next()) { appointments.add(new Appointment(rt)); } }
		catch(SQLException e) { e.printStackTrace(); }
		return appointments;
	}
}
